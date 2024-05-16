package gui_package.controllers;

import gui_package.Start;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class DialogController {

    /*
    Button ts = DialogController.appear(event, true, "title", "message"); //chinh true thanh false de an nut cancel
    ts.setOnAction(eventHandler -> {
        //run when press okay
        System.out.println("Pressed Okay");
        DialogController.okay();
    });
     */

    private static Stage popupStage;
    private static final URL fxmlURL = DialogController.class.getResource("/fxml/dialog-box.fxml");
    private static final Parent root;

    static {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(fxmlURL));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scene scene = new Scene(root, 480, 178);

    public DialogController() {
    }

    public static Button appear(Scene event, boolean isCancelDisplay, String title, String message) {
        if (popupStage == null) {
            popupStage = new Stage();
            popupStage.setTitle("Dictionary Ultra Pro");
            popupStage.getIcons().add(new Image(String.valueOf(Start.class.getResource("views/images/logo.png"))));
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);
            popupStage.setScene(scene);

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            popupStage.setX((primScreenBounds.getWidth() - 480) / 2);
            popupStage.setY((primScreenBounds.getHeight() - 178) / 2);
        }
        Button cancelButton = (Button) popupStage.getScene().lookup("#button_cancel");
        Button okayButton = (Button) popupStage.getScene().lookup("#button_okay");
        Label titleLabel = (Label) popupStage.getScene().lookup("#title");
        Label messageLabel = (Label) popupStage.getScene().lookup("#message");
        titleLabel.setText(title);
        messageLabel.setText(message);
        if (isCancelDisplay) {
            cancelButton.setVisible(true);
            cancelButton.setDisable(false);
        } else {
            cancelButton.setVisible(false);
            cancelButton.setDisable(true);
        }
        MainController.loadTheme((BorderPane) scene.lookup("#borderPane"));
        MainController.loadTheme((Button) scene.lookup("#button_cancel"));
        MainController.loadTheme((Button) scene.lookup("#button_okay"));
        MainController.loadTheme((Pane) scene.lookup("#dialogPane"));
        popupStage.show();
        return okayButton;
    }

    public void cancel(ActionEvent event) {
        popupStage.hide();
    }

    public static void okay() {
        popupStage.hide();
    }
    
    public void okay(ActionEvent actionEvent) {
        popupStage.hide();
    }
}
