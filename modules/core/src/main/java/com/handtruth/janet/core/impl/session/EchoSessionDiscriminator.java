package com.handtruth.janet.core.impl.session;

import lombok.Value;

import java.net.InetAddress;

@Value
public class EchoSessionDiscriminator implements SessionDiscriminator<EchoSessionImpl> {

    short sequenceIdentifier;

    InetAddress internalHost;

    InetAddress externalHost;
}
