package GUI.Controller.ControlSys;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;


public class ControlSys {
    // Media player.
    @FXML private MediaView mediaView;

    public ControlSys() {
    }

    @FXML private void handleStartBtnAction() {
        playVideo();
    }

    private void playVideo() {

        // Loads Video from file.
        Media media = new Media(getClass().getResource("../../Video/dummyVideo.mp4").toExternalForm());

        // Adds Video to .
        MediaPlayer player = new MediaPlayer(media);

        // Adds the Video.
        this.mediaView.setMediaPlayer(player);

        // Plays the Video.
        player.play();
    }


}
