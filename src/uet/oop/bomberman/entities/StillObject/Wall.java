package uet.oop.bomberman.entities.StillObject;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Entity {

    public Wall(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    @Override
    public void update(Scene scene) {
    }
}
