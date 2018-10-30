package videoStream;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;

/**
 * Captures image from web-cam and returns it.
 *
 * @version 25.09.2018
 * @author Eirik G. Gustafsson
 */
public class ReadWebcam {
    // Computers web-camera.
    private Webcam webcam;

    /**
     * Gets the laptop web-cam.
     */
    public ReadWebcam() {
        doSetupWebcam(0);
    }

    /**
     * Readies the webcam.
     */
    private void doSetupWebcam(int webcamNr) {
        this.webcam = Webcam.getWebcams().get(webcamNr);
        this.webcam.open();

    }

    /**
     * Captures BufferedImage and returns it as a Image.
     *
     * @return Returns image.
     */
    public BufferedImage doGetImage() {
        return webcam.getImage();
    }
}