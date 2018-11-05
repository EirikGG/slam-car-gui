package sdv.functions.webcamera;

import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.net.InetAddress;

/**
 * Gets Image from UDP based SocketReader and sends it to ImageDrawer to be displayed.
 *
 * @version 25.09.2018
 * @author Eirik G. Gustafsson
 */
public class WebCam extends Thread {
    // Read from datagram socket.
    private SocketReader socketReader;
    // Handles the image.
    private ImageDrawer imageDrawer;

    // True to stop reading, false to continue.
    private boolean stopp;

    /**
     * Connects to a web-cam server with ip address and port. Draws images from server to ImageView.
     *
     * @param imageView Image view to display image.
     * @param ipAddress Ip address for server to read from.
     * @param port Port for server to doReconnect to.
     */
    public WebCam(ImageView imageView, InetAddress ipAddress, int port) {
        this.socketReader = new SocketReader(ipAddress, port);
        this.imageDrawer = new ImageDrawer(imageView);
        this.stopp = false;

    }

    /**
     * Loop where picture is read from DatagramSocket and displayed to ImageView.
     */
    public void run() {
        while (!this.stopp) {
            // Gets image.
            BufferedImage img = this.socketReader.getImage();

            // Draw's the image.
            this.imageDrawer.drawImage(img);
        }

        // Close socket before thread is closed.
        this.socketReader.doCloseSocket();
    }

    /**
     * Changes flag to stop loop and close thread.
     */
    public void doStop() {
        this.stopp = true;
    }
}