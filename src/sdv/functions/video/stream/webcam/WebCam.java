package sdv.functions.video.stream.webcam;

import javafx.scene.image.ImageView;
import sdv.comm.UdpWebCamClient;
import sdv.functions.misc.ImageDrawer;
import sdv.gui.controller.control.sys.ControlSys;

import java.awt.image.BufferedImage;
import java.net.InetAddress;

/**
 * Gets Image from UDP based UdpWebCamClient and sends it to ImageDrawer to be displayed.
 *
 * @author Eirik G. Gustafsson
 * @version 12.11.2018.
 */
public class WebCam extends Thread {
    // Read from datagram socket.
    private UdpWebCamClient udpWebCamClient;
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
    public WebCam(ImageView imageView, InetAddress ipAddress, int port, int localPort, ControlSys sys) {
        this.udpWebCamClient = new UdpWebCamClient(ipAddress, port, localPort, sys);
        this.imageDrawer = new ImageDrawer(imageView);
        this.stop = false;

    }

    /**
     * Loop where picture is read from DatagramSocket and displayed to ImageView.
     */
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!this.stop) {
            // Gets image.
            BufferedImage img = this.udpWebCamClient.getImage();
            // Draw's the image.
            this.imageDrawer.drawImage(img);
        }

        // Clears the imageView.
        this.imageDrawer.doClear();
        // Close socket before thread is closed.
        this.udpWebCamClient.doCloseSocket();
    }

    /**
     * Changes flag to stop loop and close thread.
     */
    public void doStop() {
        this.stop = true;
    }
}