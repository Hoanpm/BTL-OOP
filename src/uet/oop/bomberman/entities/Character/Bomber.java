package uet.oop.bomberman.entities.Character;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.GameDisplay.Game;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Buff.Bomb_Item;
import uet.oop.bomberman.entities.Buff.Flame_Item;
import uet.oop.bomberman.entities.Buff.Portal;
import uet.oop.bomberman.entities.Buff.Speed_Item;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.StillObject.Brick;
import uet.oop.bomberman.entities.StillObject.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private int dx, dy;
    public int velocity = 2;
    public static int NumberOfLives = 1;
    public boolean checkdie = false;

    private int delaydie = 0;
    private boolean isMove_ = false;
    private static final String MOVE_UP = "UP",
                                MOVE_DOWN = "DOWN",
                                MOVE_RIGHT = "RIGHT",
                                MOVE_LEFT = "LEFT";

    private String direction = MOVE_RIGHT;
    private Bomb new_b;

    public static List<Bomb> bombList = new ArrayList<>();
    
    private boolean isSetBomb_ = false;

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
                        if (Bomb_Item.isStepOn) {
                            if (bombList.size() <= 1)
                                isSetBomb_ = true;
                        } else if (bombList.size() == 0) {
                            isSetBomb_ = true;
                        }
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
                y = 96;
                checkdie = false;
                delaydie = 0;
                NumberOfLives--;
                Game.lives.setText("" + NumberOfLives);
            }
            delaydie++;
        } else {

            isMove_ = dx != 0 || dy != 0;

            for (int i = 0; i < Game.buffs.size(); i++) {
                    if (checkCollision(Game.buffs.get(i)) && Game.buffs.get(i).isRevealed()) {
                        if (Game.buffs.get(i) instanceof Portal) {
                            if (Game.enemies.size() == 0)
                                Portal.isStepOn = true;
                        } else {
                            Sound.playitemGet();
                            if (Game.buffs.get(i) instanceof Flame_Item)
                                Flame_Item.isStepOn = true;
                            if (Game.buffs.get(i) instanceof Speed_Item) {
                                Speed_Item.isStepOn = true;
                            }
                            if (Game.buffs.get(i) instanceof Bomb_Item) {
                                Bomb_Item.isStepOn = true;
                            }
                            Game.buffs.remove(i);
                        }
                    }
            }

            x += dx;
            if (!canmove()) x -=dx;
            y += dy;
            if (!canmove()) y -=dy;
        }
    }

    public boolean canmove() {
        for (int i = 0; i < Game.stillObjects.size(); i++) {
            if (Game.stillObjects.get(i) instanceof Wall) {
                if (checkCollision(Game.stillObjects.get(i))) {
                    return false;
                }
            }
            if (i < Game.bricks.size()) {
                if (checkCollision(Game.bricks.get(i))) {
                    return false;
                }
            }
            if (i < bombList.size()) {
                if (bombList.get(i).isStepOut()) {
                    if (checkCollision(bombList.get(i)))
                        return false;
                }
            }
        }
        return true;
    }

    public void setBomb() {
        if (isSetBomb_) {
            new_b = new Bomb((getX() + 16) / 32, (getY()  + 16) / 32, Sprite.bomb);
            Game.map_[new_b.getY()/32 - 2][new_b.getX()/32] = 'o';
            if (Flame_Item.isStepOn) new_b.setBuff(true);
            bombList.add(new_b);
            Brick.checkDes(new_b);
            isSetBomb_ = false;
            Sound.playbomSet();
        }
    }

    public void setIsStepOut() {
        for (Bomb bomb : bombList) {
            int left_b = this.x;
            int right_b = this.x + this.sprite_.get_realWidth() * 2;
            int top_b = this.y;
            int bottom_b = this.y + this.sprite_.get_realHeight() * 2;

            int m = bomb.getX();
            int n = bomb.getY();

            if (right_b <= m || left_b >= (m + 32) || top_b >= (n + 32) || bottom_b <= n) {
                bomb.setStepOut(true);
            }
        }
    }

    @Override
    public void update(Scene scene) {
        if (Speed_Item.isStepOn) velocity = 4;
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
