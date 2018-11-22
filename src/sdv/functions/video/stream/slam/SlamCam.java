package sdv.functions.video.stream.slam;

import javafx.scene.image.ImageView;
import sdv.comm.TcpSlamClient;
import sdv.functions.misc.ImageDrawer;
import sdv.gui.controller.control.sys.ControlSys;

import javax.naming.ldap.Control;
import java.awt.image.BufferedImage;
import java.net.InetAddress;

/**
 * Gets Image from UDP based UdpWebCamClient and sends it to ImageDrawer to be displayed.
 *
 * @author Eirik G. Gustafsson
 * @version 12.11.2018.
 */
public class SlamCam extends Thread {
    // Read from datagram socket.
    private TcpSlamClient tcpSlamClient;
    // Handles the image.
    private ImageDrawer imageDrawer;
    // True to stop reading, false to continue.
    private boolean stop;
    // Slams ip.
    private InetAddress ipAddress;
    // Slams port.
    private int port;
    // Observer.
    private ControlSys sys;

    /**
     * Connects to a UDP cam server with ip address and port, and setups imageViewer.
     *
     * @param imageView Image view to display image.
     * @param ipAddress Ip address for server to read from.
     * @param port      Port for server to doReconnect to.
     */
    public SlamCam(ImageView imageView, InetAddress ipAddress, int port, ControlSys sys) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.imageDrawer = new ImageDrawer(imageView);
        this.stop = false;
        this.sys = sys;
    }

    /**
     * Loop where picture is read from DatagramSocket and displayed to ImageView.
     */
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.tcpSlamClient = new TcpSlamClient(ipAddress, port, sys);

        while (!this.stop) {
            // Gets image.
            BufferedImage img = this.tcpSlamClient.getImage();

            if (null != img) {
                // Draw's the image.
                this.imageDrawer.drawImage(img);
            }
        }

        // Close socket before thread is closed.
        this.tcpSlamClient.doCloseSocket();
    }

    /**
     * Changes flag to stop loop and close thread.
     */
    public void doStop() {
        this.stop = true;
    }
}