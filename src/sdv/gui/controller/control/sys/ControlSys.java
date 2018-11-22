package sdv.gui.controller.control.sys;

import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private JFXToggleButton manualMode;
    // GUI's slider.
    @FXML
    private Slider slider;
    // GUI's ImageView, to display the images.
    @FXML
    private ImageView webcam;
    // GUI's ImageView, to display the images.
    @FXML
    private ImageView slamCam;
    // GUI's connected label.
    @FXML
    private Label connectedLabel, manualModeLabel, webCamLabel, slamCamLabel;
    // Indecates if gui is connected to server.
    @FXML
    private Button connectBtn;
    // Webcam buttons.
    @FXML
    private Button webCamStart;
    @FXML
    private Button webCamStop;
    // Slam buttons.
    @FXML
    private Button slamCamStart;
    @FXML
    private Button slamCamStop;


    /**
     * Using initialise instead of constructor since this is called after FXML fields are populated.
     */
    public void initialize() {
        this.commOut = new CommOut("192.168.0.100", 8000, 8003);
        this.commIn = new CommIn("192.168.0.100", 8001, 9000, 8002);
        this.btnEvent = new KeyboardInput();

        // Disables all buttons not available until connection.
        doDisableBtn(true);
        this.slider.setDisable(true);
    }

    /**
     * If connect is pressed, tries to connect ot the server.
     */
    @FXML
    private void doHandleConnectBtn() {
        if (this.connectBtn.getText().equals("Connect"))
            this.commOut.doConnectMain(this);
        else {
            doDisconnect();
            this.manualMode.setSelected(false);
            this.slider.setValue(32);
        }
    }

    /**
     * Updates the GUI with changes happening in backend classes.
     *
     * @param evt Event fired.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(
                () -> {
                    if (evt.getPropertyName().equals("TcpMainClient")) {
                        if (evt.getNewValue().equals("CONNECTED")) {
                            this.connectedLabel.setText("Connected");
                            this.connectBtn.setText("Disconnect");
                            doDisableBtn(false);

                        } else if (evt.getNewValue().equals("DISCONNECTED")) {
                            this.connectedLabel.setText("Disconnected");
                            this.connectBtn.setText("Connect");
                            doDisconnect();
                            this.slider.setDisable(true);
                        } else if (evt.getNewValue().equals("LOADING")) {
                            this.connectedLabel.setText("Connecting...");
                            this.connectBtn.setText("Connect");
                        }
                    } else if (evt.getPropertyName().equals("UdpWebCamClient")) {
                        if (evt.getNewValue().equals("CONNECTED")) {
                            this.webCamLabel.setText("Connected");

                        } else if (evt.getNewValue().equals("DISCONNECTED")) {
                            this.webCamLabel.setText("Disconnected");

                        } else if (evt.getNewValue().equals("LOADING")) {
                            this.webCamLabel.setText("Connecting...");
                        }

                    } else if (evt.getPropertyName().equals("TcpSlamClient")) {
                        if (evt.getNewValue().equals("CONNECTED")) {
                            this.slamCamLabel.setText("Connected");

                        } else if (evt.getNewValue().equals("DISCONNECTED")) {
                            this.slamCamLabel.setText("Disconnected");

                        } else if (evt.getNewValue().equals("LOADING")) {
                            this.slamCamLabel.setText("Connecting...");
                        }

                    } else if (evt.getPropertyName().equals("TcpMotorClient")) {
                        if (evt.getNewValue().equals("CONNECTED")) {
                            this.manualModeLabel.setText("Connected");

                        } else if (evt.getNewValue().equals("DISCONNECTED")) {
                            this.manualModeLabel.setText("Disconnected");

                        } else if (evt.getNewValue().equals("LOADING")) {
                            this.manualModeLabel.setText("Connecting...");
                        }

                    }
                }
        );
    }

    /**
     * Handles what happens when the start video button is pressed.
     */
    @FXML
    private void doHandleVideoStart() {
        this.commOut.doSendMainString("WEBCAM:START");
        this.commIn.doStartWebCam(this.webcam, this);
    }

    /**
     * Handles what happens when the stop video button is pressed.
     */
    @FXML
    private void doHandleVideoStop() {
        this.commOut.doSendMainString("WEBCAM:STOP");
        this.commIn.doStopWebCam();
    }

    /**
     * Starts the slam cam.
     */
    @FXML
    private void doStartSlamCam() {
        this.commOut.doSendMainString("SLAM:START");
        this.commIn.doStartSlamCam(this.slamCam, this);
    }

    /**
     * Stops the slam cam.
     */
    @FXML
    private void doStopSlamCam() {
        this.commOut.doSendMainString("SLAM:STOP");
        this.commIn.doStopSlamCam();
    }

    /**
     * Send string if it is not "".
     *
     * @param event Key pressed or released event.
     */
    @FXML
    private void doHandleKeyInput(KeyEvent event) {
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
    @FXML
    private void doHandleSliderInput() {
        Double value = this.slider.getValue();
        int number = value.intValue();
        this.commOut.doSendMotorString("SPEED:" + number);
    }

    /**
     * Handles what happens when manual mode is activated.
     */
    @FXML
    private void doHandleManualMode() {
        if (this.manualMode.isSelected()) {
            this.commOut.doSendMainString("MOTORCONTROLLER:START");
            this.commOut.doConnectMotorController(this);
            this.slider.setDisable(false);
        } else if (!this.manualMode.isSelected()) {
            this.commOut.doSendMainString("MOTORCONTROLLER:STOP");
            this.commOut.doCloseMotorSocket();
            this.slider.setDisable(true);
        }
    }

    /**
     * Stops all open connections.
     */
    private void doDisconnect() {
        this.commIn.doStopSlamCam();
        this.commIn.doStopWebCam();
        this.commOut.doCloseMotorSocket();
        this.commOut.doSendMainString("STOP");
        this.commOut.doCloseMainSocket();
        doDisableBtn(true);
    }

    /**
     * Disables buttons in GUI.
     * @param doDisable True to disable buttons, false to enable.
     */
    private void doDisableBtn(boolean doDisable) {
        this.webCamStart.setDisable(doDisable);
        this.webCamStop.setDisable(doDisable);
        this.slamCamStart.setDisable(doDisable);
        this.slamCamStop.setDisable(doDisable);
        this.manualMode.setDisable(doDisable);

    }
}
