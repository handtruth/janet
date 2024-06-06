package com.handtruth.janet.core.api.session;

import com.handtruth.janet.core.api.util.Closeable;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.nio.ByteBuffer;

public interface SessionLayer extends Closeable {

    String LAYER_NAME = "Session";

    default void receiveSession(final Receiver receiver) {
    }

    default void sendSession(final Session session, final SessionEvent event, @Nullable final ByteBuffer payload) {
        session.close();
    }

    @Override
    default void close() {
    }

    interface Receiver extends EchoSessionFactory, DatagramSessionFactory, StreamSessionFactory {

        @Nullable
        ByteBuffer receive(@Nullable Session session);
    }
}
