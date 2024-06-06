package com.handtruth.janet.core.api.channel;

import edu.umd.cs.findbugs.annotations.Nullable;

public interface ListenChannel extends Channel {

    @Nullable
    StreamChannel accept();
}
