package com.handtruth.janet.core.api.network;

import com.handtruth.janet.core.api.util.Closeable;

import java.nio.ByteBuffer;

public interface NetworkLayer extends Closeable {

    String LAYER_NAME = "Network";

    short PROTOCOL_NONE = 0;

    short PROTOCOL_IPv4 = 0x0800;

    short PROTOCOL_IP = PROTOCOL_IPv4;

    short PROTOCOL_IPv6 = (short) 0x86dd;

    default short receivePacket(final ByteBuffer packet) {
        return PROTOCOL_NONE;
    }

    default void sendPacket(final short protocol, final ByteBuffer packet) {
    }

    @Override
    default void close() {
    }
}
