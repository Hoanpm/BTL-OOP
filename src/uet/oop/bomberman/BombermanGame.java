package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public int WIDTH;
    public int HEIGHT;
    
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static List<String> str = new ArrayList<>();
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tao map
        str = createMap();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        stage.setTitle("Bomberman");

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update(scene);
            }
        };

        timer.start();
    }

    public List<String> createMap() {
        List<String> str = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level1.txt");
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
                    Entity obj;
                    switch (str.get(i + 1).charAt(j)) {
                        case '#' :
                            obj = new Wall(j, i, Sprite.wall);
                            stillObjects.add(obj);
                            break;
                        case ' ' :
                            obj = new Grass(j, i, Sprite.grass);
                            stillObjects.add(obj);
                            break;
                        case '*' :
                            obj = new Grass(j, i, Sprite.grass);
                            stillObjects.add(obj);
                            obj = new Brick(j, i, Sprite.brick);
                            entities.add(obj);
                            break;
                        case 'p' :
                            obj = new Grass(j, i, Sprite.grass);
                            stillObjects.add(obj);
                            obj = new Bomber(1, 1, Sprite.player_right);
                            entities.add(obj);
                            break;
                        case 'x' :
                            obj = new Grass(j, i, Sprite.grass);
                            stillObjects.add(obj);
                            obj = new Portal(j, i, Sprite.portal);
                            stillObjects.add(obj);
                            obj = new Brick(j, i, Sprite.brick);
                            entities.add(obj);
                            break;
                        case '1' :
                            obj = new Grass(j, i, Sprite.grass);
                            stillObjects.add(obj);
                            obj = new Balloon(j ,i, Sprite.balloom_right1);
                            entities.add(obj);
                            break;
                        case '2' :
                            obj = new Grass(j, i, Sprite.grass);
                            stillObjects.add(obj);
                            obj = new Oneal(j, i, Sprite.oneal_right1);
                            entities.add(obj);
                            break;
                        case 'f' :
                            obj = new Grass(j, i, Sprite.grass);
                            stillObjects.add(obj);
                            obj = new Flame_Item(j, i, Sprite.powerup_flames);
                            entities.add(obj);
                            obj = new Brick(j, i, Sprite.brick);
                            entities.add(obj);
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
        for (int i=0; i<entities.size(); i++)
            entities.get(i).update(scene);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        Bomber.bombList.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
