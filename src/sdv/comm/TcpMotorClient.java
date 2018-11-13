package sdv.comm;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Connects to a socket and communicates with it.
 *
 * @author Eirik G. Gustafsson
 * @version 03.11.2018.
 */
public class TcpMotorClient extends Thread {
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
    public TcpMotorClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        doCloseSocket();
        this.socket = new Socket();
        doConnect();
        setPrintWriter();
    }

    /**
     * Setts the PrintWriter field.
     */
    private void setPrintWriter() {
        try {
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("TcpMotorClient: Cant set PrintWriter");
        }
    }

    /**
     * Writes a string trough the socket.
     *
     * @param str String to send trough socket.
     */
    public void doWriteString(String str) {
        if (null != this.writer) {
            this.writer.println(str);
            System.out.println(str);
        } else {
            System.out.println("TcpMotorClient: Cant send, PrintWriter is null");
        }
    }

    /**
     * Connects socket to a remote ip and port. If a socket exists, closes that one first.
     *
     * @return True if connection is successful and false if not.
     */
    private void doConnect() {

        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.socket = new Socket(this.ip, this.port);

            System.out.println("Socket connected to " + this.socket.getInetAddress().toString()
                    + ";" + this.socket.getPort());

            setPrintWriter();

        } catch (IOException e) {
            this.socket = null;
            System.out.println("TcpMotorClient: Cant connect socket");
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
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}