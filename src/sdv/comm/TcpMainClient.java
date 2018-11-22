package sdv.comm;

import sdv.gui.controller.control.sys.ControlSys;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Eirik G. Gustafsson
 * @version 13.11.2018.
 */
public class TcpMainClient extends Client {
    // Writer for socket comm.
    private PrintWriter writer;

    /**
     * Initializes with a new socket.
     */
    public TcpMainClient(ControlSys sys, String ip, int port) {
        super(sys, ip, port, "TcpMainClient");
    }

    @Override
    public void run() {
        doCloseSocket();
        if (doConnect())
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
     *
     * @param str String to send trough socket.
     */
    public void doWriteString(String str) {
        if (null != this.writer) {
            this.writer.println(str);
            System.out.println(str);
        } else
            System.out.println(this.objName + ": " + "writer = null");
    }
}