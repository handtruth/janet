package com.handtruth.janet.core.impl.util.test;

import com.handtruth.janet.core.impl.util.Ipv6Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Ipv6AddressTest {

    @Test
    void successorTest() {
        final Ipv6Address a = new Ipv6Address(0, 0);
        assertEquals(new Ipv6Address(0, 1), a.successor());
        final Ipv6Address b = new Ipv6Address(0, -1);
        assertEquals(new Ipv6Address(1, 0), b.successor());
        final Ipv6Address c = new Ipv6Address(-1, -1);
        assertEquals(new Ipv6Address(0, 0), c.successor());
    }
}
