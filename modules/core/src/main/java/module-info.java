module com.handtruth.janet.core {
    requires static com.github.spotbugs.annotations;
    requires static lombok;
    requires org.slf4j;
    exports com.handtruth.janet.core.api;
    exports com.handtruth.janet.core.api.channel;
    exports com.handtruth.janet.core.api.linklocal;
    exports com.handtruth.janet.core.api.network;
    exports com.handtruth.janet.core.api.presentation;
    exports com.handtruth.janet.core.api.session;
    exports com.handtruth.janet.core.api.transport;
    exports com.handtruth.janet.core.api.util;
}
