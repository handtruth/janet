package com.handtruth.janet.core.impl.network;

import com.handtruth.janet.core.api.network.IpAddressFilter;
import com.handtruth.janet.core.impl.util.Ipv6Address;

import java.net.InetAddress;

public class IpAddressFilterImpl implements IpAddressFilter {

    private final Modes mode;
    private final IpAddressSpace ipAddressSpace = new IpAddressSpace();

    public IpAddressFilterImpl(final Modes mode, final String definition) {
        this.mode = mode;
        this.ipAddressSpace.addMany(definition);
    }

    @Override
    public boolean isIpAddressAllowed(final InetAddress ipAddress) {
        return containsCondition(ipAddressSpace.contains(ipAddress));
    }

    @Override
    public boolean isIpAddressAllowed(final int ipAddress) {
        return containsCondition(ipAddressSpace.contains(ipAddress));
    }

    @Override
    public boolean isIpAddressAllowed(final long mostAddress, final long leastAddress) {
        final Ipv6Address address = new Ipv6Address(mostAddress, leastAddress);
        return containsCondition(ipAddressSpace.contains(address));
    }

    private boolean containsCondition(final boolean contains) {
        return switch (mode) {
            case ALLOW -> contains;
            case DENY -> !contains;
        };
    }

    public enum Modes {
        ALLOW, DENY
    }
}
