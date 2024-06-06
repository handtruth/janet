package com.handtruth.janet.core.impl.session;

import lombok.Value;

import java.net.InetAddress;

@Value
public class StreamSessionDiscriminator implements SocketSessionDiscriminator<StreamSessionImpl> {

    InetAddress internalHost;

    int internalPort;

    InetAddress externalHost;

    int externalPort;
}
