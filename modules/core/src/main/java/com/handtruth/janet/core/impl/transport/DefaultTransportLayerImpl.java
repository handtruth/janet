package com.handtruth.janet.core.impl.transport;

import com.handtruth.janet.core.api.session.SessionLayer;
import com.handtruth.janet.core.api.transport.TransportLayer;
import com.handtruth.janet.core.api.transport.TransportLayerParameters;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultTransportLayerImpl implements TransportLayer {

    private final SessionLayer sessionLayer;

    private final TransportLayerParameters parameters;
}
