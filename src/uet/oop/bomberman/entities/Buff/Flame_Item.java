package uet.oop.bomberman.entities.Buff;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Buff.Buff;
import uet.oop.bomberman.graphics.Sprite;

public class Flame_Item extends Buff {
    public static boolean isStepOn = false;

    public Flame_Item(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    @Override
    public void update(Scene scene) {}
}
