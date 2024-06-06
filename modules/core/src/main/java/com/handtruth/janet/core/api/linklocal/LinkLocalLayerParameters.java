package com.handtruth.janet.core.api.linklocal;

import com.handtruth.janet.core.impl.util.ParameterLoader;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Builder
@Value
public class LinkLocalLayerParameters {

    private static final ParameterLoader parameters = ParameterLoader.of("linklocal");

    @Builder.Default
    int addressTableCapacity = parameters.integer("address-table-capacity", 5, 0);

    @Builder.Default
    Duration arpIdleTime = parameters.duration("arm-idle-time", Duration.ofSeconds(1));
}
