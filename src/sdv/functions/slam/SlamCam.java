package sdv.functions.slam;

import sdv.comm.TcpSlamClient;
import sdv.functions.Cam;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;

/**
 * Gets Image from TCP based TcpSlamClient.
 *
 * @author Eirik G. Gustafsson
 * @version 12.11.2018.
 */
public class SlamCam extends Cam {
    // Read from datagram socket.
    private TcpSlamClient tcpSlamClient;

    /**
     * @param ipAddress Ip address of server.
     * @param port Servers web-cam port.
     * @param localPort LocalSocket port.
     */
    public SlamCam(String ipAddress, int port, int localPort, PropertyChangeListener pcl) {
        super(ipAddress, port, localPort);
        this.tcpSlamClient = new TcpSlamClient(pcl, ipAddress, port);
        this.tcpSlamClient.setDaemon(true);
        this.tcpSlamClient.start();
    }

    /**
     * @return Image from a socket.
     */
    public BufferedImage getImage() {
        return this.tcpSlamClient.getImage();
    }

    /**
     * Closes socket and creates a new one.
     */
    public void doCreateSocket() {
        this.tcpSlamClient.doCreateNewSocket(this.ipAddress, this.port);
    }

    /**
     * Closes the socket.
     */
    public void doCloseSocket() {
        this.tcpSlamClient.doCloseSocket();
    }
}