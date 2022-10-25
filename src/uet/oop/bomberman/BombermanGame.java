package uet.oop.bomberman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.sound.Sound;
//import uet.oop.bomberman.sound.Sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BombermanGame extends Application {
    public static int WIDTH;
    public static int HEIGHT;

    public static final int screen_width = 992;
    public static final int screen_height = 480;

    public static List<Buff> buffs = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
    public static List<Enemy> enemies = new CopyOnWriteArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static char[][] map_ = new char[100][100];
    public static Bomber bomber = new Bomber();
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Sound.playmenu();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Image icon = new Image("file:res/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();
    }
}
