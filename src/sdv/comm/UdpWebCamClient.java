package sdv.comm;

import sdv.gui.controller.control.sys.ControlSys;

import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Reads a DatagramSocket and assembles the data.
 *
 * @author Eirik G. Gustafsson
 * @version 25.09.2018.
 */
public class UdpWebCamClient {
    // Property change support, reports change in STATUS status.
    private PropertyChangeSupport pcs;
    // Datagram socket.
    private DatagramSocket socket;
    // Classes starting status.
    private String status;

    /**
     * Creates a socket with ip and port to doReconnect to.
     *
     * @param ipAddress Ip for socket to doReconnect to.
     * @param port      Port to doReconnect to.
     */
    public UdpWebCamClient(InetAddress ipAddress, int port, int localPort, ControlSys sys) {
        this.pcs = new PropertyChangeSupport(this);
        this.pcs.addPropertyChangeListener(sys);
        doSetupSocket(ipAddress, port, localPort);
        this.status = "DISCONNECTED";
    }

    /**
     * Gets data and returns it as a BufferedImage.
     *
     * @return BufferedImage.
     */
    public BufferedImage getImage() {
        return doAssembleData(doReadSocket());
    }

    /**
     * Takes the byte array and assembles it as a image.
     *
     * @param recivedData Byte array of image.
     * @return Image.
     * @throws IOException Cant read byteArray.
     */
    private BufferedImage doAssembleData(byte[] recivedData) {
        // Initialises the BufferedImage to be returned.
        BufferedImage img = null;

        // Gets raw data and converts it to buffered image.
        try {
            img = ImageIO.read(new ByteArrayInputStream(recivedData));
        } catch (IOException e) {
            System.out.println("UdpWebCamClient: " + e.getMessage());
        }

        return img;
    }

    /**
     * Reads from dDatagramSocket, returns the data from the packet.
     *
     * @return Data from DatagramSocket.
     * @throws IOException Nothing to receive.
     */
    private byte[] doReadSocket() {
        byte[] receivedData = new byte[15000];
        DatagramPacket packet = new DatagramPacket(receivedData, receivedData.length);

        try {
            this.socket.receive(packet);
        } catch (IOException e) {
            System.out.println("UdpWebCamClient: " + e.getMessage());
            this.pcs.firePropertyChange("WEBCAM:STATUS", this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }

        return packet.getData();
    }

    /**
     * Creates a socket and connects it.
     *
     * @param ipAddress Ip address for socket to doReconnect to.
     * @param port      Port nr for socket to doReconnect to.
     */
    private void doSetupSocket(InetAddress ipAddress, int port, int localPort) {
        this.pcs.firePropertyChange("WEBCAM:STATUS", this.status, "LOADING");
        this.status = "LOADING";
        doCloseSocket();

        try {
            this.socket = new DatagramSocket(localPort);
            this.pcs.firePropertyChange("WEBCAM:STATUS", this.status, "CONNECTED");
            this.status = "CONNECTED";
        } catch (SocketException e) {
            System.out.println("UdpWebCamClient: " + e.getMessage());
            this.pcs.firePropertyChange("WEBCAM:STATUS", this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }
        this.socket.connect(ipAddress, port);
        System.out.println("UdpWebCamClient: Created socket on " + this.socket.getLocalPort() +
                ", listening to " + this.socket.getInetAddress() + ";" + this.socket.getPort());
    }

    /**
     * Closes the socket.
     */
    public void doCloseSocket() {
        if (null != this.socket) {
            this.socket.close();
        }
        this.pcs.firePropertyChange("WEBCAM:STATUS", this.status, "DISCONNECTED");
        this.status = "DISCONNECTED";
    }
}
