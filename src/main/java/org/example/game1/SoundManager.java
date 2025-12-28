package org.example.game1;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private MediaPlayer engine;
    private MediaPlayer explosion;

    public SoundManager() {
        engine = new MediaPlayer(
                new Media(getClass().getResource("/engine.mp3").toExternalForm())
        );
        explosion = new MediaPlayer(
                new Media(getClass().getResource("/explosion.wav").toExternalForm())
        );
    }

    public void playEngine() {
        engine.setCycleCount(MediaPlayer.INDEFINITE);
        engine.play();
    }

    public void stopEngine() {
        engine.stop();
    }

    public void playExplosion() {
        explosion.stop();
        explosion.play();
    }
}
