package gui_package.controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundController extends Thread {
    String option;

    public SoundController(String option) {
        this.option = option;
    }

    @Override
    public void run() {
        makeSound(option);
    }

    public static void makeSound(String option) {
        if (MainController.isMusicOn) {
            String filename = "";
            if (Objects.equals(option, "click")) {
                filename = "click.mp3";
            } else if (Objects.equals(option, "popup")) {
                filename = "popup.mp3";
            } else if (Objects.equals(option, "tab")) {
                filename = "tab.mp3";
            }
            Media hit = new Media(Objects.requireNonNull(SoundController.class.getResource("/sounds/" + filename)).toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
    }
}
