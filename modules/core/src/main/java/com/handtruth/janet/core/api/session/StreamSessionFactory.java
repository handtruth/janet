package com.handtruth.janet.core.api.session;

import java.net.InetSocketAddress;

public interface StreamSessionFactory extends SessionFactory {

    StreamSession createStreamSession(InetSocketAddress externalSocket, InetSocketAddress internalSocket);
}
