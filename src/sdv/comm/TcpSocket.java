package sdv.comm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Connects to a socket and communicates with it.
 *
 * @author Eirik G. Gustafsson
 * @version 03.11.2018.
 */
public class TcpSocket {
    // Classes tcp socket.
    private Socket socket;
    // Sockets output stream.

    /**
     * Initializes with a new socket.
     */
    public TcpSocket(String ip, int port, int localPort) {
        this.socket = new Socket();
        doBindSocket(localPort);
        doConnectSocket(ip, port);
    }

    /**
     * Binds the socket to a local port with loopback address.
     * @param port
     */
    private void doBindSocket(int port) {
        InetSocketAddress address = doCreateInetSocketAddress(null, port);
        try {
            this.socket.bind(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects socket to a remote ip and port.
     *
     * @param ip Ip address of remote socket.
     * @param port Port of remote socket.
     */
    private void doConnectSocket(String ip, int port) {
        InetSocketAddress address = doCreateInetSocketAddress(ip, port);
        try {
            this.socket.connect(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a InetSocketAddress.
     *
     * @param ip IP address.
     * @param port Port number.
     * @return InetSocketAddress.
     */
    private InetSocketAddress doCreateInetSocketAddress(String ip, int port) {
        InetSocketAddress address = null;
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            address = new InetSocketAddress(inetAddress, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address;
    }
}
