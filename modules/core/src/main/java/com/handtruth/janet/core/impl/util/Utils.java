package com.handtruth.janet.core.impl.util;

import lombok.SneakyThrows;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {

    @SneakyThrows(UnknownHostException.class)
    public static Inet4Address wrapIp4Address(final int ipAddress) {
        final byte[] bytes = new byte[] {
            (byte) (ipAddress >> 24), (byte) (ipAddress >> 16), (byte) (ipAddress >> 8), (byte) ipAddress
        };
        return (Inet4Address) InetAddress.getByAddress(bytes);
    }

    @SneakyThrows(UnknownHostException.class)
    public static Inet6Address wrapIp6Address(final long mostAddress, final long leastAddress) {
        final byte[] bytes = new byte[] {
                (byte) (mostAddress >> 56), (byte) (mostAddress >> 48), (byte) (mostAddress >> 40), (byte) (mostAddress >> 32),
                (byte) (mostAddress >> 24), (byte) (mostAddress >> 16), (byte) (mostAddress >> 8), (byte) (mostAddress),
                (byte) (leastAddress >> 56), (byte) (leastAddress >> 48), (byte) (leastAddress >> 40), (byte) (leastAddress >> 32),
                (byte) (leastAddress >> 24), (byte) (leastAddress >> 16), (byte) (leastAddress >> 8), (byte) (leastAddress),
        };
        return (Inet6Address) InetAddress.getByAddress(bytes);
    }

    public static int surelyParseValidIpv4Address(final CharSequence string) {
        int position = 0;
        int address = 0;
        for (int i = 0; i < 3; ++i) {
            final int segmentEnd = indexOf(string, '.', position);
            address = (address << 8) | Integer.parseUnsignedInt(string, position, segmentEnd, 10);
            position = segmentEnd + 1;
        }
        return (address << 8) | Integer.parseUnsignedInt(string, position, string.length(), 10);
    }

    @SneakyThrows
    public static Ipv6Address surelyParseValidIpv6Address(final CharSequence string) {
        final InetAddress inetAddress = InetAddress.getByName(string.toString());
        final byte[] bytes = inetAddress.getAddress();
        return Ipv6Address.fromBytes(bytes);
    }

    public static int bytesToIpv4Address(final byte[] bytes) {
        return (Byte.toUnsignedInt(bytes[0]) << 24) | (Byte.toUnsignedInt(bytes[1]) << 16) |
                (Byte.toUnsignedInt(bytes[2]) << 8) | Byte.toUnsignedInt(bytes[3]);
    }

    public static int inet4AddressToIpv4Address(final Inet4Address address) {
        return bytesToIpv4Address(address.getAddress());
    }

    public static Ipv6Address bytesToIpv6Address(final byte[] bytes) {
        final long most = combineLong(bytes, 7);
        final long least = combineLong(bytes, 15);
        return new Ipv6Address(most, least);
    }

    public static Ipv6Address inet6AddressToIpv6Address(final Inet6Address address) {
        return bytesToIpv6Address(address.getAddress());
    }

    private static long combineLong(final byte[] bytes, final int offset) {
        return byteOf(bytes, offset - 7, 56) | byteOf(bytes, offset - 6, 48) | byteOf(bytes, offset - 5, 40)
                | byteOf(bytes, offset - 4, 32) | byteOf(bytes, offset - 3, 24) | byteOf(bytes, offset - 2, 16)
                | byteOf(bytes, offset - 1, 8) | byteOf(bytes, offset, 0);
    }

    private static long byteOf(final byte[] bytes, final int i, final int n) {
        return Byte.toUnsignedLong(bytes[i]) << n;
    }

//    public static int parseIpv4Address(final CharSequence string) throws AddressParseException {
//        if (!Ipv4Space.ipAddressPattern.matcher(string).matches()) {
//            throw new AddressParseException("Not an IPv4 address: " + string);
//        }
//        return surelyParseValidIpv4Address(string);
//    }

    public static int indexOf(final CharSequence string, final char character, final int start) {
        for (int i = start, length = string.length(); i < length; ++i) {
            if (string.charAt(i) == character) {
                return i;
            }
        }
        return -1;
    }

    public static void ipv4AddressToString(final StringBuilder builder, final int ipAddress) {
        builder.append(Integer.toUnsignedString(ipAddress >>> 24));
        builder.append('.');
        builder.append(Integer.toUnsignedString((ipAddress >>> 16) & 0xFF));
        builder.append('.');
        builder.append(Integer.toUnsignedString((ipAddress >>> 8) & 0xFF));
        builder.append('.');
        builder.append(Integer.toUnsignedString(ipAddress & 0xFF));
    }

    public static int getIpv4SubnetByPrefix(final int prefix) {
        if (prefix > 32 || prefix < 0) {
            throw new IllegalArgumentException("Wrong subnet prefix range");
        }
        return (int) (-1L << (32 - prefix));
    }

    public static Ipv6Address getIpv6SubnetByPrefix(final int prefix) {
        if (prefix > 128 || prefix < 0) {
            throw new IllegalArgumentException("Wrong subnet prefix range");
        }
        final long most, least;
        if (prefix == 0) {
            most = 0L;
            least = 0L;
        } else if (prefix <= 64) {
            most = -1L << (64 - prefix);
            least = 0;
        } else {
            most = -1;
            least = -1L << (128 - prefix);
        }
        return new Ipv6Address(most, least);
    }

    public static final Successor<Integer> IPV4_SUCCESSOR = x -> x + 1;
}
