package com.handtruth.janet.core.api.presentation;

public interface ApplicationProtocolCollector {

    <P extends ApplicationProtocol> void addApplicationProtocol(ApplicationProtocolDiscriminator<P> discriminator,
                                                                P applicationProtocol);
}
