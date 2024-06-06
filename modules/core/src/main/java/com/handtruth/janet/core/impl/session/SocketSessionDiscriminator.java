package com.handtruth.janet.core.impl.session;

public sealed interface SocketSessionDiscriminator<S extends AbstractSocketSession> extends SessionDiscriminator<S>
        permits DatagramSessionDiscriminator, StreamSessionDiscriminator {

    int getInternalPort();

    int getExternalPort();
}
