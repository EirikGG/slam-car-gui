package sdv.coupling;

import sdv.comm.UdpMotorClient;

/**
 * Interface for communication the gui is sending "out".
 *
 * @author Eirik G. Gustafsson
 * @version 30.10.2018.
 */
public class CommOut {
    // Socket for communicating with motor controller.
    private UdpMotorClient motorController;
    // MotorController ip.
    private String ip;
    // MotorController port.
    private int port;

    /**
     * Creates new socket and defines the addresses.
     */
    public CommOut(String ip, int port) {
        this.ip = ip;
        this.port = port;
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
     * Interrupts thread and starts a new one.
     */
    public void doConnect() {
        if (null != this.motorController) {
            this.motorController.doCloseSocket();
            this.motorController.interrupt();
        }
        this.motorController = new UdpMotorClient(this.ip, this.port);
        this.motorController.start();
    }

    public boolean getIsConnected() {
        return this.motorController.getIsConnected();
    }
}
