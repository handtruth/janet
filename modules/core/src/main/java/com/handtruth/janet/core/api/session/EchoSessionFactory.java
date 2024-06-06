package com.handtruth.janet.core.api.session;

import java.net.InetAddress;

public interface EchoSessionFactory extends SessionFactory {

    EchoSession createEchoSession(int sequenceIdentifier, InetAddress externalHost, InetAddress internalHost);
}
