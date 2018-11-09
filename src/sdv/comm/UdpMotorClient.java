package sdv.comm;

import java.io.IOException;
import java.net.*;

/**
 * Connects to a socket and communicates with it.
 *
 * @author Eirik G. Gustafsson
 * @version 03.11.2018.
 */
public class UdpMotorClient extends Thread {
    // Classes tcp socket.
    private DatagramSocket socket;
    // Sockets ip.
    private String ip;
    // Sockets port.
    private int port;

    /**
     * Initializes with a new socket.
     */
    public UdpMotorClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        doCloseSocket();
        doCreateSocket();
        doConnect(this.ip, this.port);
    }

    /**
     * Writes a string trough the socket.
     *
     * @param str String to send trough socket.
     */
    public void doWriteString(String str) {
        if (null != this.socket) {
            try {
                this.socket.send(new DatagramPacket(str.getBytes(), str.length()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(str);
        } else {
            System.out.println("UdpMotorClient: Cant send, PrintWriter is null");
        }
    }

    /**
     * Connects socket to a remote ip and port. If a socket exists, closes that one first.
     */
    private void doConnect(String ip, int port) {
        this.socket.connect(getInetAddress(ip), port);

        System.out.println("Socket connected to " + this.socket.getInetAddress().toString()
                + ";" + this.socket.getPort());
    }

    /**
     * Closes old datagramSocket and creates a new one.
     */
    private void doCreateSocket() {
        try {
            this.socket = new DatagramSocket();

        } catch (IOException e) {
            this.socket = null;
            System.out.println("UdpMotorClient: Cant create socket");
        }
    }

    /**
     * @return Connection status of socket.
     */
    public boolean getIsConnected() {
        boolean result = false;
        if (null != this.socket)
            result = this.socket.isConnected();

        return result;
    }

    /**
     * Closes socket.
     */
    public void doCloseSocket() {
        if (null != this.socket) {
            this.socket.close();
        }
    }

    /**
     * Creates a InetAddress from param ip.
     * @param ip String representation of ip.
     * @return InetAddress.
     */
    private InetAddress getInetAddress(String ip) {
        InetAddress address = null;

        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return address;
    }
}
