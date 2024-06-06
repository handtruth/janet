package com.handtruth.janet.core.api.presentation;

import com.handtruth.janet.core.api.util.Closeable;

public interface ApplicationProtocol extends Closeable {

    @Override
    default void close() {
    }
}
