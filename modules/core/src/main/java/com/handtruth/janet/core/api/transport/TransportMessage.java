package com.handtruth.janet.core.api.transport;

import com.handtruth.janet.core.api.network.NetworkLayer;

import java.nio.ByteBuffer;

public interface TransportMessage {

    byte DEFAULT_TTL = 64;

    /**
     * Gets stored TTL value.
     *
     * @return TTL value
     */
    byte getTtl();

    /**
     * Gets stored source IPv4 address.
     *
     * @return IPv4 source address
     */
    int getSrcIpv4Address();

    /**
     * Gets stored destination IPv4 address.
     *
     * @return IPv4 destination address
     */
    int getDstIpv4Address();

    /**
     * Gets transport layer data buffer
     *
     * @return transport layer data buffer
     */
    ByteBuffer getPayload();

    /**
     * Gets network protocol number
     *
     * @return network protocol number
     */
    short getNetworkProtocolNumber();

    /**
     * Checks if an IPv4 transport message stored in this object.
     *
     * @return true only if it is an IPv4 message
     */
    default boolean isIpv4() {
        return getNetworkProtocolNumber() == NetworkLayer.PROTOCOL_IPv4;
    }

    /**
     * Checks if an IPv6 transport message stored in this object.
     *
     * @return true only if it is an IPv6 message
     */
    default boolean isIpv6() {
        return getNetworkProtocolNumber() == NetworkLayer.PROTOCOL_IPv6;
    }

    /**
     * Updates network layer parameters for current transport message.
     *
     * @param networkProtocolNumber chosen network protocol number (use {@link NetworkLayer#PROTOCOL_IPv4} or
     *                              {@link NetworkLayer#PROTOCOL_IPv6} values)
     * @param srcIpAddressMost      part of a source IP address
     * @param srcIpAddressLeast     part of a source IP address
     * @param dstIpAddressMost      part of a destination IP address
     * @param dstIpAddressLeast     part of a destination IP address
     * @param ttl                   time to live value (for tracert functionality)
     */
    void update(
            final short networkProtocolNumber,
            final long srcIpAddressMost,
            final long srcIpAddressLeast,
            final long dstIpAddressMost,
            final long dstIpAddressLeast,
            final byte ttl
    );

    /**
     * Updates network layer parameters for current transport message assuming IPv4 network protocol.
     *
     * @param srcIpAddress source IP address
     * @param dstIpAddress destination IP address
     * @param ttl          time to live value (for tracert functionality)
     */
    default void updateIpv4(
            final int srcIpAddress,
            final int dstIpAddress,
            final byte ttl
    ) {
        update(
                NetworkLayer.PROTOCOL_IPv4,
                0,
                Integer.toUnsignedLong(srcIpAddress),
                0,
                Integer.toUnsignedLong(dstIpAddress),
                ttl
        );
    }

    /**
     * Updates network layer parameters for current transport message and sets the default TTL value assuming IPv4
     * network protocol.
     *
     * @param srcIpAddress source IP address
     * @param dstIpAddress destination IP address
     */
    default void updateIpv4(
            final int srcIpAddress,
            final int dstIpAddress
    ) {
        updateIpv4(srcIpAddress, dstIpAddress, DEFAULT_TTL);
    }
}
