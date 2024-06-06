package com.handtruth.janet.core.api.presentation;

import com.handtruth.janet.core.api.session.SessionLayer;
import com.handtruth.janet.core.api.session.SessionLayerInternetProvider;
import com.handtruth.janet.core.impl.session.DefaultSessionLayerImpl;

public abstract class PresentationLayerInternetProvider extends SessionLayerInternetProvider {

    protected abstract ApplicationProtocolBasket provideApplicationProtocolProvider();

    @Override
    protected final SessionLayer provideSessionLayer() {
        return new DefaultSessionLayerImpl(provideApplicationProtocolProvider());
    }
}
