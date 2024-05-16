package gui_package.controllers;

import gui_package.Start;
import gui_package.models.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class GameController {
    @FXML
    private Label c11;

    private int currentRow = 1;

    private int streak = 0;

    private String wordRow = "";

    private String selectedWord = MainModel.getWordleWord().toUpperCase();

    private boolean isGameFinished = false;

    private boolean isWin = false;

    private boolean hasInitStats = true;

    private final boolean[] keyboard = new boolean[26];

    @FXML
    private Stage stage;
    private Stage howToPlay;
    private Stage gameStats;
    private Stage gameEnd;

    private wordleStatisticsController statistics;
    private wordleEndController endScreen;

    public GameController() throws SQLException { }

    @FXML
    private void mouseClick(MouseEvent event) throws SQLException {
        SoundController.makeSound("click");
        String objectId = event.getPickResult().getIntersectedNode().getId();
        if (Objects.equals(objectId, "backspaceButton")) {
            backspace(event);
        } else {
            Label label = (Label) event.getSource();
            String labelText = label.getText();
            if (Objects.equals(labelText, "Enter")) {
                enter(event);
            } else {
                addCharacter(event, labelText);
            }
        }
    }

    @FXML
    private void gameInfo() throws IOException {
        SoundController.makeSound("click");
        if (howToPlay != null) {
            howToPlay.show();
            stage.getScene().getRoot().setEffect(new BoxBlur());
        } else {
            FXMLLoader loader = new FXMLLoader(GameController.class.getResource("/fxml/wordle_howtoplay.fxml"));
            Parent root = loader.load();
            if (stage == null) {
                stage = (Stage) (c11.getScene().getWindow());
            }
            double x = stage.getX();
            double y = stage.getY();

            howToPlay = new Stage();
            Scene scene = new Scene(root, 280, 300);
            howToPlay.setTitle("Dictionary Ultra Pro");
            howToPlay.getIcons().add(new Image(String.valueOf(Start.class.getResource("views/images/logo.png"))));
            howToPlay.initStyle(StageStyle.UNDECORATED);
            howToPlay.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    howToPlay.hide();
                    stage.getScene().getRoot().setEffect(null);
                }
            });
            howToPlay.setResizable(false);
            howToPlay.setScene(scene);
            howToPlay.show();
            howToPlay.setX(x + 372);
            howToPlay.setY(y + 234);

            stage.getScene().getRoot().setEffect(new BoxBlur());
        }
    }

    @FXML
    private void restartGame(ActionEvent event) {
        SoundController.makeSound("click");
        Button ts = DialogController.appear(((Node) event.getSource()).getScene(), true, "Alert", "Do you want to start a new game ?");
        ts.setOnAction(eventHandler -> {
            try {
                Scene scene = c11.getScene();
                // 5x6 board
                for (int i = 1; i <= 6; i++) {
                    for (int j = 1; j <= 5; j++) {
                        Label label = (Label) scene.lookup("#c" + i + j);
                        label.setText("");
                        StackPane stackPane = (StackPane) label.getParent();
                        stackPane.setStyle("-fx-background-color: white;-fx-background-radius: 5;-fx-border-color: #d3d6da;-fx-border-radius:5;-fx-border-width:2;");
                    }
                }

                // A-Z keyboard
                for (char c = 'A'; c <= 'Z'; c++) {
                    StackPane stackPaneChar = (StackPane) (scene.lookup("#" + c));
                    stackPaneChar.setStyle("-fx-background-color: #d3d6da;-fx-background-radius:5;");
                    Label labelChar = (Label) stackPaneChar.getChildren().get(0);
                    labelChar.setStyle("-fx-text-fill: black;");
                }

                // Reset keyboard color
                java.util.Arrays.fill(keyboard, false);

                if (gameStats == null) {
                    hasInitStats = false;
                    try {
                        userStatistic();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (!isWin && !isGameFinished) {
                    statistics.setGameCount(statistics.getGameCount() + 1);
                    streak = 0;
                }

                wordRow = "";
                currentRow = 1;
                selectedWord = MainModel.getWordleWord().toUpperCase();
                isWin = false;
                isGameFinished = false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Run when press okay
            DialogController.okay();
        });
    }

    @FXML
    private void userStatistic() throws IOException {
        SoundController.makeSound("click");
        if (gameStats != null) {
            statistics.update();
            gameStats.show();
            stage.getScene().getRoot().setEffect(new BoxBlur());
        } else {
            FXMLLoader loader = new FXMLLoader(GameController.class.getResource("/fxml/wordle_statistics.fxml"));
            Parent root = loader.load();
            if (stage == null) {
                stage = (Stage) (c11.getScene().getWindow());
            }
            double x = stage.getX();
            double y = stage.getY();

            gameStats = new Stage();
            Scene scene = new Scene(root, 400, 360);
            gameStats.setTitle("Dictionary Ultra Pro");
            gameStats.getIcons().add(new Image(String.valueOf(Start.class.getResource("views/images/logo.png"))));
            gameStats.initStyle(StageStyle.UNDECORATED);
            gameStats.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    gameStats.hide();
                    stage.getScene().getRoot().setEffect(null);
                }
            });
            gameStats.setResizable(false);
            gameStats.setScene(scene);

            if (hasInitStats) {
                gameStats.show();
                stage.getScene().getRoot().setEffect(new BoxBlur());
            } else {
                hasInitStats = true;
            }

            gameStats.setX(x + 312);
            gameStats.setY(y + 204);
            statistics = loader.getController();
        }
    }

    private void addCharacter(MouseEvent event, String character) {
        if (currentRow <= 6 && wordRow.length() < 5 && !isGameFinished) {
            wordRow += character;
            Label label = (Label) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + wordRow.length());
            label.setText(character);
            label.setTextFill(Color.BLACK);
        }
    }

    private void backspace(MouseEvent event) {
        if (!wordRow.isEmpty()) {
            Label label = (Label) ((StackPane) event.getSource()).getScene().lookup("#c" + currentRow + wordRow.length());
            label.setText("");
            wordRow = wordRow.substring(0, wordRow.length() - 1);
        }
    }

    private void enter(MouseEvent event) throws SQLException {
        if (wordRow.length() == 5 && currentRow <= 6) {
            String checkValidWord = MainModel.verifyWordleWord(wordRow);
            String temp = selectedWord;
            boolean[] flag = new boolean[5];
            if (checkValidWord != null) {
                for (int i = 0; i < wordRow.length(); i++) {
                    Label label = (Label) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + (i + 1));
                    label.setTextFill(Color.WHITE);
                    StackPane stackPane = (StackPane) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + (i + 1)).getParent();
                    char c = wordRow.charAt(i);
                    StackPane stackPaneChar = (StackPane) ((Label) event.getSource()).getScene().lookup("#" + c);
                    Label labelChar = (Label) stackPaneChar.getChildren().get(0);
                    if (temp.indexOf(c) != -1 && temp.charAt(i) == c) {
                        flag[i] = true;
                        temp = temp.replaceFirst(String.valueOf(c), "0");
                        stackPane.setStyle("-fx-background-color: #6aaa64;-fx-background-radius:5;");
                        stackPaneChar.setStyle("-fx-background-color: #6aaa64;-fx-background-radius:5;");
                        keyboard[c - 65] = true;
                    } else {
                        stackPane.setStyle("-fx-background-color: #787c7e;-fx-background-radius:5;");
                        if (!keyboard[c - 65]) {
                            stackPaneChar.setStyle("-fx-background-color: #787c7e;-fx-background-radius:5;");
                        }
                    }
                    labelChar.setStyle("-fx-text-fill: white;");
                }

                for (int i = 0; i < wordRow.length(); i++) {
                    Label label = (Label) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + (i + 1));
                    label.setTextFill(Color.WHITE);
                    StackPane stackPane = (StackPane) ((Label) event.getSource()).getScene().lookup("#c" + currentRow + (i + 1)).getParent();
                    char c = wordRow.charAt(i);
                    StackPane stackPaneChar = (StackPane) ((Label) event.getSource()).getScene().lookup("#" + c);
                    if (temp.indexOf(c) != -1 && temp.charAt(i) != c && !flag[i]) {
                        flag[i] = true;
                        temp = temp.replaceFirst(String.valueOf(c), "0");
                        stackPane.setStyle("-fx-background-color: #c9b458;-fx-background-radius:5;");
                        if (!keyboard[c - 65]) {
                            stackPaneChar.setStyle("-fx-background-color: #c9b458;-fx-background-radius:5;");
                        }
                    }
                }

                if (wordRow.equals(selectedWord)) {
                    isGameFinished = true;
                    isWin = true;
                    gameFinishNoti();
                } else if (currentRow == 6) {
                    isGameFinished = true;
                    isWin = false;
                    gameFinishNoti();
                }
                currentRow += 1;
                wordRow = "";
            }
        }
    }

    private void gameFinishNoti() {
        if (gameEnd != null) {
            gameEnd.show();
            stage.getScene().getRoot().setEffect(new BoxBlur());
        } else {
            FXMLLoader loader = new FXMLLoader(GameController.class.getResource("/fxml/wordle_endscreen.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (stage == null) {
                stage = (Stage) (c11.getScene().getWindow());
            }
            double x = stage.getX();
            double y = stage.getY();

            gameEnd = new Stage();
            Scene scene = new Scene(root, 240, 210);
            gameEnd.setTitle("Dictionary Ultra Pro");
            gameEnd.getIcons().add(new Image(String.valueOf(Start.class.getResource("views/images/logo.png"))));
            gameEnd.initStyle(StageStyle.UNDECORATED);
            gameEnd.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    gameEnd.hide();
                    stage.getScene().getRoot().setEffect(null);
                }
            });
            gameEnd.setResizable(false);
            gameEnd.setScene(scene);
            gameEnd.show();
            gameEnd.setX(x + 392);
            gameEnd.setY(y + 279);

            stage.getScene().getRoot().setEffect(new BoxBlur());
            endScreen = loader.getController();
        }
        endScreen.update(isWin, selectedWord.toLowerCase(), currentRow);

        if (gameStats == null) {
            hasInitStats = false;
            try {
                userStatistic();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (isWin) {
            streak++;
            statistics.setCurrStreak(streak);
            if (streak > statistics.getBestStreak()) {
                statistics.setBestStreak(streak);
            }
            statistics.setTotalWins(statistics.getTotalWins() + 1);
            statistics.addCount(currentRow);
        } else {
            statistics.setCurrStreak(0);
        }
        statistics.setGameCount(statistics.getGameCount() + 1);
    }
}
