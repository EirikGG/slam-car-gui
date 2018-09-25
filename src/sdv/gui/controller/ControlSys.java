package sdv.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sdv.coupling.CommunicationIn;

/**
 * Controls the control system GUI screen.
 */
public class ControlSys {
    // Interface for incoming communication.
    private CommunicationIn communicationIn;

    // GUI's ImageView, to display the images.
    @FXML private ImageView imageView;

    /**
     * Using initialise instead of constructor since this is called after FXML fields are populated.
     */
    public void initialize() {
        this.communicationIn = new CommunicationIn();
        doHandleVideoStart();
    }

    /**
     * Handles what happens when the start button is pressed.
     */
    @FXML private void handleStartBtnAction() {
    }

    /**
     * Handles what happens when the start video button is pressed.
     */
    @FXML private synchronized void doHandleVideoStart() {
        this.communicationIn.doStartWebCam(this.imageView);
    }

    /**
     * Handles what happens when the stop video button is pressed.
     */
    @FXML private void doHandleVideoStop() {
        this.communicationIn.doStopWebCam();
    }
}
