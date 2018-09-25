package sdv.communication.webcamera;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Gets image from DatagramSocket assebles it and returns.
 *
 * @version 25.09.2018
 * @author Eirik G. Gustafsson
 */
public class Webcamera extends Thread {
    // True to stop reading, false to continue.
    private boolean stopp;
    // ImageView to draw images to.
    private ImageView imageView;
    // Read from datagram socket.
    private ReadSocket readSocket;

    /**
     * Setup for class set imageView and a port.
     *
     * @param imageView Image view to display image.
     */
    public Webcamera(ImageView imageView) throws Exception {
        this.stopp = false;
        this.imageView = imageView;
        this.readSocket = new ReadSocket();
    }


    public synchronized void run() {
        while (!stopp) {
            Image img = null;

            try {
                img = this.readSocket.getImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.imageView.setImage(img);
        }
        this.readSocket.doCloseSocket();
    }

    public void setStopp(boolean stopp) {
        this.stopp = stopp;
    }
}