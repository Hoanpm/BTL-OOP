package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private int dx, dy;
    private static final int velocity = 2;
    private boolean isMove_ = false;
    private static final String MOVE_UP = "UP",
                                MOVE_DOWN = "DOWN",
                                MOVE_RIGHT = "RIGHT",
                                MOVE_LEFT = "LEFT";

    private String direction = MOVE_RIGHT;

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

    public void setDirection() {
        if (dx > 0) direction = MOVE_RIGHT;
        if (dx < 0) direction = MOVE_LEFT;
        if (dy > 0) direction = MOVE_DOWN;
        if (dy < 0) direction = MOVE_UP;
    }

    public void chooseSprite() {
        switch (direction) {
            case MOVE_RIGHT:
                this.sprite_ = Sprite.player_right;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, AnimatedEntity.animate_, 20);
                break;
            case MOVE_LEFT:
                this.sprite_ = Sprite.player_left;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, AnimatedEntity.animate_, 20);
                break;
            case MOVE_UP:
                this.sprite_ = Sprite.player_up;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, AnimatedEntity.animate_, 20);
                break;
            case MOVE_DOWN:
                this.sprite_ = Sprite.player_down;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, AnimatedEntity.animate_, 20);
                break;
            default:
                this.sprite_ = Sprite.player_right;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, AnimatedEntity.animate_, 20);
                break;
        }
    }

    public void move()  {
        setDirection();

        x += dx;
        y += dy;

        if (dx != 0 || dy != 0) isMove_ = true;
        else if (dx == 0 && dy == 0) isMove_ = false;

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
        AnimatedEntity.animate();
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }
}
