package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    public static int delayTime = 0;

    public Bomb(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void checkDelay() {
        if (delayTime <= 180) delayTime++;
        else {
            Bomber.bombList.remove(0);
            Bomber.flame_last_bombs.clear();
            Bomber.flame_bombs.clear();
            delayTime = 0;
        }
    }

    public void chooseSprite() {
        if (delayTime <= 150)
            sprite_ = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, delayTime, 50);
        if (delayTime > 150 && delayTime <= 180)
            sprite_ = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, delayTime, 30);
    }

    public void update(Scene scene_){
        checkDelay();
    }

    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }

}
