package com.handtruth.janet.core.api.linklocal;

import com.handtruth.janet.core.api.InternetProvider;

public abstract class LinkLocalLayerInternetProvider implements InternetProvider {

    protected abstract LinkLocalLayer provideLinkLocalLayer();

    @Override
    public final LinkLocalLayer provideInternet() {
        return provideLinkLocalLayer();
    }
}
