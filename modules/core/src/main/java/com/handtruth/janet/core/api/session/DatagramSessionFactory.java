package com.handtruth.janet.core.api.session;

import java.net.InetSocketAddress;

public interface DatagramSessionFactory extends SessionFactory {

    DatagramSession createDatagramSession(InetSocketAddress externalSocket, InetSocketAddress internalSocket);
}
