package com.handtruth.janet.core.impl.network;

import com.handtruth.janet.core.api.network.NetworkLayer;
import com.handtruth.janet.core.api.network.NetworkLayerParameters;
import com.handtruth.janet.core.api.transport.TransportLayer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultNetworkLayerImpl implements NetworkLayer {

    private final TransportLayer transportLayer;

    private final NetworkLayerParameters parameters;
}
