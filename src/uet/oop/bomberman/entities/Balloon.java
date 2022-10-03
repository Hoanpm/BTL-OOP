package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.Random;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
        sprite_ = Sprite.balloom_left1;

    }
    protected void chooseSprite() {
        if (!canmove(this)) direction_ = -direction_;
        switch(direction_) {
            case 1:
                sprite_ = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, AnimatedEntity.animate_, 60);
                break;
            case -1:
                sprite_ = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, AnimatedEntity.animate_, 60);
                break;
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        calculateMove();
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }
    public void update(Scene scene) {};
}

