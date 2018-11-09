package sdv.functions.webcamera;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

/**
 * Takes a buffered image and draws it to a JavaFx ImageView.
 *
 * @author Eirik G. Gustafsson
 * @version 25.09.2018.
 */
public class ImageDrawer {
    // ImageView to send image to.
    private ImageView imageView;

    /**
     * Setts the ImageViewer.
     *
     * @param imageView ImageViewer, displays image.
     */
    public ImageDrawer(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Takes a buffered image and draws it to the ImageView.
     *
     * @param img Image to show user.
     */
    public void drawImage(BufferedImage img) {
        this.imageView.setImage(doChangeImage(img));
    }

    /**
     * Takes a buffered image and returns it as an image.
     *
     * @param img BufferedImage, becomes Image.
     * @return Image.
     */
    private Image doChangeImage(BufferedImage img) {
        return SwingFXUtils.toFXImage(img, null);
    }

    public void doClear() {
        this.imageView.setImage(null);
    }
}
