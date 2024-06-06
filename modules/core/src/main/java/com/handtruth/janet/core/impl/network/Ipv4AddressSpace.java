package com.handtruth.janet.core.impl.network;

import com.handtruth.janet.core.impl.util.Space;
import com.handtruth.janet.core.impl.util.Utils;

public class Ipv4AddressSpace extends Space<Integer> {

    public Ipv4AddressSpace() {
        super(Utils.IPV4_SUCCESSOR, Integer::compare);
    }

    @Override
    protected void elementToString(StringBuilder builder, Integer element) {
        Utils.ipv4AddressToString(builder, element);
    }
}
