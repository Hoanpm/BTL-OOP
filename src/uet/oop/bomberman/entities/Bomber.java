package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private int dx, dy;
    private static final int velocity = 2;
    public static int NumberOfLives = 1;
    public static boolean isDie = false;
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
    public static boolean isStepOut = false;

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
                        if (Bomb.delayTime == 0 && Bomb.NumberOfBombs > 0)
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
            NumberOfLives--;
        } else {

            isMove_ = dx != 0 || dy != 0;

            for (int i = 0; i < BombermanGame.buffs.size(); i++) {
                    if (checkCollision(BombermanGame.buffs.get(0)) && BombermanGame.buffs.get(0).isRevealed()) {
                        isStep_buff = true;
                        BombermanGame.buffs.remove(0);
                    }
            }

            x += dx;
            if (!canmove()) x -=dx;
            y += dy;
            if (!canmove()) y -=dy;
        }
    }

    public boolean canmove() {
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                if (checkCollision(BombermanGame.stillObjects.get(i))) {
                    return false;
                }
            }
            if (i < BombermanGame.bricks.size()) {
                if (checkCollision(BombermanGame.bricks.get(i))) {
                    return false;
                }
            }
        }
        if (isStepOut && bombList.size() > 0) {
            return !checkCollision(bombList.get(0));
        }
        return true;
    }

    public void setBomb() {
        if (isSetBomb_) {
            Bomb new_b = new Bomb((getX() + 16) / 32, (getY()  + 16) / 32, Sprite.bomb);
            BombermanGame.map_[new_b.getY()/32][new_b.getX()/32] = 'o';
            if (isStep_buff) new_b.setBuff(true);
            bombList.add(new_b);
            Bomb.NumberOfBombs--;
            Flame_obj.checkFlame(new_b);
            Brick.checkDes(new_b);
            isSetBomb_ = false;
        }
    }

    public void setIsStepOut() {
        if (bombList.size() > 0) {
            int left_b = this.x;
            int right_b = this.x + this.sprite_.get_realWidth() * 2;
            int top_b = this.y;
            int bottom_b = this.y + this.sprite_.get_realHeight() * 2;

            int m = bombList.get(0).getX();
            int n = bombList.get(0).getY();

            if (right_b <= m || left_b >= (m + 32) || top_b >= (n + 32) || bottom_b <= n) {
                isStepOut = true;
            }
        }
    }

    @Override
    public void update(Scene scene) {
        setIsStepOut();
        setKey(scene);
        AnimatedEntity.animate();
        setBomb();
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }

    public void setSetBomb_(boolean setBomb_) {
        isSetBomb_ = setBomb_;
        Sound.playbomSet();
    }

    public void setDelaydie(int delaydie) {
        this.delaydie = delaydie;
    }
}
