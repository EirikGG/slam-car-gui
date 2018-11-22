package sdv.comm;

import sdv.gui.controller.control.sys.ControlSys;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.Socket;

/**
 * Abstract class for a TCP client socket connection.
 * @author Eirik G. Gustafsson
 * @version 22.11.2018.
 */
public abstract class Client extends Thread {
    // Property change support, reports change in STATUS status.
    private PropertyChangeSupport pcs;
    // Objects status.
    private String status;
    // Classes tcp socket.
    protected Socket socket;
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
    protected Client(ControlSys sys, String ip, int port, String objName) {
        this.pcs = new PropertyChangeSupport(this);
        this.pcs.addPropertyChangeListener(sys);
        this.status = "DISCONNECTED";
        this.ip = ip;
        this.port = port;
        this.objName = objName;
    }

    /**
     * Connects the socket to a remote location.
     */
    public boolean doConnect() {
        boolean result = false;

        // Updates classes status.
        this.pcs.firePropertyChange(this.objName, this.status, "LOADING");
        this.status = "LOADING";

        // Connects the socket to remote host.
        try {
            // Connects port.
            this.socket = new Socket(this.ip, this.port);
            System.out.println(this.objName + ": " + "connected " + this.ip + ", " + this.port);

            // Fires connection status change.
            this.pcs.firePropertyChange(this.objName, this.status, "CONNECTED");
            this.status = "CONNECTED";
            result = true;

        } catch (IOException e) {
            // Prints an error message.
            System.out.println(this.objName + ": " + e.getMessage());

            // Fires connection status change.
            this.pcs.firePropertyChange(this.objName, this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
            result = false;
        }

        return result;
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
                System.out.println(this.objName + ": " + e.getMessage());
            }

            // Fires an event, the socket is closed.
            this.pcs.firePropertyChange(this.objName, this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }
    }
}
