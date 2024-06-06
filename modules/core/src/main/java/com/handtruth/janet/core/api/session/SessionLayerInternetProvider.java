package com.handtruth.janet.core.api.session;

import com.handtruth.janet.core.api.transport.TransportLayer;
import com.handtruth.janet.core.api.transport.TransportLayerInternetProvider;
import com.handtruth.janet.core.api.transport.TransportLayerParameters;
import com.handtruth.janet.core.impl.transport.DefaultTransportLayerImpl;

public abstract class SessionLayerInternetProvider extends TransportLayerInternetProvider {

    protected TransportLayerParameters makeTransportLayerParameters() {
        return TransportLayerParameters.builder().build();
    }

    protected abstract SessionLayer provideSessionLayer();

    @Override
    protected final TransportLayer provideTransportLayer() {
        return new DefaultTransportLayerImpl(provideSessionLayer(), makeTransportLayerParameters());
    }
}
