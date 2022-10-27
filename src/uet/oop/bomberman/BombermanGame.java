package uet.oop.bomberman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.sound.Sound;

import java.io.IOException;

public class BombermanGame extends Application {
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Sound.playmenu();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML_File/menu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Image icon = new Image("file:res/FXML_Image/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();
    }
}
