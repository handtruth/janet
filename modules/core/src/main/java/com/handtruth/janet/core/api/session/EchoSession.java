package com.handtruth.janet.core.api.session;

import java.net.InetAddress;

public interface EchoSession extends Session {

    short getSequenceIdentifier();

    InetAddress getExternalHost();

    InetAddress getInternalHost();

    short getSequenceNumber();

    int getTtl();
}
