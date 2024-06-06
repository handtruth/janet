package com.handtruth.janet.core.api.transport;

import com.handtruth.janet.core.impl.util.ParameterLoader;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Builder
@Value
public class TransportLayerParameters {

    private static final ParameterLoader parameters = ParameterLoader.of("transport");

    Duration sessionLifetime = parameters.duration("session-lifetime", Duration.ofMinutes(1));

    int maxSessionCount = parameters.integer("max-session-count", 10, 0);

    int streamBufferSize = parameters.integer("stream-buffer-size", 2000, 1);

    Duration tcpRetransmissionTimeout = parameters.duration("tcp-retransmission-timeout", Duration.ofMinutes(1));
}
