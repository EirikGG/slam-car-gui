package sdv.gui.controller.control.sys;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import sdv.coupling.CommIn;
import sdv.coupling.CommOut;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Controls the control system GUI screen.
 */
public class ControlSys implements PropertyChangeListener {
    // Interface for incoming communication.
    private CommIn commIn;
    // Interface for outgoing communication.
    private CommOut commOut;
    // Handles button events.
    private KeyboardInput btnEvent;
    // Holds status of manual mode button.
    @FXML private JFXToggleButton manualMode;
    // GUI's slider.
    @FXML private Slider slider;
    // GUI's ImageView, to display the images.
    @FXML private ImageView webcam;
    // GUI's ImageView, to display the images.
    @FXML private ImageView slamCam;
    // GUI's connected label.
    @FXML private Label connectedLabel;


    /**
     * Using initialise instead of constructor since this is called after FXML fields are populated.
     */
    public void initialize() {
        this.commOut = new CommOut("192.168.0.100", 8000, 8003);
        this.commIn = new CommIn("192.168.0.100", 8001, 9000, 8002);
        this.btnEvent = new KeyboardInput();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().toUpperCase().equals("WEB_CAM_CONNECTED")) {
            this.connectedLabel.setText((String)evt.getNewValue());
        }
    }

    /**
     * Handles what happens when the start video button is pressed.
     */
    @FXML private void doHandleVideoStart() {
        this.commOut.doSendMainString("WEBCAM:START");
        this.commIn.doStartWebCam(this.webcam);
    }

    /**
     * Handles what happens when the stop video button is pressed.
     */
    @FXML private void doHandleVideoStop() {
        this.commIn.doStopWebCam();
    }

    /**
     * Starts the slam cam.
     */
    @FXML private void doHandleSlamCam() {
        this.commOut.doSendMainString("SLAM:START");
        this.commIn.doStartSlamCam(this.slamCam);
    }

    /**
     * Stops the slam cam.
     */
    @FXML private void doStopSlamCam() {this.commIn.doStopSlamCam();}

    /**
     * Send string if it is not "".
     *
     * @param event Key pressed or released event.
     */
    @FXML private void doHandleKeyInput(KeyEvent event) {
        if (this.manualMode.isSelected()) {
            String str = this.btnEvent.doHandleKeyEvent(event);
            if (!str.equals("")) {
                this.commOut.doSendMotorString(str);
            }
        }
    }

    /**
     * If a change in value is detected, sends info to motor controller.
     */
    @FXML private void doHandleSliderInput() {
        Double value = this.slider.getValue();
        int number = value.intValue();
        this.commOut.doSendMotorString("SPEED:" + number);
    }

    /**
     * If connect is pressed, tries to connect ot the server.
     */
    @FXML private void doHandleConnectBtn() {
        this.commOut.doConnectMain();
    }

    @FXML private void doHandleDisconnectBtn() {
        this.commOut.doSendMainString("STOP");
    }

    /**
     * Handles what happens when manual mode is activated.
     */
    @FXML private void doHandleManualMode() {
        if (this.manualMode.isSelected()) {
            this.commOut.doSendMainString("MOTORCONTROLLER:START");
            this.commOut.doConnectMotorController();
        } else if(!this.manualMode.isSelected()) {
            this.commOut.doSendMainString("MOTORCONTROLLER:STOP");
            this.commOut.doCloseSocket();
        }
    }
}
