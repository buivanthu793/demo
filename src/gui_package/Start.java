package gui_package;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class Start extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(String.valueOf(Start.class.getResource("views/images/logo.png")));
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("views/fxml/main-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Dictionary Ultra Pro");
        stage.getIcons().add(icon);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        //Link Start !!!
        launch();
    }
}