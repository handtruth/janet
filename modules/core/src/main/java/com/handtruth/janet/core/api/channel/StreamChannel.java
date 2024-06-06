package com.handtruth.janet.core.api.channel;

import java.nio.ByteBuffer;

public interface StreamChannel extends Channel {

    void write(final ByteBuffer data) throws ChannelException;

    boolean read(final ByteBuffer data) throws ChannelException;

    boolean connect() throws ChannelException;
}
