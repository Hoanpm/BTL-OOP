package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.GameDisplay.Game;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.concurrent.ThreadLocalRandom;
public abstract class Enemy extends Entity {
    public Enemy(int xUnit, int yUnit, Sprite sprite_) {
        super(xUnit, yUnit, sprite_);
    }
    public boolean check = false ,check1 = false, check2 = false, check3 = false, check4 = false;
    protected int direction_ = 3;
    public boolean checkdie = false;
    public int delaydie = 0, speed = 1;
    char[][] mapgame = Game.map_;

    public boolean havewayout() {
        for (int i = 1; i <= 4; i++) {
            if (canmove(i)) {
                return true;
            }
        }
        direction_ = 0;
        return false;
    }

    public void randomDirection() {
        if (x % 32 == 0 && y % 32 == 0) {
            direction_ = ThreadLocalRandom.current().nextInt(1, 5);
            while (!canmove(direction_) && havewayout()) {
                direction_ = ThreadLocalRandom.current().nextInt(1, 5);
            }
        }
    }

    public void calculateMove() {
        if (havewayout()) {
            if (x % 32 == 0 && y % 32 == 0) {
                check = false;
                check1 = false;
                check2 = false;
                check3 = false;
                check4 = false;
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
                if (check1) y -= speed;
                if (check2) y += speed;
                if (check3) x -= speed;
                if (check4) x += speed;
        }
    }

    public boolean canmove( int direction_) {
        int u = x/32;
        int v = y/32 - 2;
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
        if (u > Game.WIDTH || u < 1) return false;
        if (v > Game.HEIGHT || v < 1) return false;
        return mapgame[v][u] != '*' && mapgame[v][u] != '#' && mapgame[v][u] != 'x' && mapgame[v][u] != 'f' && mapgame[v][u] != 'o' && mapgame[v][u] != 's' && mapgame[v][u] != 'b';
    }

    public void checkbomberdie(Bomber bomber) {
        if (!checkdie) {
            if (checkCollision(bomber)) {
                bomber.checkdie = true;
            }
        }


    }

    public void checkDie() {
        for (int i=0; i<Bomber.bombList.size(); i++) {
            if (checkCollision(Bomber.bombList.get(i)) && Bomber.bombList.get(i).delayTime > 151 ) checkdie = true;
            for (int j=0; j<Bomber.bombList.get(i).flame_bombs.size(); j++) {
                if (checkCollision(Bomber.bombList.get(i).flame_bombs.get(j))) {
                    checkdie = true;
                }
            }
            for (int j=0; j<Bomber.bombList.get(i).flame_last_bombs.size(); j++) {
                if (checkCollision(Bomber.bombList.get(i).flame_last_bombs.get(j))) {
                    checkdie = true;
                }
            }
        }
    }
    public void deleteEnemy() {
        if (checkdie) {
            delaydie++;

        }
        if (delaydie == 1) {
            Sound.playDead();

        }
        if (delaydie == 59) {
            for (int i = 1; i <= Game.enemies.size(); i++) {
                if (x == Game.enemies.get(i - 1).getX()
                        && y == Game.enemies.get(i - 1).getY()) {
                    Game.enemies.remove(i - 1);
                    i--;
                }
            }
            delaydie = 0;
            Game.score_ += 200;
            Game.score.setText("Score: " + Game.score_);
            Sound.playPointup();
        }
    }
}