package sdv.gui.controller.control.sys;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sdv.coupling.CommOut;

/**
 * Handles arrow key inputs from user, to manually control car.
 *
 * @author Eirik G. Gustafsson
 * @version 16.10.2018.
 */
public class HandleKeyboardInput {
    // Interface to communicate with motor controllers.
    private CommOut commOut;
    // Holds last key press.
    private boolean upPress, downPress, rightPress, leftPress = false;

    /**
     * Setts interface for outgoing communication.
     *
     * @param commOut Interface for outgoing communication.
     */
    public HandleKeyboardInput(CommOut commOut) {
        this.commOut = commOut;
    }

    /**
     * Checks if key is pressed or released, and handles event.
     *
     * @param event Key pressed or released event.
     */
    public void doHandleKeyInput(KeyEvent event) {
        // String description of event.
        String eventType = event.getEventType().getName();

        if (eventType.equals("KEY_PRESSED")) {
            doHandleKeyPress(event.getCode());
        } else if (eventType.equals(("KEY_RELEASED"))) {
            doHandleKeyRelease(event.getCode());
        }
    }

    /**
     * Handles key press.
     *
     * @param code Which key is pressed.
     */
    private void doHandleKeyPress(KeyCode code) {

        if((KeyCode.UP == code) && (!this.upPress) && (!this.downPress)) {
            this.upPress = true;
            this.commOut.doSendMotorInstructions("Drive");

        } else if ((KeyCode.DOWN == code) && (!this.downPress) && (!this.upPress)) {
            this.downPress = true;
            this.commOut.doSendMotorInstructions("Drive backwards");

        } else if ((KeyCode.LEFT == code) && (!this.leftPress) && (!this.rightPress)) {
            this.leftPress = true;
            this.commOut.doSendMotorInstructions("Turn left");

        } else if ((KeyCode.RIGHT == code) && (!this.rightPress) && (!this.leftPress)) {
            this.rightPress = true;
            this.commOut.doSendMotorInstructions("Turn right");
        }
    }

    /**
     * Handles key release.
     *
     * @param code Which key is pressed.
     */
    private void doHandleKeyRelease(KeyCode code) {

        if((KeyCode.UP == code) && (!this.downPress)) {
            this.upPress = false;
            this.commOut.doSendMotorInstructions("Stop going");

        } else if ((KeyCode.DOWN == code) && (!this.upPress)) {
            this.downPress = false;
            this.commOut.doSendMotorInstructions("Stop going backwards");

        } else if ((KeyCode.LEFT == code) && (!this.rightPress)) {
            this.leftPress = false;
            this.commOut.doSendMotorInstructions("Straighten up");

        } else if ((KeyCode.RIGHT == code) && (!this.leftPress)) {
            this.rightPress = false;
            this.commOut.doSendMotorInstructions("Straighten up");
        }
    }
}
