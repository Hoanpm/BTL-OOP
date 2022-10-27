package uet.oop.bomberman.entities.Buff;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Buff.Buff;
import uet.oop.bomberman.graphics.Sprite;

public class Speed_Item extends Buff {
    public static boolean isStepOn = false;

    public Speed_Item(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    @Override
    public void update(Scene scene) {}
}
