package gui_package.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class wordleEndController {
    @FXML
    private Label attempts;

    @FXML
    private Label message;

    @FXML
    private Label title;

    @FXML
    private Label word;

    public wordleEndController() {
    }

    public void update(boolean win, String correctWord, int atts) {
        if (win) {
            title.setText("Impressive~");
            message.setText("You figured out the word in:");
        } else {
            title.setText("You failed..");
            message.setText("You didn't find the word in:");
        }
        word.setText(correctWord);
        attempts.setText(String.valueOf(atts));
    }
}
