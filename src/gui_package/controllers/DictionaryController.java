package gui_package.controllers;

import gui_package.Start;
import gui_package.models.Word;
import gui_package.services.tts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static gui_package.models.MainModel.*;

public class DictionaryController implements Initializable {
    private String lastWord;
    static Word currentWord;
    protected static int currentWordId = -1;
    private Stage stage;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label searchedWordLabel;

    @FXML
    private Label wordFormLabel;

    @FXML
    private TextArea wordPronunciationTextArea;

    @FXML
    private TextArea wordMeaningTextArea;

    @FXML
    private TextArea wordExampleTextArea;

    @FXML
    private Button addWordButton;

    @FXML
    private Button pronunciationButton;

    @FXML
    private Button copiedTextButton;

    @FXML
    private Button editWordButton;

    @FXML
    private BorderPane dictionaryBorderPane;

    @FXML
    void search(KeyEvent event) throws SQLException {
        List<String> ls = new ArrayList<>();
        listView.getItems().clear();
        ResultSet resultSet = getWordByString(sanitizeInput(searchBar.getText()));
        while (resultSet.next()) {
            String sword = resultSet.getString("Word");
            if (sword.isEmpty()) {
                continue;
            }
            ls.add(sword.toLowerCase());
        }

        ls.sort(Comparator.comparingInt(String::length));
        listView.getItems().addAll(ls);
    }

    @FXML
    private void listViewClicked(MouseEvent event) throws SQLException {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        currentWord = getWord(selectedItem);
        currentWordId = currentWord.getId();
        if (lastWord == null) lastWord = "";
        if (selectedItem != null && !selectedItem.equals(lastWord)) {
            searchedWordLabel.setText(selectedItem);
            wordFormLabel.setText(Objects.requireNonNull(currentWord).getWord_type());
            wordPronunciationTextArea.setText(Objects.requireNonNull(currentWord).getPronunciation());
            wordMeaningTextArea.setText(Objects.requireNonNull(currentWord).getWord_explain());
            if (Objects.equals(currentWord.getExample(), "")) { //this doesn't really work
                wordExampleTextArea.setText("No example located in database!\n" + "Từ này chưa có ví dụ!");
            } else {
                wordExampleTextArea.setText(Objects.requireNonNull(currentWord).getExample());
            }
            lastWord = selectedItem;
        }
        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: disable copying
        wordMeaningTextArea.setTextFormatter(new TextFormatter<String>(change -> {
            change.setAnchor(change.getCaretPosition());
            return change;
        }));
        wordExampleTextArea.setTextFormatter(new TextFormatter<String>(change -> {
            change.setAnchor(change.getCaretPosition());
            return change;
        }));
    }

    @FXML
    public void Pronunciation(ActionEvent event) {
        tts.speak(searchedWordLabel.getText());
    }

    @FXML
    public void copyLabel(ActionEvent event) {
        SoundController.makeSound("click");
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(searchedWordLabel.getText());
        clipboard.setContent(content);
    }

    @FXML
    private void addScene(ActionEvent event) throws IOException {
        SoundController.makeSound("click");
        URL fxmlURL = EditAddController.class.getResource("/fxml/add-box.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlURL));
        if (stage == null) {
            stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        }
        double x = stage.getX();
        double y = stage.getY();

        Stage popUp = new Stage();
        Scene scene = new Scene(root, 560, 610);
        popUp.setTitle("Dictionary Ultra Pro");
        popUp.getIcons().add(new Image(String.valueOf(Start.class.getResource("views/images/logo.png"))));
        popUp.initStyle(StageStyle.UNDECORATED);
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setResizable(false);
        popUp.setScene(scene);
        MainController.loadTheme((BorderPane) scene.lookup("#addBorderPane"));
        MainController.loadTheme((HBox) scene.lookup("#addHBox"));
        MainController.loadTheme((Button) scene.lookup("#addCloseButton"));
        MainController.loadTheme((Button) scene.lookup("#cancelButton"));
        MainController.loadTheme((Button) scene.lookup("#setEditButton"));
        popUp.show();
        popUp.setX(x + 232);
        popUp.setY(y + 79);
    }

    @FXML
    private void editScene(ActionEvent event) throws IOException {
        SoundController.makeSound("click");
        if (currentWord != null) {
            URL fxmlURL = DictionaryController.class.getResource("/fxml/edit-box.fxml");
            Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlURL));
            if (stage == null) {
                stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            }
            double x = stage.getX();
            double y = stage.getY();

            Stage popup = new Stage();
            Scene scene = new Scene(root, 560, 610);
            popup.setTitle("Dictionary Ultra Pro");
            popup.getIcons().add(new Image(String.valueOf(Start.class.getResource("views/images/logo.png"))));
            popup.initStyle(StageStyle.UNDECORATED);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.setScene(scene);
            MainController.loadTheme((BorderPane) scene.lookup("#editBorderPane"));
            MainController.loadTheme((HBox) scene.lookup("#editHBox"));
            MainController.loadTheme((Button) scene.lookup("#editCloseButton"));
            MainController.loadTheme((Button) scene.lookup("#removeWordButton"));
            MainController.loadTheme((Button) scene.lookup("#cancelButton"));
            MainController.loadTheme((Button) scene.lookup("#setEditButton"));
            ((TextField) scene.lookup("#wordTypeTextField1")).setText(currentWord.getWord_target());
            ((TextField) scene.lookup("#wordTypeTextField")).setText(currentWord.getWord_type());
            ((TextField) scene.lookup("#pronunciationTextField")).setText(currentWord.getPronunciation());
            ((TextArea) scene.lookup("#meaningTextArea")).setText(currentWord.getWord_explain());
            ((TextArea) scene.lookup("#exampleTextArea")).setText(currentWord.getExample());
            popup.show();
            popup.setX(x + 232);
            popup.setY(y + 79);
        } else {
            Button ts = DialogController.appear(((Node) event.getSource()).getScene(), false, "Alert", "Hãy chọn 1 từ để chỉnh sửa"); //chinh true thanh false de an nut cancel
            ts.setOnAction(eventHandler -> DialogController.okay());
        }
    }

    // just a poor SQL errors prevention (SQL Injection), need upgrade
    public static String sanitizeInput(String input) {
        StringBuilder filteredInput = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c != '\'' && c != '"' && c != '<' && c != '>') {
                filteredInput.append(c);
            }
        }
        return filteredInput.toString();
    }
}