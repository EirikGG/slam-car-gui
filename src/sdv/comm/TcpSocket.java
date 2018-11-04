package sdv.comm;

import java.io.IOException;
import java.io.PrintWriter;
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
    // Writer for socket comm.
    private PrintWriter writer;
    // Sockets ip.
    private String ip;
    // Sockets port.
    private int port;

    /**
     * Initializes with a new socket.
     */
    public TcpSocket(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.socket = new Socket();
        doConnectSocket();
        try {
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a string trough the socket.
     *
     * @param str String to send trough socket.
     */
    public void doWriteString(String str) {
        System.out.println(str); //TODO: Delete print statement.
        this.writer.println(str);
    }

    /**
     * Connects socket to a remote ip and port.
     */
    public void doConnectSocket() {
        InetSocketAddress address = doCreateInetSocketAddress(this.ip, this.port);
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

    /**
     * Returns socket's connected status.
     * @return Socket's connected status.
     */
    public boolean getIsConnected() {
        return this.socket.isConnected();
    }
}
