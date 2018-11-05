package sdv.gui.controller.control.sys;

import javafx.scene.input.KeyEvent;

/**
 * Handles arrow key inputs from user, to manually control car. Can only use on key at the time, pressed key has to be
 * released before next key can be pressed.
 *
 * @author Eirik G. Gustafsson
 * @version 16.10.2018.
 */
public class KeyboardInput {
    // String name of active key.
    private String activeKey;

    /**
     * Initializes the class.
     */
    public KeyboardInput() {
        this.activeKey = "";
    }

    /**
     * Builds and returns string containing event type and what key is used.
     *
     * @param event Key event.
     */
    public String doHandleKeyEvent(KeyEvent event) {
        // Code of button event.
        String keyEvent = event.getEventType().toString().toUpperCase();
        // Event type(press of release).
        String keyName = event.getCode().toString().toUpperCase();
        // String to be returned.
        String string = "";


        // Activate a key if no other key is active.
        if (keyEvent.equals("KEY_PRESSED") && this.activeKey.equals("")) {
            this.activeKey = keyName;
            string = keyEvent + ":" + keyName;

        // Releases a key if the released key equals the last active key.
        } else if (keyEvent.equals("KEY_RELEASED") && keyName.equals(this.activeKey)) {
            this.activeKey = "";
            string = keyEvent + ":" + keyName;
        }

        return string;
    }
}