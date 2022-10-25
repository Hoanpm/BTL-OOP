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

    public static void buffreveal(int x, int y, int index) {
        for (int i=0; i<BombermanGame.buffs.size(); i++) {
            int x_buff = BombermanGame.buffs.get(i).getX();
            int y_buff = BombermanGame.buffs.get(i).getY();

            if (x == x_buff && y == y_buff)
                BombermanGame.buffs.get(i).setIndexBrick(index);
        }
    }

    public static void checkDes(Bomb bomb) {
        List<Brick> c_ = BombermanGame.bricks;
        char[][] b = BombermanGame.map_;
        int x = bomb.getX();
        int y = bomb.getY();

        for (int i=0; i<c_.size(); i++) {
            int x_ = c_.get(i).getX();
            int y_ = c_.get(i).getY();
            if (y_ == y) {
                if (bomb.isBuff()) {
                    if (x_ == x + 64 && b[y / 32 - 2][x / 32 + 1] == ' '
                            || x_ == x - 64 && b[y / 32 - 2][x / 32 - 1] == ' '
                            || x_ == x + 32 || x_ == x - 32) {
                        c_.get(i).setDes(true);
                        buffreveal(x_, y_, i);
                    }
                } else {
                    if (x_ == x + 32 || x_ == x - 32) {
                        c_.get(i).setDes(true);
                        buffreveal(x_, y_, i);
                    }
                }
            } else if (x_ == x) {
                if (bomb.isBuff()) {
                    if (y_ == y + 64 && b[y / 32 - 1][x / 32] == ' '
                            || y_ == y - 64 && b[y / 32 - 3][x / 32] == ' '
                            || y_ == y + 32 || y_ == y - 32) {
                        c_.get(i).setDes(true);
                        buffreveal(x_, y_, i);
                    }
                } else {
                    if (y_ == y + 32 || y_ == y - 32) {
                        c_.get(i).setDes(true);
                        buffreveal(x_, y_, i);
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
