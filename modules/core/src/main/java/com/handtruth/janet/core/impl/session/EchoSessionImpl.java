package com.handtruth.janet.core.impl.session;

import com.handtruth.janet.core.api.session.EchoSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@Setter
@RequiredArgsConstructor
public final class EchoSessionImpl extends AbstractSession implements EchoSession {

    private final EchoSessionDiscriminator discriminator;

    private short sequenceNumber;

    private int ttl;

    @Override
    public short getSequenceIdentifier() {
        return discriminator.getSequenceIdentifier();
    }

    @Override
    public InetAddress getExternalHost() {
        return discriminator.getExternalHost();
    }

    @Override
    public InetAddress getInternalHost() {
        return discriminator.getInternalHost();
    }
}
