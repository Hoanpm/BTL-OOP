package uet.oop.bomberman.entities.StillObject;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.GameDisplay.Game;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Brick extends Entity {
    private boolean isDes = false;
    private Bomb bombDes;
    public int delayD;
    public Brick(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public static void buffreveal(int x, int y, Brick brick) {
        for (int i = 0; i< Game.buffs.size(); i++) {
            int x_buff = Game.buffs.get(i).getX();
            int y_buff = Game.buffs.get(i).getY();

            if (x == x_buff && y == y_buff)
                Game.buffs.get(i).setBrickCover(brick);
        }
    }

    public static void checkDes(Bomb bomb) {
        List<Brick> c_ = Game.bricks;
        char[][] b = Game.map_;
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
                        c_.get(i).setBomb(bomb);
                        buffreveal(x_, y_, c_.get(i));
                    }
                } else {
                    if (x_ == x + 32 || x_ == x - 32) {
                        c_.get(i).setDes(true);
                        c_.get(i).setBomb(bomb);
                        buffreveal(x_, y_, c_.get(i));
                    }
                }
            } else if (x_ == x) {
                if (bomb.isBuff()) {
                    if (y_ == y + 64 && b[y / 32 - 1][x / 32] == ' '
                            || y_ == y - 64 && b[y / 32 - 3][x / 32] == ' '
                            || y_ == y + 32 || y_ == y - 32) {
                        c_.get(i).setDes(true);
                        c_.get(i).setBomb(bomb);
                        buffreveal(x_, y_, c_.get(i));
                    }
                } else {
                    if (y_ == y + 32 || y_ == y - 32) {
                        c_.get(i).setDes(true);
                        c_.get(i).setBomb(bomb);
                        buffreveal(x_, y_, c_.get(i));
                    }
                }
            }
        }
    }

    public void chooseSprite() {
        if (isDes && delayD > 150) {
            sprite_ = Sprite.movingSprite(Sprite.brick_exploded,
                    Sprite.brick_exploded1, Sprite.brick_exploded2,
                    delayD, 30);
        }
    }

    @Override
    public void update(Scene scene) {
        if (isDes) delayD++;
    }

    public void render(GraphicsContext gc) {
        if (delayD > 150 && delayD <= 180 && isDes) {
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

    public void setBomb(Bomb bomb) {
        bombDes = bomb;
    }

    public Bomb getBombDes() {
        return bombDes;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Brick) {
            Brick other = (Brick) obj;
            return x == other.getX() && y == other.getY();
        }
        return false;
    }
}
