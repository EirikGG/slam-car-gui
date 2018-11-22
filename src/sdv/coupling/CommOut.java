package sdv.coupling;

import sdv.comm.TcpMainClient;
import sdv.comm.TcpMotorClient;
import sdv.gui.controller.control.sys.ControlSys;

/**
 * Interface for communication the gui is sending "out".
 *
 * @author Eirik G. Gustafsson
 * @version 30.10.2018.
 */
public class CommOut {
    // Socket form main communication.
    private TcpMainClient mainComm;
    // Socket for communicating with motor controller.
    private TcpMotorClient motorController;
    // Server ip.
    private String ip;
    // MotorController port.
    private int motorPort;
    // Main port.
    private int mainPort;

    /**
     * Creates new socket and defines the addresses.
     */
    public CommOut(String motorIp, int motorPort, int mainPort) {
        this.ip = motorIp;
        this.motorPort = motorPort;
        this.mainPort = mainPort;
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
     * Sends strings to motor controller class.
     *
     * @param str String to send.
     */
    public void doSendMainString(String str) {
        this.mainComm.doWriteString(str);
    }

    /**
     * Interrupts thread and starts a new one.
     */
    public void doConnectMotorController(ControlSys sys) {
        if (null != this.motorController) {
            this.motorController.doCloseSocket();
            this.motorController.interrupt();
        }
        this.motorController = new TcpMotorClient(this.ip, this.motorPort, sys);
        this.motorController.setDaemon(true);
        this.motorController.start();
    }

    /**
     * Connects main communication class.
     */
    public void doConnectMain(ControlSys controlSys) {
        if (null != this.mainComm) {
            this.mainComm.doCloseSocket();
            this.mainComm.interrupt();
        }
        this.mainComm = new TcpMainClient(controlSys, this.ip, this.mainPort);
        this.mainComm.setDaemon(true);
        this.mainComm.start();
    }

    public void doCloseMainSocket() {
        if (null != this.mainComm)
            this.mainComm.doCloseSocket();
    }

    public void doCloseMotorSocket() {
        if (null != this.motorController)
            this.motorController.doCloseSocket();
    }
}
