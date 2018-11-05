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
    // Socket connection timeout in milliseconds.
    private int timeout;

    /**
     * Initializes with a new socket.
     */
    public TcpSocket(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.socket = new Socket();
        try {
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.timeout = 1;
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
     * Connects socket to a remote ip and port. If a socket exists, closes that one first.
     */
    public void doConnect() {
        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        InetSocketAddress address = doCreateInetSocketAddress(this.ip, this.port);
        try {
            this.socket.connect(address, this.timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns connection status of socket.
     * @returns Connection status of socket.
     */
    public boolean getIsConnected() {
        boolean isConnected = false;
        if (null != this.socket) {
            isConnected = this.socket.isConnected();
        }

        return isConnected;
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
