package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {

    public Enemy(int xUnit, int yUnit, Sprite sprite_) {
        super(xUnit, yUnit, sprite_);
    }

    protected int direction_ = 1;

    public void calculateMove() {

            if (direction_ == -1) x--;
            if (direction_ == 1) x++;

    }

    public boolean canmove(Enemy enemy) {
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                if (checkCollision(BombermanGame.stillObjects.get(i))) {
                    return false;
                }
            }
            if (i < BombermanGame.entities.size()) {
                if (BombermanGame.entities.get(i) instanceof Brick) {
                    if (checkCollision(BombermanGame.entities.get(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}


