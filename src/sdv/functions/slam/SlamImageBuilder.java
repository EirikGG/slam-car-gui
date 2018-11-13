package sdv.functions.slam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Takes a byte array and builds a Image.
 *
 * @author Eirik G. Gustafsson
 * @version 13.11.2018.
 */
public class SlamImageBuilder {
    // Maps size in pixels, default value is 820.
    private int mapSize = 820;


    public SlamImageBuilder() {
        this.mapSize = 820;
    }

    /**
     * Takes the byte array and assembles it as a image.
     *
     * @param recivedData Byte array of image.
     * @return Image.
     * @throws IOException Cant read byteArray.
     */
    private BufferedImage doAssembleImage(byte[] recivedData) {
        // Initialises the BufferedImage to be returned.
        BufferedImage img = null;

        // Gets raw data and converts it to buffered image.
        try {
            img = ImageIO.read(new ByteArrayInputStream(recivedData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
