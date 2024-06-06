package com.handtruth.janet.core.impl.session;

import java.net.InetAddress;

public sealed interface SessionDiscriminator<S extends AbstractSession>
        permits EchoSessionDiscriminator, SocketSessionDiscriminator {

    InetAddress getInternalHost();

    InetAddress getExternalHost();
}
