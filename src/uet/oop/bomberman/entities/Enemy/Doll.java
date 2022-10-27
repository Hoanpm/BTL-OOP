package uet.oop.bomberman.entities.Enemy;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.GameDisplay.Game;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);

    }

    protected void chooseSprite() {
        switch(direction_) {
            case 0:
                sprite_ = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, AnimatedEntity.animate_, 60);
                break;
            case 1:
            case 4:
                sprite_ = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, AnimatedEntity.animate_, 60);
                break;
            case 2:
            case 3:
                sprite_ = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, AnimatedEntity.animate_, 60);
                break;
        }
        if (checkdie)
            sprite_ = Sprite.movingSprite(Sprite.doll_dead, Sprite.mob_dead1, Sprite.mob_dead3, this.delaydie, 60);
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }
    public void update(Scene scene) {
        speed = 2;
        checkDie();
        if (!checkdie) {
            randomDirection();
            calculateMove();
            checkbomberdie(Game.bomber);
        }
        else deleteEnemy();
    };
}
