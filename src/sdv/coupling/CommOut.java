package sdv.coupling;

import sdv.comm.TcpSocket;

/**
 * Interface for communication the gui is sending "out".
 *
 * @author Eirik G. Gustafsson
 * @version 30.10.2018.
 */
public class CommOut {
    // Socket for communicating with motor controller.
    private TcpSocket motorController;

    /**
     * Creates new socket and defines the addresses.
     */
    public CommOut() {
        this.motorController = new TcpSocket("192.168.0.100", 8000);
        this.motorController.start();
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
    public boolean doConnect() {
        return this.motorController.doConnect();
    }
}
