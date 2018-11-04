package sdv.coupling;

import sdv.comm.TcpSocket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    // Writer for socket comm.
    private BufferedWriter writer;

    /**
     * Creates new socket and defines the addresses.
     */
    public CommOut() {
        this.motorController = new TcpSocket();
        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(this.motorController.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends inputted String trough socket.
     *
     * @param string String to send.
     */
    public void doSendMotorControllerString(String string) {
        try {
            this.writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
