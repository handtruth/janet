package com.handtruth.janet.core.impl.session;

import com.handtruth.janet.core.api.session.StreamSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

@Getter
@RequiredArgsConstructor
public final class StreamSessionImpl extends AbstractSocketSession implements StreamSession {

    private final StreamSessionDiscriminator discriminator;

    @Override
    public ByteBuffer getSendBuffer() {
        return null;
    }

    @Override
    public ByteBuffer getReceiveBuffer() {
        return null;
    }

    @Override
    public void accept() {

    }
}
