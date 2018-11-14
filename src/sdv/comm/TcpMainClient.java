package sdv.comm;

import sdv.gui.controller.control.sys.ControlSys;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Eirik G. Gustafsson
 * @version 13.11.2018.
 */
public class TcpMainClient extends Thread {
    // Property change support, reports change in connection status.
    private PropertyChangeSupport pcs;
    // Connection status flag.
    private boolean isConnected;
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
    public TcpMainClient(ControlSys controlSys, String ip, int port) {
        this.pcs = new PropertyChangeSupport(this);
        this.pcs.addPropertyChangeListener(controlSys);
        this.isConnected = false;
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
            System.out.println("TcpMainClient: " + e.getMessage());
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
            this.pcs.firePropertyChange("MAIN:CONNECTION", this.isConnected, false);
            this.isConnected = false;
            System.out.println("TcpMotorClient: Cant send, socket is probably null");
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
                System.out.println("TcpMainClient: " + e.getMessage());
            }
        }

        try {
            this.socket = new Socket(this.ip, this.port);

            System.out.println("Socket connected to " + this.socket.getInetAddress().toString()
                    + ";" + this.socket.getPort());
            this.pcs.firePropertyChange("MAIN:CONNECTION", this.isConnected, true);
            this.isConnected = true;

            setPrintWriter();

        } catch (IOException e) {
            this.socket = null;
            System.out.println("TcpMainClient: " + e.getMessage());
        }
    }

    /**
     * Closes socket.
     */
    public void doCloseSocket() {
        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                System.out.println("TcpMainClient: " + e.getMessage());
            }
            this.pcs.firePropertyChange("MAIN:CONNECTION", this.isConnected, false);
            this.isConnected = false;
        }
    }
}
