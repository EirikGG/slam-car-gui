package sdv.comm;

import java.io.*;
import java.net.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Reads a DatagramSocket and assembles the data.
 *
 * @author Eirik G. Gustafsson
 * @version 25.09.2018.
 */
public class TcpClient {
    // Datagram socket.
    private Socket socket;
    // Input stream to read from socket.
    private InputStream reader;

    /**
     * Creates a socket with ip and port to doReconnect to.
     *
     * @param ipAddress Ip for socket to doReconnect to.
     * @param port      Port to doReconnect to.
     */
    public TcpClient(InetAddress ipAddress, int port) {
        doSetupSocket(ipAddress, port);
        try {
            this.reader = this.socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
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
        byte[] bytes = new byte[672400];
        try {
            this.reader.read(bytes, 0, 820*820);
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
    private void doSetupSocket(InetAddress ipAddress, int port) {
        doCloseSocket();
            this.socket = new Socket();
        try {
            this.socket.connect(getSocketAddress(ipAddress, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("TcpClient: Created socket on " + this.socket.getLocalPort() +
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

    private InetSocketAddress getSocketAddress(InetAddress ip, int port) {
        return new InetSocketAddress(ip, port);
    }
}