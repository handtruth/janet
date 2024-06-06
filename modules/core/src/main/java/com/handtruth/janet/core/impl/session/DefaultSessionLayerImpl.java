package com.handtruth.janet.core.impl.session;

import com.handtruth.janet.core.api.presentation.ApplicationProtocol;
import com.handtruth.janet.core.api.presentation.ApplicationProtocolDiscriminator;
import com.handtruth.janet.core.api.presentation.DatagramApplicationProtocol;
import com.handtruth.janet.core.api.presentation.EchoApplicationProtocol;
import com.handtruth.janet.core.api.presentation.StreamApplicationProtocol;
import com.handtruth.janet.core.api.session.DatagramSession;
import com.handtruth.janet.core.api.session.EchoSession;
import com.handtruth.janet.core.api.session.Session;
import com.handtruth.janet.core.api.session.SessionEvent;
import com.handtruth.janet.core.api.session.SessionLayer;
import com.handtruth.janet.core.api.session.SocketSession;
import com.handtruth.janet.core.api.session.StreamSession;
import com.handtruth.janet.core.api.presentation.ApplicationProtocolBasket;
import edu.umd.cs.findbugs.annotations.Nullable;
import lombok.RequiredArgsConstructor;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

@RequiredArgsConstructor
public final class DefaultSessionLayerImpl implements SessionLayer,
        EchoApplicationProtocol.Receiver,
        DatagramApplicationProtocol.Receiver,
        StreamApplicationProtocol.Receiver {

    private final ApplicationProtocolBasket applicationProtocolBasket;

    private Receiver currentReceiver = null;
    private boolean isReceived = false;

    @Override
    public void receiveSession(final Receiver receiver) {
        currentReceiver = receiver;
        isReceived = false;
        try {
            for (final ApplicationProtocol applicationProtocol : applicationProtocolBasket) {
                if (applicationProtocol instanceof EchoApplicationProtocol echoApplicationProtocol) {
                    echoApplicationProtocol.receiveEchoSession(this);
                } else if (applicationProtocol instanceof DatagramApplicationProtocol datagramApplicationProtocol) {
                    datagramApplicationProtocol.receiveDatagramSession(this);
                } else if (applicationProtocol instanceof StreamApplicationProtocol streamApplicationProtocol) {
                    streamApplicationProtocol.receiveStreamSession(this);
                }
                if (isReceived) {
                    break;
                }
            }
        } finally {
            currentReceiver = null;
        }
    }

    private Receiver getCurrentReceiver() {
        isReceived = true;
        final Receiver currentReceiver = this.currentReceiver;
        if (currentReceiver == null) {
            throw new IllegalStateException();
        }
        return currentReceiver;
    }

    @Nullable
    private ByteBuffer receiveAny(@Nullable final Session session) {
        return getCurrentReceiver().receive(session);
    }

    @Nullable
    @Override
    public ByteBuffer receiveEcho(@Nullable final EchoSession echoSession) {
        return receiveAny(echoSession);
    }

    @Nullable
    @Override
    public ByteBuffer receiveDatagram(@Nullable final DatagramSession datagramSession) {
        return receiveAny(datagramSession);
    }

    @Nullable
    @Override
    public ByteBuffer receiveStream(@Nullable final StreamSession streamSession) {
        return receiveAny(streamSession);
    }

    @Override
    public EchoSession createEchoSession(final int sequenceIdentifier,
                                         final InetAddress externalHost,
                                         final InetAddress internalHost) {
        return getCurrentReceiver().createEchoSession(sequenceIdentifier, externalHost, internalHost);
    }

    @Override
    public DatagramSession createDatagramSession(final InetSocketAddress externalSocket,
                                                 final InetSocketAddress internalSocket) {
        return getCurrentReceiver().createDatagramSession(externalSocket, internalSocket);
    }

    @Override
    public StreamSession createStreamSession(final InetSocketAddress externalSocket,
                                             final InetSocketAddress internalSocket) {
        return getCurrentReceiver().createStreamSession(externalSocket, internalSocket);
    }

    @Override
    public void sendSession(final Session session, final SessionEvent event, @Nullable final ByteBuffer payload) {
        if (session instanceof EchoSession echoSession) {
            final EchoApplicationProtocol echoApplicationProtocol = applicationProtocolBasket.getApplicationProtocol(ApplicationProtocolDiscriminator.Echo.getInstance());
            if (echoApplicationProtocol != null) {
                echoApplicationProtocol.sendEchoSession(echoSession, event, payload);
                return;
            }
        } else if (session instanceof SocketSession socketSession) {
            final int port = socketSession.getExternalSocket().getPort();
            if (socketSession instanceof DatagramSession datagramSession) {
                final ApplicationProtocolDiscriminator<DatagramApplicationProtocol> discriminator = new ApplicationProtocolDiscriminator.Datagram(port);
                final DatagramApplicationProtocol specialisedDatagramApplicationProtocol = applicationProtocolBasket.getApplicationProtocol(discriminator);
                final DatagramApplicationProtocol datagramApplicationProtocol = specialisedDatagramApplicationProtocol != null
                        ? specialisedDatagramApplicationProtocol
                        : applicationProtocolBasket.getApplicationProtocol(ApplicationProtocolDiscriminator.Datagram.getDefault());
                if (datagramApplicationProtocol != null) {
                    datagramApplicationProtocol.sendDatagramSession(datagramSession, event, payload);
                    return;
                }
            } else if (socketSession instanceof StreamSession streamSession) {
                final ApplicationProtocolDiscriminator<StreamApplicationProtocol> discriminator = new ApplicationProtocolDiscriminator.Stream(port);
                final StreamApplicationProtocol specialisedStreamApplicationProtocol = applicationProtocolBasket.getApplicationProtocol(discriminator);
                final StreamApplicationProtocol streamApplicationProtocol = specialisedStreamApplicationProtocol != null
                        ? specialisedStreamApplicationProtocol
                        : applicationProtocolBasket.getApplicationProtocol(ApplicationProtocolDiscriminator.Stream.getDefault());
                if (streamApplicationProtocol != null) {
                    streamApplicationProtocol.sendStreamSession(streamSession, event, payload);
                    return;
                }
            }
        }
        session.close();
    }

    @Override
    public void close() {
        applicationProtocolBasket.forEach(ApplicationProtocol::close);
    }
}
