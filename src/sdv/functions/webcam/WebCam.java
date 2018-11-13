package sdv.functions.webcam;

import sdv.comm.UdpDatagramReader;
import sdv.functions.Cam;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;

/**
 * Gets Image from UDP based UdpDatagramReader and sends it to ImageDrawer to be displayed.
 *
 * @author Eirik G. Gustafsson
 * @version 12.11.2018.
 */
public class WebCam extends Cam {
    // Read from datagram socket.
    private UdpDatagramReader udpDatagramReader;

    /**
     * @param ipAddress Ip address of server.
     * @param port Servers web-cam port.
     * @param localPort LocalSocket port.
     */
    public WebCam(String ipAddress, int port, int localPort, PropertyChangeListener pcl) {
        super(ipAddress, port, localPort);
        this.udpDatagramReader = new UdpDatagramReader(pcl, ipAddress, port, localPort);
        this.udpDatagramReader.setDaemon(true);
        this.udpDatagramReader.start();
    }

    /**
     * @return Image from a socket.
     */
    public BufferedImage getImage() {
        return this.udpDatagramReader.getImage();
    }

    /**
     * Closes socket and creates a new one.
     */
    public void doCreateSocket() {
        this.udpDatagramReader.doCreateNewSocket();
    }

    /**
     * Closes the socket.
     */
    public void doCloseSocket() {
        this.udpDatagramReader.doCloseSocket();
    }
}