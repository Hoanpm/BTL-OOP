package uet.oop.bomberman.entities.Bomb;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.GameDisplay.Game;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    public int delayTime = 0;
    private boolean isBuff = false;
    private boolean isStepOut = false;
    public List<Flame_bomb> flame_bombs = new ArrayList<>();
    public List<Flame_last_bomb> flame_last_bombs = new ArrayList<>();

    public Bomb(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void checkDelay() {
        if (delayTime < 180) delayTime++;
        else {
            flame_bombs.clear();
            flame_last_bombs.clear();

            for (int i=0; i<Game.bricks.size(); i++) {
                if (Game.bricks.get(i).isDes() && this.equals(Game.bricks.get(i).getBombDes())) {
                    int x = Game.bricks.get(i).getX();
                    int y = Game.bricks.get(i).getY();
                    Game.map_[y/32 - 2][x/32] = ' ';
                    for (int j = 0; j< Game.buffs.size(); j++) {
                        if (Game.bricks.get(i).equals(Game.buffs.get(j).getBrickCover())) {
                            Game.buffs.get(j).setRevealed(true);
                        }
                    }
                    Game.bricks.remove(i);
                    i--;
                }
            }
            Bomber.bombList.remove(this);
        }
    }

    public void chooseSprite() {
        if (delayTime <= 150)
            sprite_ = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, delayTime, 50);
        if (delayTime > 150 && delayTime <= 180) {
            Game.map_[getY()/32 - 2][getX()/32] = ' ';
            sprite_ = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, delayTime, 30);
            if (delayTime == 151)
                Sound.playbomExplode();
        }
    }

    public void update(Scene scene_) {
        checkDieBomber(Game.bomber);
        checkDelay();
        if (delayTime == 150) setFlame();
    }

    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }

    public boolean isBuff() {
        return isBuff;
    }

    public void setBuff(boolean buff) {
        isBuff = buff;
    }

    public void setFlame() {
        char[][] c = Game.map_;
        int y = this.getY() / 32;
        int x = this.getX() / 32;

        if (this.isBuff()) {
            if (c[y - 2][x + 1] == ' ' || c[y - 2][x + 1] == '1' || c[y - 2][x + 1] == '2' || c[y - 2][x + 1] == '3' || c[y - 2][x + 1] == '4' || c[y - 2][x + 1] == '5') {
                if (c[y - 2][x + 2] != ' ' && c[y - 2][x + 2] != '1' && c[y - 2][x + 2] != '2' && c[y - 2][x + 2] != '3' && c[y - 2][x + 2] != '4' && c[y - 2][x + 2] != '5') {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(x + 1, y, Sprite.explosion_horizontal);
                    flame_bomb_hor.setDirection(0);
                    flame_bomb_hor.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_hor);
                } else {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(x + 1, y, Sprite.explosion_horizontal);
                    Flame_last_bomb flame_last_bomb_right = new Flame_last_bomb(x + 2, y, Sprite.explosion_horizontal_right_last);
                    flame_bomb_hor.setDirection(0);
                    flame_last_bomb_right.setDirection(0);
                    flame_bomb_hor.setDelayTime(delayTime);
                    flame_last_bomb_right.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_hor);
                    flame_last_bombs.add(flame_last_bomb_right);
                }
            }

            if (c[y - 2][x - 1] == ' ' || c[y - 2][x - 1] == 'p' || c[y - 2][x - 1] == '1' || c[y - 2][x - 1] == '2' || c[y - 2][x - 1] == '3' || c[y - 2][x - 1] == '4' || c[y - 2][x - 1] == '5' ) {
                if (c[y - 2][x - 2] != 'p' && c[y - 2][x - 2] != ' ' && c[y - 2][x - 2] != '1' && c[y - 2][x - 2] != '2' && c[y - 2][x - 2] != '3' && c[y - 2][x - 2] != '4' && c[y - 2][x - 2] != '5') {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(x - 1, y, Sprite.explosion_horizontal);
                    flame_bomb_hor.setDirection(0);
                    flame_bomb_hor.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_hor);
                } else {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(x - 1, y, Sprite.explosion_horizontal);
                    Flame_last_bomb flame_last_bomb_left = new Flame_last_bomb(x - 2, y, Sprite.explosion_horizontal_left_last);
                    flame_bomb_hor.setDirection(0);
                    flame_last_bomb_left.setDirection(1);
                    flame_bomb_hor.setDelayTime(delayTime);
                    flame_last_bomb_left.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_hor);
                    flame_last_bombs.add(flame_last_bomb_left);
                }
            }

            if (c[y - 3][x] == ' ' || c[y - 3][x] == 'p' || c[y - 3][x] == '1' || c[y - 3][x] == '2' || c[y - 3][x] == '3' || c[y - 3][x] == '4' || c[y - 3][x] == '5') {
                if (c[y - 4][x] != 'p' && c[y - 4][x] != ' ' && c[y - 4][x] != '1' && c[y - 4][x] != '2' && c[y - 4][x] != '3' && c[y - 4][x] != '4' && c[y - 4][x] != '5') {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(x, y - 1, Sprite.explosion_vertical);
                    flame_bomb_ver.setDirection(1);
                    flame_bomb_ver.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_ver);
                } else {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(x, y - 1, Sprite.explosion_vertical);
                    Flame_last_bomb flame_last_bomb_top = new Flame_last_bomb(x, y - 2, Sprite.explosion_vertical_top_last);
                    flame_bomb_ver.setDirection(1);
                    flame_last_bomb_top.setDirection(2);
                    flame_bomb_ver.setDelayTime(delayTime);
                    flame_last_bomb_top.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_ver);
                    flame_last_bombs.add(flame_last_bomb_top);
                }
            }

            if (c[y - 1][x] == ' ' || c[y - 1][x] == '1' || c[y - 1][x] == '2' || c[y - 1][x] == '3' || c[y - 1][x] == '4' ||  c[y - 1][x] == '5') {
                if (c[y][x] != ' ' && c[y][x] != '1' && c[y][x] != '2' && c[y][x] != '3' && c[y][x] != '4' &&  c[y][x] != '5') {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(x, y + 1, Sprite.explosion_vertical);
                    flame_bomb_ver.setDirection(1);
                    flame_bomb_ver.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_ver);
                } else {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(x, y + 1, Sprite.explosion_vertical);
                    Flame_last_bomb flame_last_bomb_down = new Flame_last_bomb(x, y + 2, Sprite.explosion_vertical_down_last);
                    flame_bomb_ver.setDirection(1);
                    flame_last_bomb_down.setDirection(3);
                    flame_bomb_ver.setDelayTime(delayTime);
                    flame_last_bomb_down.setDelayTime(delayTime);
                    flame_bombs.add(flame_bomb_ver);
                    flame_last_bombs.add(flame_last_bomb_down);
                }
            }
        } else {
            if (c[y - 2][x + 1] == ' ' || c[y - 2][x + 1] == '1' || c[y - 2][x + 1] == '2' || c[y - 2][x + 1] == '3' || c[y - 2][x + 1] == '4' || c[y - 2][x + 1] == '5') {
                Flame_last_bomb flame_last_bomb_right = new Flame_last_bomb(x + 1, y, Sprite.explosion_horizontal_right_last);
                flame_last_bomb_right.setDirection(0);
                flame_last_bomb_right.setDelayTime(delayTime);
                flame_last_bombs.add(flame_last_bomb_right);
            }
            if (c[y - 2][x - 1] == ' ' || c[y - 2][x - 1] == 'p' || c[y - 2][x - 1] == '1' || c[y - 2][x - 1] == '2' || c[y - 2][x - 1] == '3' || c[y - 2][x - 1] == '4' || c[y - 2][x - 1] == '5') {
                Flame_last_bomb flame_last_bomb_left = new Flame_last_bomb(x - 1, y, Sprite.explosion_horizontal_left_last);
                flame_last_bomb_left.setDirection(1);
                flame_last_bomb_left.setDelayTime(delayTime);
                flame_last_bombs.add(flame_last_bomb_left);
            }
            if (c[y - 3][x] == ' ' || c[y - 3][x] == 'p' || c[y - 3][x] == '1' || c[y - 3][x] == '2' || c[y - 3][x] == '3' || c[y - 3][x] == '4' || c[y - 3][x] == '5') {
                Flame_last_bomb flame_last_bomb_top = new Flame_last_bomb(x, y - 1, Sprite.explosion_vertical_top_last);
                flame_last_bomb_top.setDirection(2);
                flame_last_bomb_top.setDelayTime(delayTime);
                flame_last_bombs.add(flame_last_bomb_top);
            }
            if (c[y - 1][x] == ' ' || c[y - 1][x] == '1' || c[y - 1][x] == '2' || c[y - 1][x] == '3' || c[y - 1][x] == '4' || c[y - 1][x] == '5') {
                Flame_last_bomb flame_last_bomb_down = new Flame_last_bomb(x, y + 1, Sprite.explosion_vertical_down_last);
                flame_last_bomb_down.setDirection(3);
                flame_last_bomb_down.setDelayTime(delayTime);
                flame_last_bombs.add(flame_last_bomb_down);
            }
        }
    }

    public void checkDieBomber(Bomber bomber) {
        if (!isStepOut && delayTime > 151) {
            bomber.checkdie = true;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Bomb) {
            Bomb other = (Bomb) obj;
            return x == other.getX() && y == other.getY();
        }
        return false;
    }

    public void setStepOut(boolean stepOut) {
        isStepOut = stepOut;
    }

    public boolean isStepOut() {
        return isStepOut;
    }
}