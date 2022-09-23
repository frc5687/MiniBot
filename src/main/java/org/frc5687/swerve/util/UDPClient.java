// mostly copied from https://www.baeldung.com/udp-in-java

package org.frc5687.swerve.util;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public UDPClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, address, 5687);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
          packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }
}
