package uet.oop.bomberman.entities.Bomb;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.GameDisplay.Game;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame_bomb extends Entity {
    private int delayTime = 0;
    private int direction = 0;
    public Flame_bomb(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void chooseSprite() {
        if (delayTime > 150 && delayTime <= 180)  {
            switch (direction) {
                case 0 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_horizontal,
                              Sprite.explosion_horizontal1, Sprite.explosion_horizontal2,
                              delayTime, 30);
                    break;
                case 1 :
                    sprite_ = Sprite.movingSprite(Sprite.explosion_vertical,
                              Sprite.explosion_vertical1, Sprite.explosion_vertical2,
                              delayTime, 30);
                    break;
            }

        }
    }

    public void checkBomberDie(Bomber bomber) {
        if (delayTime > 150) {
            if (checkCollision(bomber)) {
                bomber.checkdie = true;
            }
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void update(Scene scene) {
        //System.out.println(delayTime);
        if (delayTime >= 150 && delayTime <= 180) delayTime++;
        checkBomberDie(Game.bomber);
    }

    public void render(GraphicsContext gc) {
        if (delayTime > 150 && delayTime <= 180) {
            chooseSprite();
            Image new_img = sprite_.getFxImage();
            gc.drawImage(new_img, x, y);
        }
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public int getDelayTime() {
        return delayTime;
    }
}
