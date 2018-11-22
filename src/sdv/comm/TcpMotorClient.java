package sdv.comm;

import sdv.gui.controller.control.sys.ControlSys;

import java.beans.PropertyChangeSupport;
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
    // Property change support, reports change in STATUS status.
    private PropertyChangeSupport pcs;
    // Classes starting status.
    private String status;
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
    public TcpMotorClient(String ip, int port, ControlSys sys) {
        this.pcs = new PropertyChangeSupport(this);
        this.pcs.addPropertyChangeListener(sys);
        this.ip = ip;
        this.port = port;
        this.status = "DISCONNECTED";
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
            System.out.println("TcpMotorClient: " + e.getMessage());
            this.pcs.firePropertyChange("MOTOR:STATUS", this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
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
            this.pcs.firePropertyChange("MOTOR:STATUS", this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }
    }

    /**
     * Connects socket to a remote ip and port. If a socket exists, closes that one first.
     *
     * @return True if connection is successful and false if not.
     */
    private void doConnect() {
        this.pcs.firePropertyChange("MOTOR:STATUS", this.status, "LOADING");
        this.status = "LOADING";

        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                System.out.println("TcpMotorClient: " + e.getMessage());
            }
        }

        try {
            this.socket = new Socket(this.ip, this.port);

            System.out.println("Socket connected to " + this.socket.getInetAddress().toString()
                    + ";" + this.socket.getPort());

            setPrintWriter();
            this.pcs.firePropertyChange("MOTOR:STATUS", this.status, "CONNECTED");
            this.status = "CONNECTED";

        } catch (IOException e) {
            this.socket = null;
            System.out.println("TcpMotorClient: " + e.getMessage());
            this.pcs.firePropertyChange("MOTOR:STATUS", this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
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
                System.out.println("TcpMotorClient: " + e.getMessage());
            }
            this.pcs.firePropertyChange("MOTOR:STATUS", this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }
    }
}