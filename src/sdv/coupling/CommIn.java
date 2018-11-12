package sdv.coupling;

import javafx.scene.image.ImageView;
import sdv.functions.slam.SlamImage;
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
    // Server webCamIp address.
    private String webCamIp;
    // Servers port.
    private int webCamPort;

    // Reads from slam server.
    private SlamImage slam;
    // Slam server ip address.
    private String slamIp;
    // Slam servers webCamPort.
    private int slamPort;


    /**
     * Initial values.
     */
    public CommIn(String webCamIp, int webCamPort, String slamIp, int slamPort) {
        // Webcam info.
        this.webCamIp = webCamIp;
        this.webCamPort = webCamPort;
        this.webCam = null;


        // Slam info.
        this.slam = null;
        this.slamIp = slamIp;
        this.slamPort = slamPort;
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
        this.webCam = new WebCam(imageViewer, getInetAddress(this.webCamIp), this.webCamPort);
        this.webCam.setDaemon(true);
        this.webCam.start();
    }

    /**
     * Stops the web-cam client.
     */
    public void doStopWebCam() {
        this.webCam.doStop();
    }

    /**
     * Initialises the SLAM image stream and draws images to ImageViewer.
     *
     * @param imageViewer Gui's image viewer, to display video feed.
     */
    public void doStartSlamCam(ImageView imageViewer) {
        if(this.slam != null) {
            this.slam.doStop();
        }
        this.slam = new SlamImage(imageViewer, getInetAddress(this.slamIp), this.slamPort);
        this.slam.setDaemon(true);
        this.slam.start();
    }

    /**
     * Stops the web-cam client.
     */
    public void doStopSlamCam() {
        this.slam.doStop();
    }




    /**
     * @return InetAddress, null if not found.
     */
    private InetAddress getInetAddress(String ip) {
        // Ip for InetAddress server.
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}
