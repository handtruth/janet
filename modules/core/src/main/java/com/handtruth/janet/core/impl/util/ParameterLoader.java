package com.handtruth.janet.core.impl.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class ParameterLoader {

    private static final String PREFIX = "com.handtruth.janet.";

    public static ParameterLoader of(final String name) {
        return new ParameterLoader(name);
    }

    private final String segment;

    public ParameterLoader(final String part) {
        segment = PREFIX + part + ".";
    }

    public String string(final String name, final String def) {
        return parameter(name).orElse(def);
    }

    public int integer(final String name, final int def, final int start, final int end) {
        return parameter(name)
                .flatMap(noThrow(Integer::parseInt))
                .filter(x -> x >= start && x <= end)
                .orElse(def);
    }

    public int integer(final String name, final int def, final int start) {
        return integer(name, def, start, Integer.MAX_VALUE);
    }

    public Duration duration(final String name, final Duration def) {
        return parameter(name).flatMap(noThrow(Duration::parse)).orElse(def);
    }

    public <E extends Enum<E>> E enumeration(final String name, final E def, final E[] values) {
        return parameter(name).flatMap(x ->
            Arrays.stream(values).filter(y -> y.name().equalsIgnoreCase(x)).findFirst()
        ).orElse(def);
    }

    private Optional<String> parameter(final String name) {
        return Optional.ofNullable(System.getProperty(segment + name));
    }

    private <A, B> Function<A, Optional<B>> noThrow(final Function<A, B> function) {
        return x -> {
            try {
                return Optional.of(function.apply(x));
            } catch (final Exception e) {
                log.error("failed to extract janet parameter value from {}", x, e);
                return Optional.empty();
            }
        };
    }
}
