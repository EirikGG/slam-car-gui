package sdv.gui.controller.control.sys;

import javafx.scene.input.KeyEvent;
import sdv.coupling.CommOut;

/**
 * Handles arrow key inputs from user, to manually control car. Can only use on key at the time, pressed key has to be
 * released before next key can be pressed.
 *
 * @author Eirik G. Gustafsson
 * @version 16.10.2018.
 */
public class HandleKeyboardInput {
    // Interface to communicate with motor controllers.
    private CommOut commOut;
    // String name of active key.
    private String activeKey;

    /**
     * Setts interface for outgoing communication.
     *
     * @param commOut Interface for outgoing communication.
     */
    public HandleKeyboardInput(CommOut commOut) {
        this.commOut = commOut;
        this.activeKey = "";
    }

    /**
     * Checks if key is pressed or released, and handles event.
     *
     * @param event Key event.
     */
    public void doHandleKeyEvent(KeyEvent event) {
        // Code of button event.
        String keyEvent = event.getEventType().toString().toUpperCase();
        // Event type(press of release).
        String keyName = event.getCode().toString().toUpperCase();

        // Activate a key if no other key is active.
        if (keyEvent.equals("KEY_PRESSED") && this.activeKey.equals("")) {
            this.activeKey = keyName;
            doSendKeyInfo(keyEvent, keyName);

        // Releases a key if the released key equals the last active key.
        } else if (keyEvent.equals("KEY_RELEASED") && keyName.equals(this.activeKey)) {
            doSendKeyInfo(keyEvent, keyName);
            this.activeKey = "";
        }
    }

    /**
     * Sends key events to class containing TCP socket for further communication.
     *
     * @param keyEvent KEY_RELEASED or KEY_PRESSED.
     * @param keyName Name of key used, can be: UP, DOWN, LEFT or RIGHT.
     */
    private void doSendKeyInfo(String keyEvent, String keyName) {
        this.commOut.doSendKeyPress(keyEvent, keyName);
    }
}
