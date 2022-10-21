package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private int dx, dy;
    private static final int velocity = 2;

    public boolean checkdie = false;

    private int delaydie = 0;
    private boolean isMove_ = false;
    private boolean isStep_buff = false;
    private static final String MOVE_UP = "UP",
                                MOVE_DOWN = "DOWN",
                                MOVE_RIGHT = "RIGHT",
                                MOVE_LEFT = "LEFT";

    private String direction = MOVE_RIGHT;

    public static List<Bomb> bombList = new ArrayList<>();

    private boolean isSetBomb_ = false;
    private boolean isStepOut = false;

    public Bomber() {}

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
                    case SPACE:
                        if (Bomb.delayTime == 0)
                            isSetBomb_ = true;
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
        if (checkdie)
            this.sprite_ = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, AnimatedEntity.animate_, 60);
    }

    public void move() {
        setDirection();
        if (checkdie) {
            if (delaydie == 40) {
                x = 32;
                y = 32;
                checkdie = false;
                delaydie = 0;
            }
            delaydie++;
        } else {
            x += dx;
            y += dy;
            isMove_ = dx != 0 || dy != 0;

            for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
                if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                    if (checkCollision(BombermanGame.stillObjects.get(i))) {
                        x -= dx;
                        y -= dy;
                    }
                }
                if (i < BombermanGame.bricks.size()) {
                    if (checkCollision(BombermanGame.bricks.get(i))) {
                        x -= dx;
                        y -= dy;
                    }
                }
                if (i < BombermanGame.buffs.size()) {
                    if (checkCollision(BombermanGame.buffs.get(0)) && BombermanGame.buffs.get(0).isRevealed()) {
                        isStep_buff = true;
                        BombermanGame.buffs.remove(0);
                    }
                }
            }
        }
    }

    public void setBomb() {
        if (isSetBomb_) {
            Bomb new_b = new Bomb((getX() + 16) / 32, (getY()  + 16) / 32, Sprite.bomb);
            BombermanGame.map_[new_b.getY()/32][new_b.getX()/32] = 'o';
            if (isStep_buff) new_b.setBuff(true);
            bombList.add(new_b);
            Flame_obj.checkFlame(new_b);
            Brick.checkDes(new_b);
            isSetBomb_ = false;
        }
    }

    @Override
    public void update(Scene scene) {
        setKey(scene);
        AnimatedEntity.animate();
        move();
        setBomb();
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }
}
