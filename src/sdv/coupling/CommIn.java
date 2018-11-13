package sdv.coupling;

import javafx.scene.image.ImageView;
import sdv.functions.slam.SlamCam;
import sdv.functions.webcam.WebCam;

import java.beans.PropertyChangeListener;

/**
 * Interface class between camera stream, picture communication and TCP communication.
 *
 * @author Eirik G. Gustafsson
 * @version 23.09.2018.
 */
public class CommIn {
    // Reads from web-cam server.
    private WebCam webCam;
    // Reads from slam server.
    private SlamCam slamCam;

    /**
     * Initial values.
     */
    public CommIn(PropertyChangeListener pcl, String serverIp, int webCamPort, int localWebCamPort, int slamPort) {
        this.webCam = new WebCam(serverIp, webCamPort, localWebCamPort, pcl);
        this.slamCam = new SlamCam(serverIp, webCamPort, localWebCamPort, pcl);
    }

    /**
     * Initialises the web cam stream and draws images to ImageViewer.
     */
    public void doStartWebCam() {
        if (this.webCam != null) {
            this.webCam.doCloseSocket();
        }
        this.webCam.doCreateSocket();
    }

    /**
     * Stops the web-cam client.
     */
    public void doStopWebCam() {
        this.webCam.doCloseSocket();
    }

    /**
     * Initialises the SLAM image stream and draws images to ImageViewer.
     *
     * @param imageViewer Gui's image viewer, to display video feed.
     */
    public void doStartSlamCam(ImageView imageViewer) {
        if (this.slamCam != null) {
            this.slamCam.doCloseSocket();
        }
        this.slamCam.doCreateSocket();
    }

    /**
     * Stops the web-cam client.
     */
    public void doStopSlamCam() {
        this.slamCam.doCloseSocket();
    }
}
