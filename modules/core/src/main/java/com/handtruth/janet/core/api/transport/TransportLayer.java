package com.handtruth.janet.core.api.transport;

import com.handtruth.janet.core.api.util.Closeable;

public interface TransportLayer extends Closeable {

    String LAYER_NAME = "Transport";

    byte PROTOCOL_NONE = 0;

    byte PROTOCOL_ICMP = 1;

    byte PROTOCOL_TCP = 6;

    byte PROTOCOL_UDP = 17;

    default byte receiveTransportMessage(final TransportMessage message) {
        return PROTOCOL_NONE;
    }

    default void sendTransportMessage(final byte protocol, final TransportMessage message) {
    }

    @Override
    default void close() {
    }
}
