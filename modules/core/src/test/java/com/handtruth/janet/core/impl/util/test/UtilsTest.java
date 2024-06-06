package com.handtruth.janet.core.impl.util.test;

import com.handtruth.janet.core.impl.util.Ipv6Address;
import com.handtruth.janet.core.impl.util.Successor;
import com.handtruth.janet.core.impl.util.Utils;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilsTest {

    @Test
    void wrapIp4AddressTest() {
        final Inet4Address address = Utils.wrapIp4Address(168296815);
        assertDoesNotThrow(() ->
                assertEquals(InetAddress.getByName("10.8.1.111"), address)
        );
    }

    @Test
    void wrapIpv6AddressTest() {
        final Ipv6Address input = new Ipv6Address(2665254114678557504L, -5050704633899807273L);
        final Inet6Address address = input.toInetAddress();
        assertDoesNotThrow(() ->
                assertEquals(InetAddress.getByName("24fc:e27f:590:4b40:b9e8:4ae5:2efb:8dd7"), address)
        );
    }

    @Test
    void surelyParseValidIpv4AddressTest() {
        final int address = Utils.surelyParseValidIpv4Address("10.8.1.111");
        assertEquals(168296815, address);
    }

    @Test
    void surelyParseValidIpv6AddressTest() {
        final Ipv6Address address = Utils.surelyParseValidIpv6Address("24fc:e27f:590:4b40:b9e8:4ae5:2efb:8dd7");
        final Ipv6Address expect = new Ipv6Address(2665254114678557504L, -5050704633899807273L);
        assertEquals(expect, address);
    }

    @Test
    void bytesToIpv4AddressTest() {
        final int address = Utils.bytesToIpv4Address(new byte[]{10, 8, 1, 111});
        assertEquals(168296815, address);
    }

    @Test
    void inet4AddressToIpv4AddressTest() throws Exception {
        final int address = Utils.inet4AddressToIpv4Address((Inet4Address) Inet4Address.getByName("10.8.1.111"));
        assertEquals(168296815, address);
    }

    @Test
    void inet6AddressToIpv6AddressTest() throws Exception {
        final Ipv6Address address = Ipv6Address.fromInetAddress((Inet6Address) Inet4Address.getByName("24fc:e27f:590:4b40:b9e8:4ae5:2efb:8dd7"));
        final Ipv6Address expect = new Ipv6Address(2665254114678557504L, -5050704633899807273L);
        assertEquals(expect, address);
    }

    @Test
    void indexOfTest() {
        final int index1 = Utils.indexOf("abcejfisnvrivoen", '8', 0);
        assertEquals(-1, index1);
        final int index2 = Utils.indexOf("abcejf8isnvrivoen", '8', 7);
        assertEquals(-1, index2);
    }

    @Test
    void ipv4AddressToStringTest() {
        final StringBuilder builder = new StringBuilder();
        Utils.ipv4AddressToString(builder, 168296815);
        assertEquals("10.8.1.111", builder.toString());
    }

    @Test
    void getIpv4SubnetByPrefixTest() {
        final int subnet1 = Utils.getIpv4SubnetByPrefix(23);
        assertEquals((int) 4294966784L, subnet1);
        assertThrows(IllegalArgumentException.class, () -> Utils.getIpv4SubnetByPrefix(-1));
        assertThrows(IllegalArgumentException.class, () -> Utils.getIpv4SubnetByPrefix(33));
        final int subnet2 = Utils.getIpv4SubnetByPrefix(32);
        assertEquals(-1, subnet2);
        final int subnet3 = Utils.getIpv4SubnetByPrefix(0);
        assertEquals(0, subnet3);
    }

    @Test
    void getIpv6SubnetByPrefixTest() {
        final Ipv6Address subnet1 = Utils.getIpv6SubnetByPrefix(121);
        assertEquals(-1L, subnet1.most());
        assertEquals(-128L, subnet1.least());
        assertThrows(IllegalArgumentException.class, () -> Utils.getIpv6SubnetByPrefix(-1));
        assertThrows(IllegalArgumentException.class, () -> Utils.getIpv6SubnetByPrefix(129));
        final Ipv6Address subnet2 = Utils.getIpv6SubnetByPrefix(128);
        assertEquals(-1L, subnet2.most());
        assertEquals(-1L, subnet2.least());
        final Ipv6Address subnet3 = Utils.getIpv6SubnetByPrefix(64);
        assertEquals(-1L, subnet3.most());
        assertEquals(0L, subnet3.least());
        final Ipv6Address subnet4 = Utils.getIpv6SubnetByPrefix(0);
        assertEquals(0L, subnet4.most());
        assertEquals(0L, subnet4.least());
        final Ipv6Address subnet5 = Utils.getIpv6SubnetByPrefix(34);
        assertEquals(-1073741824L, subnet5.most());
        assertEquals(0L, subnet5.least());
    }

    @Test
    void ipv4SuccessorTest() {
        final Successor<Integer> subject = Utils.IPV4_SUCCESSOR;
        assertEquals(1, subject.successor(0));
        assertEquals(0, subject.successor(-1));
    }
}
