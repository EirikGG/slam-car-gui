package sdv.gui.controller;

import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class ControlSys{
    // Computers webcamera.
    @FXML private ImageView imageView;
    // Image from web-cam.
    private BufferedImage grabbedImage;
    // Stop camera flag.
    private boolean stopCamera = false;

    public ControlSys() {}

    /**
     * Handles what happens when the start button.
     */
    @FXML private void handleStartBtnAction() throws IOException {
        Webcam webcam = Webcam.getWebcams().get(1);
        webcam.open();
        BufferedImage bufferedImage = webcam.getImage();
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        this.imageView.setImage(image);
    }
}
