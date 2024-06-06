package com.handtruth.janet.core.impl.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Space<T> {

    private final Successor<T> successor;

    private final NavigableMap<T, T> ranges;

    public static <T> Space<T> create(final Successor<T> successor, final Comparator<? super T> comparator) {
        return new Space<>(successor, comparator);
    }

    public static <T extends Comparable<T>> Space<T> create(final Successor<T> successor) {
        return create(successor, T::compareTo);
    }

    public Space(final Successor<T> successor, final Comparator<? super T> comparator) {
        this.successor = successor;
        this.ranges = new TreeMap<>(comparator);
    }

    public final boolean add(final T element) {
        return addRange(element, element);
    }

    public final boolean addRange(final T begin, final T end) {
        final Comparator<? super T> comparator = ranges.comparator();
        if (comparator.compare(end, begin) < 0) {
            return addRange(end, begin);
        }

        // Remove all ranges between begin and end
        ranges.subMap(begin, false, end, false).entrySet()
                .removeIf(range -> comparator.compare(range.getValue(), end) < 0);

        final Map.Entry<T, T> floorBegin = ranges.floorEntry(begin);
        final Map.Entry<T, T> higherBegin = ranges.higherEntry(begin);

        if (floorBegin != null
                && comparator.compare(floorBegin.getValue(), end) >= 0) {
            // Already exists in the space
            // [---------]
            // [---------]
            //   [---]
            // [---------]
            return false;
        } else if (floorBegin != null && higherBegin != null
                && comparator.compare(successor.successor(floorBegin.getValue()), begin) >= 0
                && comparator.compare(successor.successor(end), higherBegin.getKey()) >= 0) {
            // Remove whitespace between 2 ranges
            // [---------]      [----------]
            //            [----]
            // [---------------------------]
            // [---------------]
            //            [----------------]
            ranges.entrySet().remove(higherBegin);
            ranges.put(floorBegin.getKey(), higherBegin.getValue());
        } else if (higherBegin != null
                && comparator.compare(successor.successor(end), higherBegin.getKey()) >= 0) {
            // Change higher range start position
            // [---------]       [---------]
            //             [----]
            //             [---------------]
            ranges.entrySet().remove(higherBegin);
            ranges.put(begin, higherBegin.getValue());
        } else if (floorBegin != null
                && comparator.compare(successor.successor(floorBegin.getValue()), begin) >= 0) {
            // New range after some other range
            // [---------]
            //            [--------]
            // [-------------------]
            ranges.put(floorBegin.getKey(), end);
        } else {
            // New range in empty space or new range before all others
            //          [---------]
            // [-----]
            ranges.put(begin, end);
        }
        return true;
    }

    public final boolean contains(final T element) {
        final Map.Entry<T, T> floorRange = ranges.floorEntry(element);
        if (floorRange == null) {
            return false;
        }
        final Comparator<? super T> comparator = ranges.comparator();
        return comparator.compare(element, floorRange.getValue()) <= 0;
    }

    public final boolean isEmpty() {
        return ranges.isEmpty();
    }

    public final int rangeCount() {
        return ranges.size();
    }

    protected void elementToString(final StringBuilder builder, final T element) {
        builder.append(element);
    }

    private void appendRangeToString(final StringBuilder builder, final Map.Entry<T, T> range) {
        final T begin = range.getKey();
        final T end = range.getValue();
        elementToString(builder, begin);
        if (begin != end) {
            builder.append('-');
            elementToString(builder, range.getValue());
        }
    }

    @Override
    public String toString() {
        final Iterator<Map.Entry<T, T>> iterator = ranges.entrySet().iterator();
        if (iterator.hasNext()) {
            final StringBuilder builder = new StringBuilder();
            builder.append('[');
            final Map.Entry<T, T> first = iterator.next();
            appendRangeToString(builder, first);
            while (iterator.hasNext()) {
                builder.append(", ");
                final Map.Entry<T, T> range = iterator.next();
                appendRangeToString(builder, range);
            }
            builder.append(']');
            return builder.toString();
        } else {
            return "[]";
        }
    }
}
