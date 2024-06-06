package com.handtruth.janet.core.impl.session;

import com.handtruth.janet.core.api.session.SocketSession;

import java.net.InetSocketAddress;

public abstract class AbstractSocketSession extends AbstractSession implements SocketSession {

    @Override
    public abstract SocketSessionDiscriminator<? extends AbstractSocketSession> getDiscriminator();

    @Override
    public InetSocketAddress getExternalSocket() {
        return new InetSocketAddress(getDiscriminator().getExternalHost(), getDiscriminator().getExternalPort());
    }

    @Override
    public InetSocketAddress getInternalSocket() {
        return new InetSocketAddress(getDiscriminator().getInternalHost(), getDiscriminator().getInternalPort());
    }
}
