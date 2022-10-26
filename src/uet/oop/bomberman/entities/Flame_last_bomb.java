package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Sprite;

public class Flame_last_bomb extends Flame_obj {
    private int direction = 0;

    public Flame_last_bomb(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void chooseSprite() {
        if (Bomb.delayTime > 150 && Bomb.delayTime <= 180) {
            switch (direction) {
                case 0 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                              Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2,
                              Bomb.delayTime, 30);
                    break;
                case 1 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                            Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2,
                            Bomb.delayTime, 30);
                    break;
                case 2 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                            Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2,
                            Bomb.delayTime, 30);
                    break;
                case 3 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                            Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2,
                            Bomb.delayTime, 30);
                    break;
            }
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void update(Scene scene) {
        checkBomberDie(Game.bomber);
    }

    public void render(GraphicsContext gc) {
        if (Bomb.delayTime > 150 && Bomb.delayTime <= 180) {
            chooseSprite();
            Image new_img = sprite_.getFxImage();
            gc.drawImage(new_img, x, y);
        }
    }
}
