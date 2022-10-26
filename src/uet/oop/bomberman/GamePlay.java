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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.*;
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
    private boolean isRunning = true;
    private int Level = 1;
    private int delayrenderLvl = 0;
    private final Text text = new Text();
    public static Text lives = new Text();
    public static Text bombs = new Text();

    public void reset() {
        Sound.playStartgame();
        buffs.clear();
        bricks.clear();
        enemies.clear();
        stillObjects.clear();
        Bomber.bombList.clear();
        Bomb.flame_objs.clear();
        Bomber.NumberOfLives = 100;
        Bomb.NumberOfBombs = 10000;
        Bomb.delayTime = 0;
        bomber.setSetBomb_(false);
        bomber.checkdie = false;
        bomber.setDelaydie(0);
        Bomber.isStepOut = false;
        delayrenderLvl = 0;
        Portal.isStepOn = false;
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
                            buff = new Portal(j, i_, Sprite.portal);
                            buffs.add(buff);
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
                        case '3' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Doll(j, i_, Sprite.doll_right1);
                            enemies.add(obj_);
                            break;
                        case '4' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Kondoria(j, i_, Sprite.kondoria_right1);
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
        text.setFill(Color.WHITE);
        root.getChildren().add(text);
    }

    public void setUpgame(Group root, Scene scene) {
        Image heart = new Image("file:res/miniheart.png");
        ImageView imageView = new ImageView(heart);
        imageView.setX(7);
        imageView.setY(7);
        root.getChildren().add(imageView);

        lives = new Text("" + Bomber.NumberOfLives);
        lives.setFill(Color.rgb(252, 207, 3));
        lives.setX(64);
        lives.setY(44);
        lives.setFont(Font.font("Verdana", 30));
        root.getChildren().add(lives);

        Image bomb = new Image("file:res/minibomb.png");
        ImageView imageView1 = new ImageView(bomb);
        imageView1.setX(128);
        imageView1.setY(10);
        root.getChildren().add(imageView1);

        bombs = new Text("" + Bomb.NumberOfBombs);
        bombs.setFill(Color.rgb(252, 207, 3));
        bombs.setX(180);
        bombs.setY(44);
        bombs.setFont(Font.font("Verdana", 30));
        root.getChildren().add(bombs);

        Image pause = new Image("file:res/pause.png");
        ImageView imageView2 = new ImageView(pause);
        imageView2.setX(910);
        imageView2.setY(7);
        root.getChildren().add(imageView2);

        imageView2.setOnMouseClicked(mouseEvent -> isRunning = !isRunning);
    }

    public void run(Group root, Scene scene, javafx.event.ActionEvent actionEvent, AnimationTimer timer) {
        if (delayrenderLvl == 171) {
            root.getChildren().remove(text);
            setUpgame(root, scene);
            delayrenderLvl++;
        }
        if (delayrenderLvl > 170) {
            if (isRunning) {
                if (Bomber.NumberOfLives > 0) {
                    Sound.playbgSound();
                    if (!Portal.isStepOn) {
                        render();
                        update(scene);
                    } else {
                        int t = Level + 1;
                        if (t <= 2) {
                            Level++;
                            reset();
                            try {
                                createGamePlay(actionEvent);
                                timer.stop();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                reset();
                                switchToVictoryScene(actionEvent);
                                timer.stop();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } else {
                    try {
                        reset();
                        switchToGameOverScene(actionEvent);
                        timer.stop();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else Sound.pausebgSound();
        }
        if (delayrenderLvl < 172) delayrenderLvl++;
    }

    public void createGamePlay(javafx.event.ActionEvent actionEvent) throws IOException {
        Sound.stopmenu();
        Sound.playStartgame();

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
                run(root, scene, actionEvent, this);
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
        Sound.playmenu();
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
        Sound.pausebgSound();
        Sound.playEndgame();
        Parent root = FXMLLoader.load(getClass().getResource("/GameoverScene.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToVictoryScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Sound.pausebgSound();
        Parent root = FXMLLoader.load(getClass().getResource("/VictoryScene.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}