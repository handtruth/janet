package com.handtruth.janet.core.impl.session;

import com.handtruth.janet.core.api.session.DatagramSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class DatagramSessionImpl extends AbstractSocketSession implements DatagramSession {

    private final DatagramSessionDiscriminator discriminator;
}
