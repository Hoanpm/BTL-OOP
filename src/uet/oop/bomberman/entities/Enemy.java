package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Enemy extends Entity {

    public Enemy(int xUnit, int yUnit, Sprite sprite_) {
        super(xUnit, yUnit, sprite_);
    }
    public boolean check = false ,check1 = false, check2 = false, check3 = false, check4 = false;
    protected int direction_ = 3;
    public boolean checkdie = false;
    public int delaydie = 0;

    public void caculateBalloon() {
        if (x % 32 == 0 && y % 32 == 0) {
            check = false; check1 = false; check2 = false; check3 = false; check4 = false;
            direction_ = ThreadLocalRandom.current().nextInt(1, 5);
            while (!canmove(direction_)) {
                direction_ = ThreadLocalRandom.current().nextInt(1, 5);
            }
            switch (direction_) {
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
        }
        if (check1) y -= 1;
        if (check2) y += 1;
        if (check3) x -= 1;
        if (check4) x += 1;
    }

    public void calculateMove() {
        if (x % 32 == 0 && y % 32 == 0) {
            check = false; check1 = false; check2 = false; check3 = false; check4 = false;
            switch (direction_) {
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
        }
        if (check1) y -= 1;
        if (check2) y += 1;
        if (check3) x -= 1;
        if (check4) x += 1;
    }

    public boolean canmove( int direction_) {
        char[][] mapgame = BombermanGame.map_;
        int u = x/32;
        int v = y/32;
        switch (direction_) {
            case 1:
                v --;
                break;
            case 2:
                v ++;
                break;
            case 3:
                u --;
                break;
            case 4:
                u ++;
                break;
        }
        if (u > BombermanGame.WIDTH || u < 1) return false;
        if (v > BombermanGame.HEIGHT || v < 1) return false;
        return mapgame[v][u] != '*' && mapgame[v][u] != '#' && mapgame[v][u] != 'x' && mapgame[v][u] != 'f' && mapgame[v][u] != 'o';
    }

    public void checkbomberdie(Bomber bomber) {
        if (!checkdie) {
            if (checkCollision(bomber))
                bomber.checkdie = true;
        }
    }

    public void checkDie() {
        if (Bomb.delayTime > 150)
            for (int i = 0; i < Bomb.flame_objs.size(); i++) {
                if (checkCollision(Bomb.flame_objs.get(i))) {
                    checkdie = true;
                }
            }
    }
        public void deleteEnemy() {
            if (checkdie)
                delaydie ++;
            if (delaydie == 60) {
                for (int i = 1; i <= BombermanGame.enemies.size(); i++) {
                    if (x == BombermanGame.enemies.get(i - 1).getX()
                            && y == BombermanGame.enemies.get(i - 1).getY()) {
                        BombermanGame.enemies.remove(i - 1);
                        i--;
                    }
                }
                delaydie = 0;
            }
        }
    }