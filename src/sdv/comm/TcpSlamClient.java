package sdv.comm;

import java.awt.*;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.*;

import java.awt.image.BufferedImage;

/**
 * Reads a DatagramSocket and assembles the data.
 *
 * @author Eirik G. Gustafsson
 * @version 25.09.2018.
 */
public class TcpSlamClient extends Thread {
    // Listener to listen for change.
    private PropertyChangeSupport pcs;
    // Datagram socket.
    private Socket socket;
    // Input stream to read from socket.
    private DataInputStream reader;
    // Servers ip.
    private String ip;
    // Servers port.
    private int port;

    /**
     * Creates a socket with ip and port to doReconnect to.
     *
     * @param ipAddress Ip for socket to doReconnect to.
     * @param port      Port to doReconnect to.
     */
    public TcpSlamClient(PropertyChangeListener pcl, String ipAddress, int port) {
        // Saves server info.
        this.ip = ipAddress;
        this.port = port;

        this.pcs = new PropertyChangeSupport(this);
        this.pcs.addPropertyChangeListener(pcl);
    }

    @Override
    public void run() {

        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            if (this.socket.isConnected())
                this.pcs.firePropertyChange("Web-cam connected", false, true);
            else
                this.pcs.firePropertyChange("Web-cam connected", true, false);
        }
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

        BufferedImage img = new BufferedImage(820, 820, BufferedImage.TYPE_BYTE_GRAY);
        img.setData(Raster.createRaster(img.getSampleModel(), new DataBufferByte(recivedData, recivedData.length), new Point()));

        return img;
    }

    /**
     * Reads from dDatagramSocket, returns the data from the packet.
     *
     * @return Data from DatagramSocket.
     * @throws IOException Nothing to receive.
     */
    private byte[] doReadSocket() {
        byte[] bytes = new byte[672400];
        try {
            this.reader.readFully(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Creates a socket and connects it.
     *
     * @param ipAddress Ip address for socket to doReconnect to.
     * @param port      Port nr for socket to doReconnect to.
     */
    public void doCreateNewSocket(String ipAddress, int port) {
        this.socket = new Socket();
        try {
            this.socket.connect(getSocketAddress(ipAddress, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        doSetupDataInputStream();
        System.out.println("TcpSlamClient: Created socket on " + this.socket.getLocalPort() +
                ", listening to " + this.socket.getInetAddress() + ";" + this.socket.getPort());
    }

    /**
     * Closes the socket.
     */
    public void doCloseSocket() {
        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a socket address for a socket to connect to.
     *
     * @param ip   Ip address.
     * @param port Port to connect to.
     * @return InetSocketAddress.
     */
    private InetSocketAddress getSocketAddress(String ip, int port) {
        // Ip for InetAddress server.
        InetAddress ipAddress = null;
        // Socket address to connect to.
        InetSocketAddress socketAddress = null;

        try {
            ipAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        socketAddress = new InetSocketAddress(ipAddress, port);
        return socketAddress;
    }

    /**
     * Create server reader.
     */
    private void doSetupDataInputStream() {
        try {
            this.reader = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
