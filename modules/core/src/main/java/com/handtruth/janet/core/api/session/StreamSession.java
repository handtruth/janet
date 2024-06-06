package com.handtruth.janet.core.api.session;

import java.nio.ByteBuffer;

public interface StreamSession extends SocketSession {

    ByteBuffer getSendBuffer();

    ByteBuffer getReceiveBuffer();

    void accept();
}
