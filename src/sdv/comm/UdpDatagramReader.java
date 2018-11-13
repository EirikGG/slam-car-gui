package sdv.comm;

import java.beans.PropertyChangeListener;
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
public class UdpDatagramReader extends Thread {
    // Listener to listen for change.
    private PropertyChangeSupport pcs;
    // Datagram socket.
    private DatagramSocket socket;
    // Ip address to server.
    private String ip;
    // Servers port.
    private int port;
    // Local port.
    private int localPort;

    /**
     * Creates a socket with ip and port to doReconnect to.
     *
     * @param ipAddress Ip for socket to doReconnect to.
     * @param port      Port to doReconnect to.
     * @param localPort Local port to bind socket to.
     */
    public UdpDatagramReader(String ipAddress, int port, int localPort) {
        this.ip = ipAddress;
        this.port = port;
        this.localPort = localPort;
        this.pcs = new PropertyChangeSupport(this);
    }

    @Override
    public void run() {
        doCloseSocket();
        doCreateNewSocket();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return packet.getData();
    }

    /**
     * Creates a new socket.
     */
    public void doCreateNewSocket() {
        try {
            this.socket = new DatagramSocket(this.localPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.socket.connect(getInetAddress(this.ip), this.port);
        this.pcs.firePropertyChange("WEB_CAM_CONNECT", false, true);
        System.out.println("UdpDatagramReader: Created socket on " + this.socket.getLocalPort() +
                ", listening to " + this.socket.getInetAddress() + ";" + this.socket.getPort());
    }

    /**
     * Closes the socket if it exists.
     */
    public void doCloseSocket() {
        if (null != this.socket) {
            this.socket.close();
        }
    }

    /**
     * @return InetAddress, null if not found.
     */
    private InetAddress getInetAddress(String ip) {
        // Ip for InetAddress server.
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}
