package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    public static int delayTime = 0;
    public static int NumberOfBombs = 20;
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

            for (int i=0; i<Game.bricks.size(); i++) {
                if (Game.bricks.get(i).isDes()) {
                    int x = Game.bricks.get(i).getX();
                    int y = Game.bricks.get(i).getY();
                    Game.map_[y/32 - 2][x/32] = ' ';
                    Game.bricks.remove(i);
                    for (int j = 0; j< Game.buffs.size(); j++) {
                        if (i == Game.buffs.get(j).getIndexBrick()) {
                            Game.buffs.get(j).setRevealed(true);
                        }
                    }
                    i--;
                }
            }
            Bomber.isStepOut = false;
            delayTime = 0;
        }
    }

    public void chooseSprite() {
        if (delayTime <= 150)
            sprite_ = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, delayTime, 50);
        if (delayTime > 150 && delayTime <= 180) {
            Game.map_[getY()/32 - 2][getX()/32] = ' ';
            sprite_ = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, delayTime, 30);
            if (delayTime == 151)
                Sound.playbomExplode();
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
