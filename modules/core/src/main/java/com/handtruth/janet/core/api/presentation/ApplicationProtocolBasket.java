package com.handtruth.janet.core.api.presentation;

import edu.umd.cs.findbugs.annotations.Nullable;

public interface ApplicationProtocolBasket extends Iterable<ApplicationProtocol> {

    @Nullable
    <P extends ApplicationProtocol> P getApplicationProtocol(ApplicationProtocolDiscriminator<P> discriminator);
}
