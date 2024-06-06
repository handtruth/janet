package com.handtruth.janet.core.api.presentation;

import com.handtruth.janet.core.api.session.DatagramSession;
import com.handtruth.janet.core.api.session.DatagramSessionFactory;
import com.handtruth.janet.core.api.session.SessionEvent;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.nio.ByteBuffer;

public interface DatagramApplicationProtocol extends ApplicationProtocol {

    default void receiveDatagramSession(final Receiver receiver) {
    }

    default void sendDatagramSession(final DatagramSession session,
                                     final SessionEvent event,
                                     @Nullable final ByteBuffer datagram) {
        session.close();
    }

    interface Receiver extends DatagramSessionFactory {

        @Nullable
        ByteBuffer receiveDatagram(@Nullable DatagramSession datagramSession);
    }
}
