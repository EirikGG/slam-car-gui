package sdv.communication;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class Webcamera {
    // Webcamera.
    private Webcam webcam;


    public Webcamera(Webcam webcam) {
        this.webcam = Webcam.getDefault();
    }

    public void preStart() throws Exception {
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();
    }

    public void postStop() throws Exception {
        webcam.close();
    }

    public BufferedImage getImage() {
        return webcam.getImage();
    }
}