package sdv.coupling;

import javafx.scene.image.ImageView;
import sdv.com.webcamera.WebCam;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Interface class between camera stream, picture communication and TCP communication.
 *
 * @author Eirik G. Gustafsson
 * @version 23.09.2018.
 */
public class CommunicationIn {
    // Reads from web-cam server.
    private WebCam webCam;

    /**
     * Initial values.
     */
    public CommunicationIn() {
        this.webCam = null;
    }

    /**
     * Initialises the web cam stream and draws images to ImageViewer.
     */
    public void doStartWebCam(ImageView imageViewer) {
        // Ip for WebCam server.
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.webCam = new WebCam(imageViewer, ipAddress, 1235);
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
