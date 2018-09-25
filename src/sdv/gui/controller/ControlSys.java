package sdv.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sdv.communication.Webcamera;


public class ControlSys{
    // GUI's ImageView, to display the images.
    @FXML private ImageView imageView;
    // Webcamera reader class.
    private Webcamera webcam;

    public ControlSys() {
    }

    /**
     * Handles what happens when the start button.
     */
    @FXML private void handleStartBtnAction() {
    }

    @FXML private void doHandleVideoStart() {
        this.webcam = new Webcamera(this.imageView);
        this.webcam.start();
    }

    @FXML private void doHandleVideoStopp() {
        this.webcam.setCamStop(true);
    }
}
