package sdv.functions;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Parent class to web-cam and slam-cam, contains their common methods.
 * @author Eirik G. Gustafsson
 * @version 13.11.2018.
 */
public abstract class Cam {
    // Cams ip.
    protected String ipAddress;
    // Cams port.
    protected int port;
    // Local port to connect socket to.
    protected int localPort;

    /**
     * Populates fields.
     * @param ipAddress 
     * @param port
     */
    public Cam(String ipAddress, int port, int localPort) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.localPort = localPort;
    }
}
