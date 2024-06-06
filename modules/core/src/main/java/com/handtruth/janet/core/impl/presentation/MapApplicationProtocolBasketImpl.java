package com.handtruth.janet.core.impl.presentation;

import com.handtruth.janet.core.api.presentation.ApplicationProtocol;
import com.handtruth.janet.core.api.presentation.ApplicationProtocolBasket;
import com.handtruth.janet.core.api.presentation.ApplicationProtocolCollector;
import com.handtruth.janet.core.api.presentation.ApplicationProtocolDiscriminator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapApplicationProtocolBasketImpl implements ApplicationProtocolCollector, ApplicationProtocolBasket {

    private final Map<ApplicationProtocolDiscriminator<?>, ApplicationProtocol> applicationProtocols = new HashMap<>();

    @Override
    public Iterator<ApplicationProtocol> iterator() {
        return applicationProtocols.values().iterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends ApplicationProtocol> P getApplicationProtocol(ApplicationProtocolDiscriminator<P> discriminator) {
        return (P) applicationProtocols.get(discriminator);
    }

    @Override
    public <P extends ApplicationProtocol> void addApplicationProtocol(ApplicationProtocolDiscriminator<P> discriminator,
                                                                       P applicationProtocol) {
        if (applicationProtocols.putIfAbsent(discriminator, applicationProtocol) != null) {
            throw new IllegalStateException();
        }
    }
}
