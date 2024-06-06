package com.handtruth.janet.core.api;

import com.handtruth.janet.core.api.linklocal.LinkLocalLayer;

public interface InternetProvider {

    LinkLocalLayer provideInternet();
}
