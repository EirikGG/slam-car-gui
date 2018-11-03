package sdv.coupling;

import java.net.Socket;

/**
 * Interface for communication the gui is sending "out".
 *
 * @author Eirik G. Gustafsson
 * @version 30.10.2018.
 */
public class CommOut {
    // Socket for communicating with motor controller.
    private Socket motorController;

    public CommOut() {
        this.motorController = new Socket();
    }

    public void doSendKeyPress(String eventType, String keyName) {

    }
}
