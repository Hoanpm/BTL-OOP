package uet.oop.bomberman.entities.Enemy;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);

    }

    protected void chooseSprite() {
        switch(direction_) {
            case 0:
                sprite_ = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, AnimatedEntity.animate_, 60);
                break;
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
            sprite_ = Sprite.movingSprite(Sprite.balloom_dead, Sprite.mob_dead1, Sprite.mob_dead3, this.delaydie, 60);
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }
    public void update(Scene scene) {
        speed = 1;
        checkDie();
        if (!checkdie) {
            randomDirection();
            calculateMove();
            checkbomberdie(Game.bomber);
        }
        else deleteEnemy();
    };
}