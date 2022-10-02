package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private int dx = 0, dy = 0;
    private static final int velocity = 2;
    public Bomber(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void setKey(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = -velocity;
                        break;
                    case DOWN:
                    case S:
                        dy = velocity;
                        break;
                    case LEFT:
                    case A:
                        dx = -velocity;
                        break;
                    case RIGHT:
                    case D:
                        dx = velocity;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = 0;
                        break;
                    case DOWN:
                    case S:
                        dy = 0;
                        break;
                    case LEFT:
                    case A:
                        dx = 0;
                        break;
                    case RIGHT:
                    case D:
                        dx = 0;
                        break;
                }
            }
        });
    }

    public void move()  {
        x += dx;
        y += dy;

        for (int i=0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                if (checkCollision(BombermanGame.stillObjects.get(i))) {
                    x -= dx;
                    y -= dy;
                }
            }
            if (i < BombermanGame.entities.size()) {
                if (BombermanGame.entities.get(i) instanceof Brick) {
                    if (checkCollision(BombermanGame.entities.get(i))) {
                        x -= dx;
                        y -= dy;
                    }
                }
            }
        }

    }

    @Override
    public void update(Scene scene) {
        setKey(scene);
        move();
    }
}
