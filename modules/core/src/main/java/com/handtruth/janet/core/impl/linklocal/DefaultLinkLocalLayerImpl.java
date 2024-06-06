package com.handtruth.janet.core.impl.linklocal;

import com.handtruth.janet.core.api.linklocal.LinkLocalLayer;
import com.handtruth.janet.core.api.linklocal.LinkLocalLayerParameters;
import com.handtruth.janet.core.api.network.NetworkLayer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultLinkLocalLayerImpl implements LinkLocalLayer {

    private final NetworkLayer networkLayer;

    private final LinkLocalLayerParameters parameters;
}
