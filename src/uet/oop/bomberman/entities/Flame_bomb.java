package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Flame_bomb extends Flame_obj {
    private int direction = 0;
    public Flame_bomb(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void chooseSprite() {
        if (Bomb.delayTime > 150 && Bomb.delayTime <= 180)  {
            switch (direction) {
                case 0 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_horizontal,
                              Sprite.explosion_horizontal1, Sprite.explosion_horizontal2,
                              Bomb.delayTime, 30);
                    break;
                case 1 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_vertical,
                              Sprite.explosion_vertical1, Sprite.explosion_vertical2,
                              Bomb.delayTime, 30);
                    break;
            }

        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void update(Scene scene) {}

    public void render(GraphicsContext gc) {
        if (Bomb.delayTime > 150 && Bomb.delayTime <= 180) {
            chooseSprite();
            Image new_img = sprite_.getFxImage();
            gc.drawImage(new_img, x, y);
        }
    }
}
