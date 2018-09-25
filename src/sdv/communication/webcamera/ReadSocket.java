package sdv.communication.webcamera;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * @author: Eirik G. Gustafsson
 * @version: 25.09.2018.
 */
public class ReadSocket {
    // Datagram socket.
    private DatagramSocket socket;

    public ReadSocket() throws Exception {
        doSetupSocket();
    }

    /**
     * Reads the DatagramSocket, assembles the image and returns it.
     *
     * @return Image.
     * @throws IOException Cant read socket.
     */
    public Image getImage() throws IOException {
        byte[] recivedData = null;

        try {
            recivedData = doReadSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doAssembleImage(recivedData);
    }

    /**
     * Takes the byte array and assembles it as a image.
     *
     * @param recivedData Byte array of image.
     * @return Image.
     * @throws IOException Cant read byteArray.
     */
    private Image doAssembleImage(byte[] recivedData) throws IOException {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new ByteArrayInputStream(recivedData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SwingFXUtils.toFXImage(img, null);
    }

    /**
     * Reads from dDatagramSocket.
     *
     * @return Bytes from DatagramSocket.
     * @throws IOException Nothing to recive.
     */
    private byte[] doReadSocket() throws IOException {
        byte[] receivedData = new byte[9600];
        DatagramPacket packet = new DatagramPacket(receivedData, receivedData.length);


        try {
            this.socket.receive(packet);
        } catch(IOException exc) {
            exc.printStackTrace();
        }

        return packet.getData();
    }

    /**
     * Sets the ipAddress.
     * @throws UnknownHostException Don't know the host address.
     */
    private void doSetupSocket() throws UnknownHostException, SocketException {
        InetAddress ipAddress = InetAddress.getLocalHost();

        this.socket = new DatagramSocket(1234);
        this.socket.connect(ipAddress, 1235);
    }
}
