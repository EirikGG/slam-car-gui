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
public class TcpClient extends Thread {
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
    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
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
            System.out.println("TcpClient: Cant set PrintWriter");
        }
    }

    /**
     * Writes a string trough the socket.
     *
     * @param str String to send trough socket.
     */
    public void doWriteString(String str) {
        System.out.println(str); //TODO: Delete print statement.
        if (null != this.writer) {
            this.writer.println(str);
        } else {
            System.out.println("TcpClient: Cant send, PrintWriter is null");
        }
    }

    /**
     * Connects socket to a remote ip and port. If a socket exists, closes that one first.
     * @return True if connection is successful and false if not.
     */
    public void doConnect() {

        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.socket = new Socket(this.ip, this.port);

        } catch (IOException e) {
            this.socket = null;
            e.printStackTrace();
        }
    }
}
