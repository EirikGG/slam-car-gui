package sdv.functions;

/**
 * Parent class to web-cam and slam-cam, contains their common methods.
 * @author Eirik G. Gustafsson
 * @version 13.11.2018.
 */
public abstract class Cam {
    // Cams ip.
    private String ipAddress;
    // Cams port.
    private int port;
    // Local port to connect socket to.

    /**
     * Populates fields.
     * @param ipAddress 
     * @param port
     */
    public Cam(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
}
