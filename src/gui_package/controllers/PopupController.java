package gui_package.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PopupController {
    private double x;
    private double y;

    @FXML
    private ImageView button_close_icon;

    public void titleBarDragged(MouseEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void titleBarPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    public void close_button_highlight(MouseEvent event) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        button_close_icon.setEffect(colorAdjust);
    }

    public void close_button_reset(MouseEvent event) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        button_close_icon.setEffect(colorAdjust);
    }
}
