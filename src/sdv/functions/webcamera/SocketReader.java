package sdv.functions.webcamera;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.SocketException;
import javax.imageio.ImageIO;

/**
 * Reads a DatagramSocket and assembles the data.
 *
 * @author Eirik G. Gustafsson
 * @version 25.09.2018.
 */
public class SocketReader {
    // Datagram socket.
    private DatagramSocket socket;

    /**
     * Creates a socket with ip and port to doReconnect to.
     *
     * @param ipAddress Ip for socket to doReconnect to.
     * @param port Port to doReconnect to.
     */
    public SocketReader(InetAddress ipAddress, int port) {
        doSetupSocket(ipAddress, port);
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
        byte[] receivedData = new byte[9600];
        DatagramPacket packet = new DatagramPacket(receivedData, receivedData.length);

        try {
            this.socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packet.getData();
    }

    /**
     * Creates a socket and connects it.
     * @param ipAddress Ip address for socket to doReconnect to.
     * @param port Port nr for socket to doReconnect to.
     */
    private void doSetupSocket(InetAddress ipAddress, int port) {
        try {
            this.socket = new DatagramSocket(1234);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.socket.connect(ipAddress, port);
    }

    /**
     * Closes the socket.
     */
    public void doCloseSocket() {
        this.socket.close();
    }
}
