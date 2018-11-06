package sdv.coupling;

import sdv.comm.TcpClient;

/**
 * Interface for communication the gui is sending "out".
 *
 * @author Eirik G. Gustafsson
 * @version 30.10.2018.
 */
public class CommOut {
    // Socket for communicating with motor controller.
    private TcpClient motorController;

    /**
     * Creates new socket and defines the addresses.
     */
    public CommOut() {
        this.motorController = new TcpClient("192.168.0.100", 8000);
    }

    /**
     * Sends strings to motor controller class.
     *
     * @param str String to send.
     */
    public void doSendMotorString(String str) {
        this.motorController.doWriteString(str);
    }

    /**
     * Reconnects the socket if the connection is broken or never connected.
     */
    public void doConnect() {
        this.motorController.start();
    }
}
