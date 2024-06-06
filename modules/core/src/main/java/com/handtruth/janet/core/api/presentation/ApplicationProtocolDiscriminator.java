package com.handtruth.janet.core.api.presentation;

import lombok.Value;

public sealed interface ApplicationProtocolDiscriminator<P extends ApplicationProtocol> {

    int PROTOCOL_ALL = 0;
    int PROTOCOL_ECHO = 7;
    int PROTOCOL_DOMAIN = 53;
    int PROTOCOL_HTTP = 80;
    int PROTOCOL_HTTPS = 443;

    final class Echo implements ApplicationProtocolDiscriminator<EchoApplicationProtocol> {

        private static final Echo INSTANCE = new Echo();

        public static Echo getInstance() {
            return INSTANCE;
        }

        private Echo() {
        }
    }

    @Value
    class Datagram implements ApplicationProtocolDiscriminator<DatagramApplicationProtocol> {

        private static final Datagram DEFAULT = new Datagram(PROTOCOL_ALL);

        public static Datagram getDefault() {
            return DEFAULT;
        }

        int port;
    }

    @Value
    class Stream implements ApplicationProtocolDiscriminator<StreamApplicationProtocol> {

        private static final Stream DEFAULT = new Stream(PROTOCOL_ALL);

        public static Stream getDefault() {
            return DEFAULT;
        }

        int port;
    }
}
