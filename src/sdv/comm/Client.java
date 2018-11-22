package sdv.comm;

import sdv.gui.controller.control.sys.ControlSys;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Abstract class for a TCP client socket connection.
 * @author Eirik G. Gustafsson
 * @version 22.11.2018.
 */
public abstract class Client {
    // Property change support, reports change in STATUS status.
    private PropertyChangeSupport pcs;
    // Objects status.
    protected String status;
    // Classes tcp socket.
    private Socket socket;
    // Writer for socket comm.
    protected PrintWriter writer;
    // Sockets ip.
    private String ip;
    // Sockets port.
    private int port;
    // Objects string id, used for printing errors and fire events.
    protected String objName;

    /**
     * Initializes TCP client fields.
     * @param sys Listener for connection status events.
     * @param ip Server ip.
     * @param port TCP servers port.
     */
    public Client(ControlSys sys, String ip, int port) {
        this.pcs = new PropertyChangeSupport(this);
        this.pcs.addPropertyChangeListener(sys);
        this.status = "DISCONNECTED";
        this.ip = ip;
        this.port = port;
    }

    /**
     * Connects the socket to a remote location.
     * @param eventName
     */
    protected void doConnect(String eventName) {
        // Updates classes status.
        this.pcs.firePropertyChange(eventName, this.status, "LOADING");
        this.status = "LOADING";

        // Connects the socket to remote host.
        try {
            // Connects port.
            this.socket = new Socket(this.ip, this.port);
            System.out.println(this.objName + " connected = " + this.ip + ", " + this.port);

            // Fires connection status change.
            this.pcs.firePropertyChange("MAIN:STATUS", this.status, "CONNECTED");
            this.status = "CONNECTED";

        } catch (IOException e) {
            // Prints an error message.
            System.out.println(this.objName + " = " + e.getMessage());

            // Fires connection status change.
            this.pcs.firePropertyChange("MAIN:STATUS", this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }

    }

    /**
     * Closes the socket if it is not null.
     */
    public void doCloseSocket() {
        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                // Prints an error.
                System.out.println(this.objName + e.getMessage());
            }

            // Fires an event, the socket is closed.
            this.pcs.firePropertyChange(this.objName, this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }
    }
}
