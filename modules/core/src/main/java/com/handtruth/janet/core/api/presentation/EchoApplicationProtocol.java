package com.handtruth.janet.core.api.presentation;

import com.handtruth.janet.core.api.session.EchoSession;
import com.handtruth.janet.core.api.session.EchoSessionFactory;
import com.handtruth.janet.core.api.session.SessionEvent;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.nio.ByteBuffer;

public interface EchoApplicationProtocol extends ApplicationProtocol {

    default void receiveEchoSession(final Receiver receiver) {
    }

    default void sendEchoSession(final EchoSession session,
                                 final SessionEvent event,
                                 @Nullable final ByteBuffer payload) {
        session.close();
    }

    interface Receiver extends EchoSessionFactory {

        @Nullable
        ByteBuffer receiveEcho(@Nullable EchoSession echoSession);
    }
}
