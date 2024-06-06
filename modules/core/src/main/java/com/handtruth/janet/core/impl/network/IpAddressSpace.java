package com.handtruth.janet.core.impl.network;

import com.handtruth.janet.core.impl.util.Ipv6Address;
import com.handtruth.janet.core.impl.util.Utils;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressSpace {

    private final Ipv4AddressSpace ipv4AddressSpace = new Ipv4AddressSpace();

    private final Ipv6AddressSpace ipv6AddressSpace = new Ipv6AddressSpace();

    private static final String IPV4ADDRESS_PATTERN =
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}";
    private static final String IPV6ADDRESS_PATTERN =
            "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";
    private static final String IPADDRESS_PATTERN = group("v4", IPV4ADDRESS_PATTERN) + "|" + group("v6", IPV6ADDRESS_PATTERN);
    public static final Pattern ipAddressPattern =
            line(group("ip", IPADDRESS_PATTERN));
    private static final Pattern ipv4RangePattern =
            line(group("start", IPV4ADDRESS_PATTERN) + "-" + group("end", IPV4ADDRESS_PATTERN));
    private static final Pattern ipv6RangePattern =
            line(group("start", IPV6ADDRESS_PATTERN) + "-" + group("end", IPV6ADDRESS_PATTERN));
    private static final Pattern subnetPattern =
            line(group("ip", IPADDRESS_PATTERN) + "\\/" + group("prefix", "[0-9]|[1-9][0-9]|1[01][0-9]|12[0-8]"));
    private static final Pattern interfaceNamePattern =
            line("@" + group("name", "[a-zA-Z].*"));
    private static final Pattern interfaceIdPattern =
            line("@" + group("id", "\\d*"));

    private static Pattern line(final String pattern) {
        return Pattern.compile('^' + pattern + '$');
    }

    private static String group(final String name, final String pattern) {
        return "(?<" + name + ">" + pattern + ")";
    }

    public boolean addMany(final String string) {
        return Arrays.stream(string.split(","))
                .reduce(false, (acc, element) -> add(element.trim()) || acc, (a, b) -> b);
    }

    public boolean add(final CharSequence string) {
        final Matcher ipAddressMatch = ipAddressPattern.matcher(string);
        if (ipAddressMatch.matches()) {
            final String ipv4AddressString = ipAddressMatch.group("v4");
            if (ipv4AddressString != null) {
                final int ipv4Address = Utils.surelyParseValidIpv4Address(ipv4AddressString);
                return ipv4AddressSpace.add(ipv4Address);
            } else {
                final String ipv6AddressString = ipAddressMatch.group("v6");
                assert ipv6AddressString != null;
                final Ipv6Address ipv6Address = Utils.surelyParseValidIpv6Address(ipv6AddressString);
                return ipv6AddressSpace.add(ipv6Address);
            }
        }

        final Matcher ipv4RangeMatch = ipv4RangePattern.matcher(string);
        if (ipv4RangeMatch.matches()) {
            final String rangeStartString = ipv4RangeMatch.group("start");
            assert rangeStartString != null;
            final String rangeEndString = ipv4RangeMatch.group("end");
            assert rangeEndString != null;
            final int rangeStart = Utils.surelyParseValidIpv4Address(rangeStartString);
            final int rangeEnd = Utils.surelyParseValidIpv4Address(rangeEndString);
            return ipv4AddressSpace.addRange(rangeStart, rangeEnd);
        }

        final Matcher ipv6RangeMatch = ipv6RangePattern.matcher(string);
        if (ipv6RangeMatch.matches()) {
            final String rangeStartString = ipv6RangeMatch.group("start");
            assert rangeStartString != null;
            final String rangeEndString = ipv6RangeMatch.group("end");
            assert rangeEndString != null;
            final Ipv6Address rangeStart = Utils.surelyParseValidIpv6Address(rangeStartString);
            final Ipv6Address rangeEnd = Utils.surelyParseValidIpv6Address(rangeEndString);
            return ipv6AddressSpace.addRange(rangeStart, rangeEnd);
        }

        final Matcher subnetMatch = subnetPattern.matcher(string);
        if (subnetMatch.matches()) {
            final int prefix = Integer.parseInt(subnetMatch.group("prefix"));
            final String ipv4AddressString = subnetMatch.group("v4");
            if (ipv4AddressString != null) {
                final int ipv4Address = Utils.surelyParseValidIpv4Address(ipv4AddressString);
                return addIpv4Subnet(ipv4Address, prefix);
            } else {
                final String ipv6AddressString = subnetMatch.group("v6");
                assert ipv6AddressString != null;
                final Ipv6Address ipv6Address = Utils.surelyParseValidIpv6Address(ipv6AddressString);
                return addIpv6Subnet(ipv6Address, prefix);
            }
        }

        final Matcher interfaceNameMatch = interfaceNamePattern.matcher(string);
        if (interfaceNameMatch.matches()) {
            final String interfaceName = interfaceNameMatch.group("name");
            try {
                final NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
                return addNullableNetworkInterface(networkInterface);
            } catch (final SocketException e) {
                throw new IllegalStateException("Failed to get a network interface by name", e);
            }
        }

        final Matcher interfaceIdMatch = interfaceIdPattern.matcher(string);
        if (interfaceIdMatch.matches()) {
            final int interfaceId = Integer.parseInt(interfaceIdMatch.group("id"));
            try {
                final NetworkInterface networkInterface = NetworkInterface.getByIndex(interfaceId);
                return addNullableNetworkInterface(networkInterface);
            } catch (final SocketException e) {
                throw new IllegalStateException("Failed to get a network interface by index", e);
            }
        }

        // Assume it is a hostname
        try {
            final InetAddress[] addresses = InetAddress.getAllByName(string.toString());
            boolean result = false;
            for (final InetAddress address : addresses) {
                if (address instanceof Inet4Address inet4Address) {
                    final int ipAddress = Utils.inet4AddressToIpv4Address(inet4Address);
                    result = ipv4AddressSpace.add(ipAddress) || result;
                } else if (address instanceof Inet6Address inet6Address) {
                    final Ipv6Address ipAddress = Ipv6Address.fromInetAddress(inet6Address);
                    result = ipv6AddressSpace.add(ipAddress) || result;
                }
            }
            return result;
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private boolean addNullableNetworkInterface(@Nullable final NetworkInterface networkInterface) {
        if (networkInterface == null) {
            throw new IllegalArgumentException("Network interface not found");
        }
        return addNetworkInterface(networkInterface);
    }

    public boolean addNetworkInterface(final NetworkInterface networkInterface) {
        boolean result = false;
        for (final InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
            final InetAddress inetAddress = address.getAddress();
            if (inetAddress instanceof Inet4Address inet4Address) {
                final int ipAddress = Utils.inet4AddressToIpv4Address(inet4Address);
                result = addIpv4Subnet(ipAddress, address.getNetworkPrefixLength()) || result;
            } else if (inetAddress instanceof Inet6Address inet6Address) {
                final Ipv6Address ipAddress = Ipv6Address.fromInetAddress(inet6Address);
                result = addIpv6Subnet(ipAddress, address.getNetworkPrefixLength()) || result;
            }
        }
        return result;
    }

    public boolean addIpv4Subnet(final int ipAddress, final int prefix) {
        final int subnet = Utils.getIpv4SubnetByPrefix(prefix);
        final int rangeStart = ipAddress & subnet;
        final int rangeEnd = ipAddress | ~subnet;
        return ipv4AddressSpace.addRange(rangeStart, rangeEnd);
    }

    public boolean addIpv6Subnet(final Ipv6Address ipAddress, final int prefix) {
        final Ipv6Address subnet = Utils.getIpv6SubnetByPrefix(prefix);
        final Ipv6Address rangeStart = ipAddress.and(subnet);
        final Ipv6Address rangeEnd = ipAddress.or(subnet.not());
        return ipv6AddressSpace.addRange(rangeStart, rangeEnd);
    }

    public boolean contains(final int ipv4Address) {
        return ipv4AddressSpace.contains(ipv4Address);
    }

    public boolean contains(final Ipv6Address ipv6Address) {
        return ipv6AddressSpace.contains(ipv6Address);
    }

    public boolean contains(final InetAddress address) {
        if (address instanceof Inet4Address inet4Address) {
            return contains(Utils.inet4AddressToIpv4Address(inet4Address));
        } else {
            return contains(Ipv6Address.fromInetAddress((Inet6Address) address));
        }
    }

    @Override
    public String toString() {
        return "{ipv4:" + ipv4AddressSpace + "; ipv6:" + ipv6AddressSpace + "}";
    }
}
