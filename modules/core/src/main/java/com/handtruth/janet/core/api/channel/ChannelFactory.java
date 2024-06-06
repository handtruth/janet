package com.handtruth.janet.core.api.channel;

import java.net.SocketAddress;

public interface ChannelFactory {

    DatagramChannel createDatagramChannel(ReadyChannelAction<DatagramChannel> readyAction) throws ChannelException;

    DatagramChannel createDatagramChannel(SocketAddress address,
                                          ReadyChannelAction<DatagramChannel> readyAction) throws ChannelException;

    StreamChannel createStreamChannel(SocketAddress address,
                                      ReadyChannelAction<StreamChannel> readyAction) throws ChannelException;

    ListenChannel createListenChannel(SocketAddress address,
                                      ReadyChannelAction<StreamChannel> readyAction) throws ChannelException;
}
