package com.handtruth.janet.core.impl.session;

import edu.umd.cs.findbugs.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;

public final class SessionStorage {

    private final NavigableMap<Instant, AbstractSession> expirationQueue = new TreeMap<>();

    private final Map<SessionDiscriminator<?>, AbstractSession> sessions = new HashMap<>();

    public <S extends AbstractSession, D extends SessionDiscriminator<S>> S getOrCreateSession(final D discriminator,
                                                                                               final Function<D, S> sessionFactory) {
        final S session = getSession(discriminator);
        if (session != null) {
            return session;
        }
        final S newSession = sessionFactory.apply(discriminator);
        assert newSession != null;
        putSession(discriminator, newSession);
        expirationQueue.put(newSession.getUpdatedAt(), newSession);
        return newSession;
    }

    public <S extends AbstractSession> void updateSession(final S session) {
        final Instant updatedAt = session.getUpdatedAt();
        final AbstractSession removedSession = expirationQueue.remove(updatedAt);
        assert removedSession == session;
        session.update();
        final Instant newUpdatedAt = session.getUpdatedAt();
        final AbstractSession oldSession = expirationQueue.put(newUpdatedAt, session);
        assert oldSession == null;
    }

    @Nullable
    public AbstractSession getNextExpiredSession(final Duration timeout) {
        final Instant expiredTime = Instant.now().minus(timeout);
        final Map.Entry<Instant, AbstractSession> entry = expirationQueue.firstEntry();
        if (entry == null) {
            return null;
        }
        if (entry.getKey().compareTo(expiredTime) <= 0) {
            final AbstractSession session = entry.getValue();
            removeSession(session);
            return session;
        }
        return null;
    }

    public <S extends AbstractSession> void removeSession(final S session) {
        final AbstractSession removedSession = sessions.remove(session.getDiscriminator());
        assert removedSession == session;
        final AbstractSession removedFromQueue = expirationQueue.remove(session.getUpdatedAt());
        assert removedFromQueue == session;
    }

    private <S extends AbstractSession, D extends SessionDiscriminator<S>> void putSession(final D discriminator,
                                                                                           final S session) {
        final AbstractSession oldSession = sessions.put(discriminator, session);
        assert oldSession == null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <S extends AbstractSession, D extends SessionDiscriminator<S>> S getSession(final D discriminator) {
        final AbstractSession session = sessions.get(discriminator);
        if (session != null) {
            return (S) session;
        } else {
            return null;
        }
    }
}
