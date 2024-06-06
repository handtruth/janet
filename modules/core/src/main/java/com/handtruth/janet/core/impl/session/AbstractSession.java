package com.handtruth.janet.core.impl.session;

import com.handtruth.janet.core.api.session.Session;
import com.handtruth.janet.core.api.util.AbstractAttachable;
import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class AbstractSession extends AbstractAttachable implements Session {

    private Instant updatedAt = Instant.now();

    public void update() {
        updatedAt = Instant.now();
    }

    private boolean isClosed = false;

    public abstract SessionDiscriminator<? extends AbstractSession> getDiscriminator();

    @Override
    public final void close() {
        isClosed = true;
    }
}
