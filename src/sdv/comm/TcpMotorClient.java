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
public class TcpMotorClient extends Client {
    // Writer for socket comm.
    private PrintWriter writer;

    /**
     * Initializes with a new socket.
     */
    public TcpMotorClient(String ip, int port, ControlSys sys) {
        super(sys, ip, port, "TcpMotorClient");
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
            System.out.println(this.objName + ": " + e.getMessage());
        }
    }

    /**
     * Writes a string trough the socket.
     * @param str String to send trough socket.
     */
    public void doWriteString(String str) {
        if (null != this.writer) {
            this.writer.println(str);
            System.out.println(str);
        } else {
            System.out.println(this.objName + ": " + "writer = null");
        }
    }
}