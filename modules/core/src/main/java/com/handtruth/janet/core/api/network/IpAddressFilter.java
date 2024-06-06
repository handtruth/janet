package com.handtruth.janet.core.api.network;

import com.handtruth.janet.core.impl.network.IpAddressFilterImpl;
import com.handtruth.janet.core.impl.util.Utils;

import java.net.InetAddress;

@FunctionalInterface
public interface IpAddressFilter {

    static IpAddressFilter createAllowFilter(final String definition) {
        return new IpAddressFilterImpl(IpAddressFilterImpl.Modes.ALLOW, definition);
    }

    static IpAddressFilter createDenyFilter(final String definition) {
        return new IpAddressFilterImpl(IpAddressFilterImpl.Modes.DENY, definition);
    }

    boolean isIpAddressAllowed(InetAddress ipAddress);

    default boolean isIpAddressAllowed(final int ipAddress) {
        return isIpAddressAllowed(Utils.wrapIp4Address(ipAddress));
    }

    default boolean isIpAddressAllowed(final long mostAddress, final long leastAddress) {
        return isIpAddressAllowed(Utils.wrapIp6Address(mostAddress, leastAddress));
    }
}
