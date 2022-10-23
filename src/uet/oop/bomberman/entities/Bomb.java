package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    public static int delayTime = 0;
    private boolean isBuff = false;

    public static List<Flame_obj> flame_objs = new ArrayList<>();

    public Bomb(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void checkDelay() {
        if (delayTime < 180) delayTime++;
        else {
            Bomber.bombList.clear();
            flame_objs.clear();

            for (int i=0; i<BombermanGame.bricks.size(); i++) {
                if (BombermanGame.bricks.get(i).isDes()) {
                    int x = BombermanGame.bricks.get(i).getX();
                    int y = BombermanGame.bricks.get(i).getY();
                    BombermanGame.map_[y/32][x/32] = ' ';
                    BombermanGame.bricks.remove(i);
                    if (BombermanGame.buffs.size() > 0
                        && i == BombermanGame.buffs.get(0).getIndexBrick()) {
                        BombermanGame.buffs.get(0).setRevealed(true);
                    }
                    i--;
                }
            }

            delayTime = 0;
        }
    }

    public void chooseSprite() {
        if (delayTime <= 150)
            sprite_ = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, delayTime, 50);
        if (delayTime > 150 && delayTime <= 180) {
            BombermanGame.map_[getY()/32][getX()/32] = ' ';
            sprite_ = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, delayTime, 30);
            if (delayTime == 151)
                Sound.playbomExplose();
        }
    }

    public void update(Scene scene_){
        checkDelay();
    }

    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }

    public boolean isBuff() {
        return isBuff;
    }

    public void setBuff(boolean buff) {
        isBuff = buff;
    }
}
