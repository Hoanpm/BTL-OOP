package uet.oop.bomberman.GameDisplay;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.Controller.GamePlay;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Buff.*;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.StillObject.Brick;
import uet.oop.bomberman.graphics.TimeCount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    private Canvas canvas;
    private GraphicsContext gc;
    public static int WIDTH;
    public static int HEIGHT;
    public static final int screen_width = 992;
    public static final int screen_height = 480;
    public int Level = 1;
    public static List<Buff> buffs = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
    public static List<Enemy> enemies = new CopyOnWriteArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static char[][] map_ = new char[100][100];
    public static Bomber bomber = new Bomber();
    private boolean isRunning = true;
    private int delayrenderLvl = 0;
    private final Text text = new Text();
    public static Text lives = new Text();
    public static Text time_ = new Text();
    public Text levelDisplay = new Text();
    public static Text score = new Text();
    public static int score_ = 0;
    GamePlay gamePlay = new GamePlay();
    TimeCount timeCount = new TimeCount();

    public void gamescreen(javafx.event.ActionEvent actionEvent) {
//        Sound.stopmenu();
//        Sound.playStartgame();
//        Sound.playbgSound();

        Map map = new Map();
        List<String> str = map.createMap();
        canvas = new Canvas(screen_width, screen_height);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root, Color.rgb(0, 0, 0));

        renderLevel(root,scene);

        // Them scene vao stage
        GamePlay.stage.setScene(scene);
        GamePlay.stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (delayrenderLvl == 121) {
                    root.getChildren().remove(text);
                    setUpgame(root, scene);
                    delayrenderLvl++;
                }
                if (delayrenderLvl > 120) {
                    if (isRunning) {
                        if (timeCount.time > 0 && Bomber.NumberOfLives > 0) {
                            if (!Portal.isStepOn) {
                                render();
                                update(scene);
                            } else checkLevelChange(actionEvent, this);
                        } else switchGO(actionEvent, this);
                    }
                }
                if (delayrenderLvl < 122) delayrenderLvl++;
            }
        };
        timer.start();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        buffs.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        for (int i=0; i<Bomber.bombList.size(); i++) {
            Bomber.bombList.get(i).render(gc);
            for (int j=0; j<Bomber.bombList.get(i).flame_bombs.size(); j++) {
                Bomber.bombList.get(i).flame_bombs.get(j).render(gc);
            }
            for (int j=0; j<Bomber.bombList.get(i).flame_last_bombs.size(); j++) {
                Bomber.bombList.get(i).flame_last_bombs.get(j).render(gc);
            }
        }
        bomber.render(gc);
    }

    public void update(Scene scene) {
        try {
            for (int i = 0; i < Bomber.bombList.size(); i++) {
                Bomber.bombList.get(i).update(scene);
                for (int j=0; j<Bomber.bombList.get(i).flame_bombs.size(); j++) {
                    Bomber.bombList.get(i).flame_bombs.get(j).update(scene);
                }
                for (int j=0; j<Bomber.bombList.get(i).flame_last_bombs.size(); j++) {
                    Bomber.bombList.get(i).flame_last_bombs.get(j).update(scene);
                }
            }
        } catch (IndexOutOfBoundsException ignored) {}
        for (Brick brick : bricks) {
            brick.update(scene);
        }
        bomber.update(scene);
        for (Enemy enemy : enemies) {
            enemy.update(scene);
        }
        timeCount.update();
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

    public void reset() {
        buffs.clear();
        bricks.clear();
        enemies.clear();
        stillObjects.clear();
        Bomber.bombList.clear();
        Bomber.NumberOfLives = 5;
        bomber.setSetBomb_(false);
        bomber.checkdie = false;
        bomber.setDelaydie(0);
        delayrenderLvl = 0;
        Portal.isStepOn = false;
        Flame_Item.isStepOn = false;
        Bomb_Item.isStepOn = false;
        Speed_Item.isStepOn = false;
        timeCount.time = 300;
        timeCount.count = 300;
        score_ = 0;
    }

    public void nextLevel() {
        buffs.clear();
        bricks.clear();
        enemies.clear();
        stillObjects.clear();
        Bomber.bombList.clear();
        Portal.isStepOn = false;
        delayrenderLvl = 0;
        timeCount.time = 300;
        timeCount.count = 0;
    }

    public void setUpgame(Group root, Scene scene) {
        javafx.scene.image.Image heart = new javafx.scene.image.Image("file:res/FXML_Image/miniheart.png");
        ImageView imageView = new ImageView(heart);
        imageView.setX(7);
        imageView.setY(7);
        root.getChildren().add(imageView);

        lives = new Text("" + Bomber.NumberOfLives);
        lives.setFill(Color.WHITE);
        lives.setX(64);
        lives.setY(44);
        lives.setFont(Font.font("Verdana", 30));
        root.getChildren().add(lives);

        levelDisplay = new Text("Level: " + Level);
        levelDisplay.setFill(Color.WHITE);
        levelDisplay.setX(435);
        levelDisplay.setY(44);
        levelDisplay.setFont(Font.font("Verdana", 30));
        root.getChildren().add(levelDisplay);

        time_ = new Text("Time: " + timeCount.time);
        time_.setFill(Color.WHITE);
        time_.setX(180);
        time_.setY(44);
        time_.setFont(Font.font("Verdana", 30));
        root.getChildren().add(time_);

        score = new Text("Score: " + score_);
        score.setFill(Color.WHITE);
        score.setX(650);
        score.setY(44);
        score.setFont(Font.font("Verdana", 30));
        root.getChildren().add(score);

        javafx.scene.image.Image pause = new javafx.scene.image.Image("file:res/FXML_Image/pause.png");
        ImageView imageView2 = new ImageView(pause);
        imageView2.setX(910);
        imageView2.setY(7);
        root.getChildren().add(imageView2);

        imageView2.setOnMouseClicked(mouseEvent -> isRunning = !isRunning);
    }

    public void checkLevelChange(ActionEvent actionEvent, AnimationTimer timer) {
        int t = Level + 1;
        if (t <= 2) {
            Level++;
            nextLevel();
            gamescreen(actionEvent);
            timer.stop();
        } else {
            reset();
            try {
                gamePlay.switchToVictoryScene(actionEvent);
                timer.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void switchGO(ActionEvent actionEvent, AnimationTimer timer) {
        try {
            reset();
            gamePlay.switchToGameOverScene(actionEvent);
            timer.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}