package sdv.comm;

import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Abstract class for a TCP client socket connection.
 * @author Eirik G. Gustafsson
 * @version 22.11.2018.
 */
public class Client {
    // Property change support, reports change in STATUS status.
    private PropertyChangeSupport pcs;
    // STATUS status.
    private String status;
    // Classes tcp socket.
    private Socket socket;
    // Writer for socket comm.
    private PrintWriter writer;
    // Sockets ip.
    private String ip;
    // Sockets port.
    private int port;

}
