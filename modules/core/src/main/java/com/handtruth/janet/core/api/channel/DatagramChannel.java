package com.handtruth.janet.core.api.channel;

import edu.umd.cs.findbugs.annotations.Nullable;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public interface DatagramChannel extends Channel {

    void sendTo(final ByteBuffer data, final SocketAddress destination) throws ChannelException;

    @Nullable
    SocketAddress receiveFrom(final ByteBuffer data) throws ChannelException;
}
