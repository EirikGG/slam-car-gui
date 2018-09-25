package sdv.communication;


import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

/**
 * Captures image from web-cam and displays it to the GUI.
 *
 * @version 25.09.2018
 * @author Eirik G. Gustafsson
 */
public class Webcamera extends Thread{
    // Computers web-camera.
    private Webcam webcam;
    // Gui image view.
    private ImageView imageView;
    // Tells the camera to stop/start.
    private boolean camStop;

    /**
     * Gets the laptop web-cam.
     */
    public Webcamera(ImageView imageView) {
        this.webcam = Webcam.getWebcams().get(1);
        this.imageView = imageView;
    }

    /**
     * Captures image and displays it to the imageView.
     */
    public void run() {
        this.webcam.open();
        this.camStop = false;

        while(!this.camStop) {
            BufferedImage bufferedImage = webcam.getImage();
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);


        }
    }

    public void setCamStop(boolean stop) {
        this.camStop = stop;
    }
}