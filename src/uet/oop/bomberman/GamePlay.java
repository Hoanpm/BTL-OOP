package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePlay extends BombermanGame {
    private Stage stage;
    private Scene scene;
    private GraphicsContext gc;
    private Canvas canvas;
    private int Level = 1;
    private int delayrenderLvl = 0;
    private final Text text = new Text();

    public void reset() {
        buffs.clear();
        bricks.clear();
        enemies.clear();
        stillObjects.clear();
        Bomber.bombList.clear();
        Bomb.flame_objs.clear();
        Bomber.NumberOfLives = 1;
        Bomb.NumberOfBombs = 20;
        Bomb.delayTime = 0;
        bomber.setSetBomb_(false);
        bomber.checkdie = false;
        bomber.setDelaydie(0);
        Bomber.isStepOut = false;
    }

    public List<String> createMap() {
        List<String> str = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level" + Level + ".txt");
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            while (true) {
                line = br.readLine();
                if (line == null) break;
                str.add(line);
            }

            String[] numb = str.get(0).trim().split(" ");
            HEIGHT = Integer.parseInt(numb[1]);
            WIDTH = Integer.parseInt(numb[2]);

            for (int i=0; i<HEIGHT; i++) {
                for (int j=0; j<WIDTH; j++) {
                    map_[i][j] = str.get(i+1).charAt(j);
                }
            }

            for (int i=0; i<HEIGHT; i++) {
                for (int j=0; j<WIDTH; j++) {
                    Entity obj;
                    Enemy obj_;
                    Buff buff;
                    Brick brick;
                    int i_ = i + 2;
                    switch (str.get(i+1).charAt(j)) {
                        case '#' :
                            obj = new Wall(j, i_, Sprite.wall);
                            stillObjects.add(obj);
                            break;
                        case ' ' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            break;
                        case '*' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            brick = new Brick(j, i_, Sprite.brick);
                            bricks.add(brick);
                            break;
                        case 'p' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            bomber = new Bomber(1, 3, Sprite.player_right);
                            break;
                        case 'x' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj = new Portal(j, i_, Sprite.portal);
                            stillObjects.add(obj);
                            brick = new Brick(j, i_, Sprite.brick);
                            bricks.add(brick);
                            break;
                        case '1' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Balloon(j ,i_, Sprite.balloom_right1);
                            enemies.add(obj_);
                            break;
                        case '2' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Oneal(j, i_, Sprite.oneal_right1);
                            enemies.add(obj_);
                            break;
                        case 'f' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            buff = new Flame_Item(j, i_, Sprite.powerup_flames);
                            buffs.add(buff);
                            brick = new Brick(j, i_, Sprite.brick);
                            bricks.add(brick);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cant load file!");
        }
        return str;
    }

    public void update(Scene scene) {
        for (int i=0; i<Bomber.bombList.size(); i++) {
            Bomber.bombList.get(i).update(scene);
        }
        for (int i=0; i<Bomb.flame_objs.size(); i++) {
            Bomb.flame_objs.get(i).update(scene);
        }
        bomber.update(scene);
        for (Enemy enemy : enemies) {
            enemy.update(scene);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        buffs.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        Bomber.bombList.forEach(g -> g.render(gc));
        Bomb.flame_objs.forEach(g -> g.render(gc));
        bomber.render(gc);
    }

    public void renderLevel(Group root, Scene scene) {
        text.setText("Level " + Level);
        text.setTextOrigin(VPos.TOP);
        text.setFont(Font.font("Verdana",50));
        text.layoutXProperty().bind(scene.widthProperty().subtract(text.prefWidth(-1)).divide(2));
        text.layoutYProperty().bind(scene.heightProperty().subtract(text.prefHeight(-1)).divide(2));
        text.setFill(Color.rgb(255, 255, 255));
        root.getChildren().add(text);
    }

    public void createGamePlay(javafx.event.ActionEvent actionEvent) throws IOException {
        Sound.stopmenu();
        Sound.playStartgame();
        Sound.playbgSound();
        List<String> str = createMap();
        canvas = new Canvas(screen_width, screen_height);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root, Color.rgb(68, 78, 94));

        renderLevel(root,scene);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (delayrenderLvl > 120) {
                    root.getChildren().remove(text);
                    if (Bomber.NumberOfLives > 0) {
                        render();
                        update(scene);
                    } else {
                        try {
                            reset();
                            switchToGameOverScene(actionEvent);
                            this.stop();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (delayrenderLvl < 122) delayrenderLvl++;
            }
        };
        timer.start();
    }

    public void Game(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        createGamePlay(actionEvent);

    }

    public void switchToControlGuide(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/controlGuide.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void switchToGameOverScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Sound.stopbgSound();
        Sound.playEndgame();
        Parent root = FXMLLoader.load(getClass().getResource("/GameoverScene.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

