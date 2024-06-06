package com.handtruth.janet.core.api.presentation;

import com.handtruth.janet.core.api.session.SessionEvent;
import com.handtruth.janet.core.api.session.StreamSession;
import com.handtruth.janet.core.api.session.StreamSessionFactory;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.nio.ByteBuffer;

public interface StreamApplicationProtocol extends ApplicationProtocol {

    default void receiveStreamSession(final Receiver receiver) {
    }

    default void sendStreamSession(final StreamSession session,
                                   final SessionEvent event,
                                   @Nullable ByteBuffer stream) {
        session.close();
    }

    interface Receiver extends StreamSessionFactory {

        @Nullable
        ByteBuffer receiveStream(@Nullable final StreamSession streamSession);
    }
}
