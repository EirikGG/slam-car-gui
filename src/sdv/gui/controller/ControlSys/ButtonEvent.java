package sdv.gui.controller.ControlSys;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Eirik G. Gustafsson
 * @version 16.10.2018.
 */
public class ButtonEvent {
    // Holds last key press.
    private boolean upPress, downPress, rightPress, leftPress = false;

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
            System.out.println("Up pressed");
            this.upPress = true;

        } else if ((KeyCode.DOWN == code) && (!this.downPress) && (!this.upPress)) {
            System.out.println("Down pressed");
            this.downPress = true;

        } else if ((KeyCode.LEFT == code) && (!this.leftPress) && (!this.rightPress)) {
            System.out.println("Left pressed");
            this.leftPress = true;

        } else if ((KeyCode.RIGHT == code) && (!this.rightPress) && (!this.leftPress)) {
            System.out.println("Right pressed");
            this.rightPress = true;
        }
    }

    /**
     * Handles key release.
     *
     * @param code Which key is pressed.
     */
    private void doHandleKeyRelease(KeyCode code) {

        if((KeyCode.UP == code) && (!this.downPress)) {
            System.out.println("Up released");
            this.upPress = false;

        } else if ((KeyCode.DOWN == code) && (!this.upPress)) {
            System.out.println("Down released");
            this.downPress = false;

        } else if ((KeyCode.LEFT == code) && (!this.rightPress)) {
            System.out.println("Left released");
            this.leftPress = false;

        } else if ((KeyCode.RIGHT == code) && (!this.leftPress)) {
            System.out.println("Right released");
            this.rightPress = false;
        }
    }
}
