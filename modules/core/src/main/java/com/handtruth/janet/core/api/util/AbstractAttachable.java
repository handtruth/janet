package com.handtruth.janet.core.api.util;

import edu.umd.cs.findbugs.annotations.Nullable;

public abstract class AbstractAttachable implements Attachable {

    private Object attachment = null;

    @Override
    public final void setAttachment(@Nullable Object attachment) {
        this.attachment = attachment;
    }

    @Override
    @Nullable
    public final Object getAttachment() {
        return attachment;
    }
}
