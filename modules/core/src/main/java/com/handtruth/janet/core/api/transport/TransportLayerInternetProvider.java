package com.handtruth.janet.core.api.transport;

import com.handtruth.janet.core.api.network.NetworkLayer;
import com.handtruth.janet.core.api.network.NetworkLayerInternetProvider;
import com.handtruth.janet.core.api.network.NetworkLayerParameters;
import com.handtruth.janet.core.impl.network.DefaultNetworkLayerImpl;

public abstract class TransportLayerInternetProvider extends NetworkLayerInternetProvider {

    protected NetworkLayerParameters makeNetworkLayerParameters() {
        return NetworkLayerParameters.builder().build();
    }

    protected abstract TransportLayer provideTransportLayer();

    @Override
    protected final NetworkLayer provideNetworkLayer() {
        return new DefaultNetworkLayerImpl(provideTransportLayer(), makeNetworkLayerParameters());
    }
}
