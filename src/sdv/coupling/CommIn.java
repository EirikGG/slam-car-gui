package sdv.coupling;

import javafx.scene.image.ImageView;
import sdv.functions.webcamera.WebCam;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Interface class between camera stream, picture communication and TCP communication.
 *
 * @author Eirik G. Gustafsson
 * @version 23.09.2018.
 */
public class CommIn {
    // Reads from web-cam server.
    private WebCam webCam;
    // Server ip address.
    private String ip;
    // Servers port.
    private int port;

    /**
     * Initial values.
     */
    public CommIn(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.webCam = null;
    }

    /**
     * Initialises the web cam stream and draws images to ImageViewer.
     *
     * @param imageViewer Gui's image viewer, to display video feed.
     */
    public void doStartWebCam(ImageView imageViewer) {
        if(this.webCam != null) {
            this.webCam.doStop();
        }

        // Ip for WebCam server.
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(this.ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.webCam = new WebCam(imageViewer, ipAddress, this.port);
        this.webCam.setDaemon(true);
        this.webCam.start();
    }

    /**
     * Stops the web-cam client.
     */
    public void doStopWebCam() {
        this.webCam.doStop();
    }
}
