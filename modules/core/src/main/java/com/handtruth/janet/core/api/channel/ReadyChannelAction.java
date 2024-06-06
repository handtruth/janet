package com.handtruth.janet.core.api.channel;

@FunctionalInterface
public interface ReadyChannelAction<C extends Channel> {

    void invokeReadyChannelAction(C channel, Readiness readiness);
}
