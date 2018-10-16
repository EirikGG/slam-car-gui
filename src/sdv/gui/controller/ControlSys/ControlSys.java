package sdv.gui.controller.ControlSys;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import sdv.coupling.CommunicationIn;

/**
 * Controls the control system GUI screen.
 */
public class ControlSys {
    // Interface for incoming communication.
    private CommunicationIn communicationIn;
    // Handles button events.
    private ButtonEvent btnEvent;
    // Holds status of manual mode button.
    @FXML private JFXToggleButton manualMode;

    // GUI's ImageView, to display the images.
    @FXML private ImageView imageView;

    /**
     * Using initialise instead of constructor since this is called after FXML fields are populated.
     */
    public void initialize() {
        this.communicationIn = new CommunicationIn();
        doHandleVideoStart();
        this.btnEvent = new ButtonEvent();
    }

    /**
     * Handles what happens when the start button is pressed.
     */
    @FXML private void handleStartBtnAction() {
    }

    /**
     * Handles what happens when the start video button is pressed.
     */
    @FXML private void doHandleVideoStart() {
        this.communicationIn.doStartWebCam(this.imageView);
    }

    /**
     * Handles what happens when the stop video button is pressed.
     */
    @FXML private void doHandleVideoStop() {
        this.communicationIn.doStopWebCam();
    }

    /**
     * Handles button events.
     *
     * @param event Key pressed/ released event.
     */
    @FXML private void doHandleKeyInput(KeyEvent event) {
        if (this.manualMode.isSelected())
            this.btnEvent.doHandleKeyInput(event);
    }
}
