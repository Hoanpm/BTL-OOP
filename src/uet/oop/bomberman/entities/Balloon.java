package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
        sprite_ = Sprite.balloom_left1;

    }
    protected void chooseSprite() {
        if (!canmove(this)) {
            if (direction_ == 3) {
                direction_ = 4;
                check3 = false;
                check4 = true;
            }
            else if (direction_ == 4) {
                direction_ = 3;
                check4 = false;
                check3 = true;
            }
        }
        switch(direction_) {
            case 4:
                sprite_ = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, AnimatedEntity.animate_, 60);
                break;
            case 3:
                sprite_ = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, AnimatedEntity.animate_, 60);
                break;
        }
    }
//    @Override
//    public void render(GraphicsContext gc) {
//        calculateMove();
//        chooseSprite();
//        Image new_img = sprite_.getFxImage();
//        gc.drawImage(new_img, x, y);
//    }
    public void update(Scene scene) {};
}

