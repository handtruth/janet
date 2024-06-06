package com.handtruth.janet.core.api.session;

import java.net.InetSocketAddress;

public interface SocketSession extends Session {

    InetSocketAddress getExternalSocket();

    InetSocketAddress getInternalSocket();
}
