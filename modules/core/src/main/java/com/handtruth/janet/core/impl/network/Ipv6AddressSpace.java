package com.handtruth.janet.core.impl.network;

import com.handtruth.janet.core.impl.util.Space;
import com.handtruth.janet.core.impl.util.Ipv6Address;

public class Ipv6AddressSpace extends Space<Ipv6Address> {

    public Ipv6AddressSpace() {
        super(Ipv6Address::successor, Ipv6Address::compareTo);
    }
}
