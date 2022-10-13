package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {

    public int delaychangemove = 0;
    public Enemy(int xUnit, int yUnit, Sprite sprite_) {
        super(xUnit, yUnit, sprite_);
    }

    public boolean check = false ,check1 = false, check2 = false, check3 = false, check4 = false;
    protected int direction_ = 3;

    public void calculateMove() {
        if (delaychangemove == 32) {
            check = false; check1 = false; check2 = false; check3 = false; check4 = false;
            switch (direction_) {
                case 0:
                    check = true;
                    break;
                case 1:
                    check1 = true;
                    break;
                case 2:
                    check2 = true;
                    break;
                case 3:
                    check3 = true;
                    break;
                case 4:
                    check4 = true;
                    break;
            }
            delaychangemove = 0;
        }
        delaychangemove ++;
        if (check) {
            y += 0;
        }
        if (check1) y -= 1;
        if (check2) y += 1;
        if (check3) x -= 1;
        if (check4) x += 1;
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


