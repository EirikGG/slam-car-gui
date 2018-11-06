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
    // Ip address.
    private String ip;
    // Port.
    private int port;

    /**
     * Creates new socket and defines the addresses.
     */
    public CommOut(String ip, int port) {
        this.motorController = new TcpClient(ip, port);
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
            this.motorController.interrupt();
        this.motorController.start();
    }
}
