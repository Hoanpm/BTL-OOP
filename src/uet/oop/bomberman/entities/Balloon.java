package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);

    }


    protected void chooseSprite() {
        switch(direction_) {
            case 1:
            case 4:
                sprite_ = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, AnimatedEntity.animate_, 60);
                break;
            case 2:
            case 3:
                sprite_ = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, AnimatedEntity.animate_, 60);
                break;
        }
        if (checkdie)
            sprite_ = Sprite.balloom_dead;
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }
    public void update(Scene scene) {
        checkDie();
        if (!checkdie) {
            caculateBalloon();
            checkbomberdie(BombermanGame.bomber);
        }
        else deleteEnemy();
    };
}