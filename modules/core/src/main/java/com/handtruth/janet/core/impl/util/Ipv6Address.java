package com.handtruth.janet.core.impl.util;

import java.net.Inet6Address;
import java.util.Comparator;

public record Ipv6Address(long most, long least) implements Comparable<Ipv6Address> {

    public static final Comparator<Ipv6Address> COMPARATOR =
            Comparator.comparing(Ipv6Address::most, Long::compareUnsigned)
                    .thenComparing(Ipv6Address::least, Long::compareUnsigned);

    public static Ipv6Address fromBytes(final byte[] bytes) {
        return Utils.bytesToIpv6Address(bytes);
    }

    public static Ipv6Address fromInetAddress(final Inet6Address address) {
        return Utils.inet6AddressToIpv6Address(address);
    }

    public Ipv6Address or(final Ipv6Address other) {
        return new Ipv6Address(this.most | other.most, this.least | other.least);
    }

    public Ipv6Address and(final Ipv6Address other) {
        return new Ipv6Address(this.most & other.most, this.least & other.least);
    }

    public Ipv6Address not() {
        return new Ipv6Address(~most, ~least);
    }

    @Override
    public int compareTo(final Ipv6Address other) {
        return COMPARATOR.compare(this, other);
    }

    public Ipv6Address successor() {
        if (least == -1L) {
            return new Ipv6Address(most + 1, 0L);
        } else {
            return new Ipv6Address(most, least + 1);
        }
    }

    public Inet6Address toInetAddress() {
        return Utils.wrapIp6Address(most, least);
    }

    @Override
    public String toString() {
        return toInetAddress().toString().substring(1);
    }
}
