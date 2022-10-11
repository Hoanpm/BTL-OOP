package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    public Brick(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void chooseSprite() {
        if (isDes) {
            if (Bomb.delayTime > 150 && Bomb.delayTime <= 180) {
                sprite_ = Sprite.movingSprite(Sprite.brick_exploded,
                          Sprite.brick_exploded1, Sprite.brick_exploded2,
                          Bomb.delayTime, 30);
            }
        }
    }

    @Override
    public void update(Scene scene) {
    }

    public void render(GraphicsContext gc) {
        if (Bomb.delayTime > 150 && Bomb.delayTime <= 180 && isDes) {
            chooseSprite();
            Image new_img = sprite_.getFxImage();
            gc.drawImage(new_img, x, y);
        } else gc.drawImage(img, x, y);
    }
}
