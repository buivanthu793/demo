package gui_package.controllers;

import gui_package.services.googleTranslator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TranslatorController implements Initializable {
    private String currentLanguage = "vi";
    private Timeline timer;

    @FXML
    private Button button_swap;

    @FXML
    private Label label_left;

    @FXML
    private Label label_right;

    @FXML
    private TextArea textArea_translation;

    @FXML
    private TextArea textArea_userInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new Timeline(new KeyFrame(Duration.millis(200),
                event -> {
                    String inputText = textArea_userInput.getText();
                    if (inputText.isBlank()) {
                        textArea_translation.clear();
                        return;
                    }
                    textArea_translation.setText(googleTranslator.translate(inputText, currentLanguage));
                }));
        timer.setCycleCount(1);
    }

    @FXML
    public void swap_labels(ActionEvent event) {
        SoundController.makeSound("click");
        String temp = label_left.getText();
        label_left.setText(label_right.getText());
        label_right.setText(temp);

        temp = textArea_userInput.getText();
        textArea_userInput.setText(textArea_translation.getText());
        textArea_translation.setText(temp);

        if (currentLanguage.equals("vi")) {
            currentLanguage = "en";
        } else {
            currentLanguage = "vi";
        }

        button_swap.setDisable(true);
        Timeline wait = new Timeline(new KeyFrame(Duration.seconds(1),
                actionEvent -> button_swap.setDisable(false)));
        wait.setCycleCount(1);
        wait.play();
    }

    @FXML
    public void resetTimer(KeyEvent keyPressed) {
        timer.stop();
    }

    @FXML
    public void translate(KeyEvent keyReleased) {
        timer.play();
    }
}