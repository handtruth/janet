package com.handtruth.janet.core.api.network;

import com.handtruth.janet.core.impl.network.IpAddressFilterImpl;
import com.handtruth.janet.core.impl.network.IpAddressFilterImpl.Modes;
import com.handtruth.janet.core.impl.util.ParameterLoader;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class NetworkLayerParameters {

    private static final ParameterLoader parameters = ParameterLoader.of("network");

    private static IpAddressFilter loadIpAddressFilter() {
        final Modes mode = parameters.enumeration("filter.mode", Modes.DENY, Modes.values());
        final String definition = parameters.string(
                "filter.list",
                "127.0.0.0/8, 10.0.0.0/8, 100.64.0.0/10, 172.16.0.0/12, 192.168.0.0/16, 224.0.0.0/4"
        );
        return new IpAddressFilterImpl(mode, definition);
    }

    @Builder.Default
    IpAddressFilter ipAddressFilter = loadIpAddressFilter();
}
