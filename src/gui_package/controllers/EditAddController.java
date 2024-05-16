package gui_package.controllers;

import gui_package.models.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;

import static gui_package.models.MainModel.*;

public class EditAddController {
    private double x;
    private double y;


    @FXML
    private Button setEditButton;

    @FXML
    private ImageView button_close_icon;

    @FXML
    private TextField wordTypeTextField;

    @FXML
    private TextField pronunciationTextField;

    @FXML
    private TextArea meaningTextArea;

    @FXML
    private TextArea exampleTextArea;

    @FXML
    private Button removeWordButton;

    @FXML
    private TextField addWordTarget;

    @FXML
    private TextField addWordType;

    @FXML
    private TextField addWordPronunciation;

    @FXML
    private TextArea addWordMeaning;

    @FXML
    private TextArea addWordExample;

    @FXML
    public void titleBarDragged(MouseEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    public void titleBarPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void close_button_highlight(MouseEvent mouseEvent) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        button_close_icon.setEffect(colorAdjust);
    }

    public void close_button_reset(MouseEvent mouseEvent) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        button_close_icon.setEffect(colorAdjust);
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    public void removeSelectedWord(ActionEvent event) {
        Button okButton = DialogController.appear(((Node) event.getSource()).getScene(), true, "Confirm Removal",
                "Are you sure you want to remove this word?");
        okButton.setOnAction(eventHandler -> {
            try {
                removeWord(DictionaryController.currentWordId);
                Button ts = DialogController.appear(((Node) event.getSource()).getScene(), false, "Success", "Từ đã được xoá, hãy tìm kiếm lại để xem thay đổi."); //chinh true thanh false de an nut cancel
                ts.setOnAction(eventHandlers -> DialogController.okay());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void editedWordContent(ActionEvent event) throws SQLException {
        updateWord(DictionaryController.currentWordId, wordTypeTextField.getText(),
                meaningTextArea.getText(), pronunciationTextField.getText(), exampleTextArea.getText());
        Button ts = DialogController.appear(((Node) event.getSource()).getScene(), false, "Success", "Từ đã được cập nhật thành công, hãy tìm kiếm lại để xem thay đổi."); //chinh true thanh false de an nut cancel
        ts.setOnAction(eventHandler -> DialogController.okay());
    }

    @FXML
    public void addWord(ActionEvent event) throws SQLException {
        if (findWord(addWordTarget.getText())) {
            Word word = new Word(addWordTarget.getText(), addWordType.getText(),
                    addWordMeaning.getText(), addWordPronunciation.getText(), addWordExample.getText());
            createWord(word);
            Button ts = DialogController.appear(((Node) event.getSource()).getScene(), false, "Success", "Từ đã được thêm thành công, hãy tìm kiếm lại để tìm từ."); //chinh true thanh false de an nut cancel
            ts.setOnAction(eventHandler -> DialogController.okay());
        } else {
            Button ts = DialogController.appear(((Node) event.getSource()).getScene(), false, "Error", "Từ đã tồn tại, vui lòng sửa từ nếu muốn thay đổi thông tin từ."); //chinh true thanh false de an nut cancel
            ts.setOnAction(eventHandler -> DialogController.okay());
        }
    }
}
