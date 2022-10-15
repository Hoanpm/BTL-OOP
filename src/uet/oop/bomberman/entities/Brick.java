package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Brick extends Entity {
    private boolean isDes = false;
    public Brick(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public static void checkDes(Bomb bomb) {
        List<Brick> c_ = BombermanGame.bricks;
        char[][] b = BombermanGame.map_;
        int x = bomb.getX();
        int y = bomb.getY();

        int x_buff = -1;
        int y_buff = -1;
        if (BombermanGame.buffs.size() > 0) {
            x_buff = BombermanGame.buffs.get(0).getX();
            y_buff = BombermanGame.buffs.get(0).getY();
        }

        for (int i=0; i<c_.size(); i++) {
            int x_ = c_.get(i).getX();
            int y_ = c_.get(i).getY();
            if (y_ == y) {
                if (bomb.isBuff()) {
                    if (x_ == x + 64 && b[y / 32][x / 32 + 1] == ' '
                            || x_ == x - 64 && b[y / 32][x / 32 - 1] == ' '
                            || x_ == x + 32 || x_ == x - 32) {
                        c_.get(i).setDes(true);
                        if (x_ == x_buff && y_ == y_buff) {
                            BombermanGame.buffs.get(0).setIndexBrick(i);
                        }
                    }
                } else {
                    if (x_ == x + 32 || x_ == x - 32) {
                        c_.get(i).setDes(true);
                        if (x_ == x_buff && y_ == y_buff) {
                            BombermanGame.buffs.get(0).setIndexBrick(i);
                        }
                    }
                }
            } else if (x_ == x) {
                if (bomb.isBuff()) {
                    if (y_ == y + 64 && b[y / 32 + 1][x / 32] == ' '
                            || y_ == y - 64 && b[y / 32 - 1][x / 32] == ' '
                            || y_ == y + 32 || y_ == y - 32) {
                        c_.get(i).setDes(true);
                        if (x_ == x_buff && y_ == y_buff) {
                            BombermanGame.buffs.get(0).setIndexBrick(i);
                        }
                    }
                } else {
                    if (y_ == y + 32 || y_ == y - 32) {
                        c_.get(i).setDes(true);
                        if (x_ == x_buff && y_ == y_buff) {
                            BombermanGame.buffs.get(0).setIndexBrick(i);
                        }
                    }
                }
            }
        }
    }

    public void chooseSprite() {
        if (isDes) {
            sprite_ = Sprite.movingSprite(Sprite.brick_exploded,
                    Sprite.brick_exploded1, Sprite.brick_exploded2,
                    Bomb.delayTime, 30);
        }
    }

    @Override
    public void update(Scene scene) {}

    public void render(GraphicsContext gc) {
        if (Bomb.delayTime > 150 && Bomb.delayTime <= 180 && isDes) {
            chooseSprite();
            Image new_img = sprite_.getFxImage();
            gc.drawImage(new_img, x, y);
        } else gc.drawImage(img, x, y);
    }

    public boolean isDes() {
        return isDes;
    }

    public void setDes(boolean des) {
        isDes = des;
    }
}
