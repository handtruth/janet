package com.handtruth.janet.core.impl.network.test;

import com.handtruth.janet.core.impl.network.IpAddressSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IpAddressSpaceTest {

    private IpAddressSpace subject;

    @BeforeEach
    void initializeSubject() {
        subject = new IpAddressSpace();
    }

    @Test
    void addIpv4Address() throws Exception {
        assertTrue(subject.add("10.8.1.111"));
        assertFalse(subject.add("10.8.1.111"));
        assertTrue(subject.contains(InetAddress.getByName("10.8.1.111")));
        assertFalse(subject.contains(InetAddress.getByName("10.8.1.112")));
        assertFalse(subject.contains(InetAddress.getByName("10.8.1.110")));
    }

    @Test
    void addIpv6Address() throws Exception {
        assertTrue(subject.add("fe80::1ff:fe23:4567:890a%3"));
        assertFalse(subject.add("fe80::1ff:fe23:4567:890a%3"));
        assertTrue(subject.contains(InetAddress.getByName("fe80::1ff:fe23:4567:890a%3")));
        assertFalse(subject.contains(InetAddress.getByName("fe80::1ff:fe23:4567:890b%3")));
        assertFalse(subject.contains(InetAddress.getByName("fe80::1ff:fe23:4567:8909%3")));
    }

    @Test
    void addIpv4Range() throws Exception {
        assertTrue(subject.add("10.8.1.111-10.7.1.13"));
        assertFalse(subject.add("10.8.1.1"));
        assertFalse(subject.add("10.8.1.1-10.8.1.10"));
        assertTrue(subject.contains(InetAddress.getByName("10.8.1.111")));
        assertTrue(subject.contains(InetAddress.getByName("10.7.1.13")));
        assertTrue(subject.contains(InetAddress.getByName("10.8.1.1")));
        assertFalse(subject.contains(InetAddress.getByName("10.7.1.12")));
    }

    @Test
    void addIpv6Range() throws Exception {
        assertTrue(subject.add("2001:db8:85a3:8d3:1319:8a2e:370:7348-2001:db8:85a3:8d3:1319:8a2e:370:9348"));
        assertFalse(subject.add("2001:db8:85a3:8d3:1319:8a2e:370:7748"));
        assertFalse(subject.add("2001:db8:85a3:8d3:1319:8a2e:370:8348-2001:db8:85a3:8d3:1319:8a2e:370:8748"));
        assertTrue(subject.contains(InetAddress.getByName("2001:db8:85a3:8d3:1319:8a2e:370:7348")));
        assertTrue(subject.contains(InetAddress.getByName("2001:db8:85a3:8d3:1319:8a2e:370:9348")));
        assertTrue(subject.contains(InetAddress.getByName("2001:db8:85a3:8d3:1319:8a2e:370:7748")));
        assertFalse(subject.contains(InetAddress.getByName("2001:db8:85a3:8d3:1319:8a2e:370:7347")));
    }

    @Test
    void addIpv4Subnet() throws Exception {
        assertTrue(subject.add("10.8.0.1/25"));
        assertFalse(subject.add("10.8.0.1/26"));
        assertFalse(subject.add("10.8.0.3"));
        assertTrue(subject.contains(InetAddress.getByName("10.8.0.1")));
        assertFalse(subject.contains(InetAddress.getByName("10.7.1.12")));
    }

    @Test
    void addIpv6Subnet() throws Exception {
        assertTrue(subject.add("2001:db8:a::/64"));
        assertFalse(subject.add("2001:db8:a::/120"));
        assertFalse(subject.add("2001:db8:a::123"));
        assertTrue(subject.contains(InetAddress.getByName("2001:db8:a::123")));
        assertFalse(subject.contains(InetAddress.getByName("2001:db8:b::123")));
    }

    @Test
    void addInterfaceByName() throws Exception {
        assertTrue(subject.add("@lo"));
        assertFalse(subject.add("@lo"));
        assertTrue(subject.contains(InetAddress.getByName("127.0.0.1")));
        assertTrue(subject.contains(InetAddress.getByName("::1")));
        assertThrows(IllegalArgumentException.class, () -> subject.add("@popka"));
    }

    @Test
    void addInterfaceById() throws Exception {
        assertTrue(subject.add("@1"));
        assertFalse(subject.add("@1"));
        assertTrue(subject.contains(InetAddress.getByName("127.0.0.1")));
        assertTrue(subject.contains(InetAddress.getByName("::1")));
        assertThrows(IllegalArgumentException.class, () -> subject.add("@10000000"));
    }

    @Test
    void addInterfaceByHostname() throws Exception {
        assertTrue(subject.add("handtruth.com"));
        assertFalse(subject.add("unknown.mc.handtruth.com"));
        assertTrue(subject.contains(InetAddress.getByName("handtruth.com")));
        assertFalse(subject.contains(InetAddress.getByName("::1")));
    }

    @Test
    void addInterfaceByHostname2() throws Exception {
        assertTrue(subject.add("google.com"));
        assertFalse(subject.add("google.com"));
        for (final InetAddress address : InetAddress.getAllByName("google.com")) {
            assertTrue(subject.contains(address));
        }
    }

    @Test
    void addMany() {
        assertTrue(subject.addMany("127.0.0.1, 10.8.0.1/24, 2001:db8:a::/64, fe80::1ff:fe23:4567:890a%3, @lo"));
        assertFalse(subject.addMany("127.0.0.1, 10.8.0.1/24, 2001:db8:a::/64, fe80::1ff:fe23:4567:890a%3, @lo"));
        final String expected = "{ipv4:[10.8.0.0-10.8.0.255, 127.0.0.0-127.255.255.255]; ipv6:[0:0:0:0:0:0:0:1-0:0:0:0:0:0:0:1, 2001:db8:a:0:0:0:0:0-2001:db8:a:0:ffff:ffff:ffff:ffff, fe80:0:0:0:1ff:fe23:4567:890a]}";
        assertEquals(expected, subject.toString());
    }
}
