package com.handtruth.janet.core.api.util;

import edu.umd.cs.findbugs.annotations.Nullable;

public interface Attachable {

    void setAttachment(@Nullable Object attachment);

    @Nullable
    Object getAttachment();
}
