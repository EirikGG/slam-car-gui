package sdv.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class ControlSys {
    // Media player.
    @FXML private MediaView mediaView;

    public ControlSys() {
    }

    /**
     * Handles what happens when the start button.
     */
    @FXML private void handleStartBtnAction() {
        playVideo();
    }

    private void playVideo() {

        // Loads video from file.
        Media media = new Media(getClass().getResource("../video/dummyVideo.mp4").toExternalForm());

        // Adds video to media player.
        MediaPlayer player = new MediaPlayer(media);

        // Adds the video.
        this.mediaView.setMediaPlayer(player);

        // Plays the video.
        player.play();
    }
}
