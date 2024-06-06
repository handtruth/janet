package com.handtruth.janet.core.api.channel;

import edu.umd.cs.findbugs.annotations.Nullable;

public class ChannelException extends Exception {

    public ChannelException() {
        super();
    }

    public ChannelException(@Nullable String message) {
        super(message);
    }

    public ChannelException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    public ChannelException(@Nullable Throwable cause) {
        super(cause);
    }
}
