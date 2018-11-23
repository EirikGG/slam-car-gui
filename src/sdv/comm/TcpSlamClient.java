package sdv.comm;

import sdv.gui.controller.control.sys.ControlSys;

import java.awt.*;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
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
public class TcpSlamClient {
    // Property change support, reports change in STATUS status.
    private PropertyChangeSupport pcs;
    // Datagram socket.
    private Socket socket;
    // Input stream to read from socket.
    private DataInputStream reader;
    // Classes starting status.
    private String status;
    private String objName;

    /**
     * Creates a socket with ip and port to doReconnect to.
     *
     * @param ipAddress Ip for socket to doReconnect to.
     * @param port      Port to doReconnect to.
     */
    public TcpSlamClient(InetAddress ipAddress, int port, ControlSys sys) {
        this.status = "DISCONNECTED";
        this.objName = "TcpSlamClient";
        this.pcs = new PropertyChangeSupport(this);
        this.pcs.addPropertyChangeListener(sys);
        doSetupSocket(ipAddress, port);
        try {
            this.reader = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            System.out.println("TcpSlamClient: " + e.getMessage());
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
        img.setData(Raster.createRaster(img.getSampleModel(), new DataBufferByte(recivedData, recivedData.length), new Point() ) );

        return img;
    }

    /**
     * Reads from dDatagramSocket, returns the data from the packet.
     * @return Data from DatagramSocket.
     * @throws IOException Nothing to receive.
     */
    private byte[] doReadSocket() {
        byte[] bytes = new byte[672400];
        try {
            this.reader.readFully(bytes, 0, bytes.length);
        } catch (IOException e) {
            System.out.println(this.objName + ": " + e.getMessage());
            this.pcs.firePropertyChange(this.objName, this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }
        return bytes;
    }

    /**
     * Creates a socket and connects it.
     *
     * @param ipAddress Ip address for socket to doReconnect to.
     * @param port      Port nr for socket to doReconnect to.
     */
    private void doSetupSocket(InetAddress ipAddress, int port) {
        this.pcs.firePropertyChange(this.objName, this.status, "LOADING");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.status = "LOADING";
        doCloseSocket();
        this.socket = new Socket();
        try {
            this.socket.connect(getSocketAddress(ipAddress, port));
            this.pcs.firePropertyChange(this.objName, this.status, "CONNECTED");
            this.status = "CONNECTED";
        } catch (IOException e) {
            System.out.println("TcpSlamClient: " + e.getMessage());
        }
        System.out.println(this.objName + ": " + "Created socket on " + this.socket.getLocalPort() +
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
                System.out.println(this.objName + ": " + e.getMessage());
            }
            this.pcs.firePropertyChange(this.objName, this.status, "DISCONNECTED");
            this.status = "DISCONNECTED";
        }
    }

    /**
     * Creates a socket address for a socket to connect to.
     * @param ip Ip address.
     * @param port Port to connect to.
     * @return InetSocketAddress.
     */
    private InetSocketAddress getSocketAddress(InetAddress ip, int port) {
        return new InetSocketAddress(ip, port);
    }
}
