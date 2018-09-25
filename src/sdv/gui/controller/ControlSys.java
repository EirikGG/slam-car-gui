package sdv.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sdv.communication.webcamera.Webcamera;


public class ControlSys extends Thread{
    // GUI's ImageView, to display the images.
    @FXML private ImageView imageView;
    // Webcamera reader class.
    private Webcamera webCam;

    public ControlSys() {
    }

    /**
     * Handles what happens when the start button.
     */
    @FXML private void handleStartBtnAction() {
    }

    @FXML private synchronized void doHandleVideoStart() throws Exception {
            doStartWebCam();
            this.webCam.setStopp(false);
    }

    @FXML private void doHandleVideoStop() {
        this.webCam.setStopp(true);
    }

    private void doStartWebCam() throws Exception {
        this.webCam = new Webcamera(this.imageView);
        this.webCam.start();
    }
}
