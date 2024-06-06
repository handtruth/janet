package com.handtruth.janet.core.api.linklocal;

import com.handtruth.janet.core.api.util.Closeable;

import java.nio.ByteBuffer;

public interface LinkLocalLayer extends Closeable {

    String LAYER_NAME = "LinkLocal";

    int FRAME_HEADER_SIZE = 14;

    int DEFAULT_MTU = 1500;

    int FRAME_SIZE = FRAME_HEADER_SIZE + DEFAULT_MTU;

    default boolean receiveEthernetFrame(final ByteBuffer frame) {
        return false;
    }

    default void sendEthernetFrame(final ByteBuffer frame) {
    }

    @Override
    default void close() {
    }
}
