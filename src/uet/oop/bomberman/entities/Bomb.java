package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int delayTime = 0;

    public Bomb(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void update(Scene scene_){}
}
