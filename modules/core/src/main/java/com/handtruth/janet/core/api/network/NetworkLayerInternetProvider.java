package com.handtruth.janet.core.api.network;

import com.handtruth.janet.core.api.linklocal.LinkLocalLayer;
import com.handtruth.janet.core.api.linklocal.LinkLocalLayerInternetProvider;
import com.handtruth.janet.core.api.linklocal.LinkLocalLayerParameters;
import com.handtruth.janet.core.impl.linklocal.DefaultLinkLocalLayerImpl;

public abstract class NetworkLayerInternetProvider extends LinkLocalLayerInternetProvider {

    protected LinkLocalLayerParameters makeLinkLocalLayerParameters() {
        return LinkLocalLayerParameters.builder().build();
    }

    protected abstract NetworkLayer provideNetworkLayer();

    @Override
    protected final LinkLocalLayer provideLinkLocalLayer() {
        return new DefaultLinkLocalLayerImpl(provideNetworkLayer(), makeLinkLocalLayerParameters());
    }
}
