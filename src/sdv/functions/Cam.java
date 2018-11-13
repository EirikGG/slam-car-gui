package sdv.functions;

import javafx.scene.image.ImageView;
import sdv.comm.UdpDatagramReader;
import sdv.functions.misc.ImageDrawer;

import java.awt.image.BufferedImage;
import java.net.InetAddress;

/**
 * Gets Image from UDP based UdpDatagramReader and sends it to ImageDrawer to be displayed.
 *
 * @author Eirik G. Gustafsson
 * @version 12.11.2018.
 */
public class Cam extends Thread {
    // Read from datagram socket.
    private UdpDatagramReader udpDatagramReader;
    // Handles the image.
    private ImageDrawer imageDrawer;
    // True to stop reading, false to continue.
    private boolean stop;

    /**
     * Connects to a UDP cam server with ip address and port, and setups imageViewer.
     *
     * @param imageView Image view to display image.
     * @param ipAddress Ip address for server to read from.
     * @param port      Port for server to doReconnect to.
     */
    public Cam(ImageView imageView, InetAddress ipAddress, int port) {
        this.udpDatagramReader = new UdpDatagramReader(ipAddress, port);
        this.imageDrawer = new ImageDrawer(imageView);
        this.stop = false;

    }

    /**
     * Loop where picture is read from DatagramSocket and displayed to ImageView.
     */
    public void run() {
        while (!this.stop) {
            // Gets image.
            BufferedImage img = this.udpDatagramReader.getImage();
            // Draw's the image.
            this.imageDrawer.drawImage(img);
        }

        // Clears the imageView.
        this.imageDrawer.doClear();
        // Close socket before thread is closed.
        this.udpDatagramReader.doCloseSocket();
    }

    /**
     * Changes flag to stop loop and close thread.
     */
    public void doStop() {
        this.stop = true;
    }
}