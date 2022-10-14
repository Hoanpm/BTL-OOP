package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public abstract class Flame_obj extends Entity {
    public static List<Flame_bomb> flame_bombs = new ArrayList<>();
    public static List<Flame_last_bomb> flame_last_bombs = new ArrayList<>();

    public Flame_obj(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public static void checkFlame(Bomb bomb) {
        char[][] c = BombermanGame.map_;
        int y = bomb.getY() / 32;
        int x = bomb.getX() / 32;

        if (bomb.isBuff()) {
            if (c[y][x + 1] == ' ' || c[y][x + 1] == '1' || c[y][x + 1] == '2') {
                if (c[y][x + 2] != ' ' && c[y][x + 2] != '1' && c[y][x + 2] != '2') {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX() / 32 + 1, bomb.getY() / 32, Sprite.explosion_horizontal);
                    flame_bomb_hor.setDirection(0);
                    flame_bombs.add(flame_bomb_hor);
                } else {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX() / 32 + 1, bomb.getY() / 32, Sprite.explosion_horizontal);
                    Flame_last_bomb flame_last_bomb_right = new Flame_last_bomb(bomb.getX() / 32 + 2, bomb.getY() / 32, Sprite.explosion_horizontal_right_last);
                    flame_bomb_hor.setDirection(0);
                    flame_last_bomb_right.setDirection(0);
                    flame_bombs.add(flame_bomb_hor);
                    flame_last_bombs.add(flame_last_bomb_right);
                }
            }

            if (c[y][x - 1] == ' ' || c[y][x - 1] == 'p' || c[y][x - 1] == '1' || c[y][x - 1] == '2') {
                if (c[y][x - 2] != 'p' && c[y][x - 2] != ' ' && c[y][x - 2] != '1' && c[y][x - 2] != '2') {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX() / 32 - 1, bomb.getY() / 32, Sprite.explosion_horizontal);
                    flame_bomb_hor.setDirection(0);
                    flame_bombs.add(flame_bomb_hor);
                } else {
                    Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX() / 32 - 1, bomb.getY() / 32, Sprite.explosion_horizontal);
                    Flame_last_bomb flame_last_bomb_left = new Flame_last_bomb(bomb.getX() / 32 - 2, bomb.getY() / 32, Sprite.explosion_horizontal_left_last);
                    flame_bomb_hor.setDirection(0);
                    flame_last_bomb_left.setDirection(1);
                    flame_bombs.add(flame_bomb_hor);
                    flame_last_bombs.add(flame_last_bomb_left);
                }
            }

            if (c[y - 1][x] == ' ' || c[y - 1][x] == 'p' || c[y - 1][x] == '1' || c[y - 1][x] == '2') {
                if (c[y - 2][x] != 'p' && c[y - 2][x] != ' ' && c[y - 2][x] != '1' && c[y - 2][x] != '2') {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX() / 32, bomb.getY() / 32 - 1, Sprite.explosion_vertical);
                    flame_bomb_ver.setDirection(1);
                    flame_bombs.add(flame_bomb_ver);
                } else {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX() / 32, bomb.getY() / 32 - 1, Sprite.explosion_vertical);
                    Flame_last_bomb flame_last_bomb_top = new Flame_last_bomb(bomb.getX() / 32, bomb.getY() / 32 - 2, Sprite.explosion_vertical_top_last);
                    flame_bomb_ver.setDirection(1);
                    flame_last_bomb_top.setDirection(2);
                    flame_bombs.add(flame_bomb_ver);
                    flame_last_bombs.add(flame_last_bomb_top);
                }
            }

            if (c[y + 1][x] == ' ' || c[y + 1][x] == '1' || c[y + 1][x] == '2') {
                if (c[y + 2][x] != ' ' && c[y + 2][x] != '1' && c[y + 2][x] != '2') {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX() / 32, bomb.getY() / 32 + 1, Sprite.explosion_vertical);
                    flame_bomb_ver.setDirection(1);
                    flame_bombs.add(flame_bomb_ver);
                } else {
                    Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX() / 32, bomb.getY() / 32 + 1, Sprite.explosion_vertical);
                    Flame_last_bomb flame_last_bomb_down = new Flame_last_bomb(bomb.getX() / 32, bomb.getY() / 32 + 2, Sprite.explosion_vertical_down_last);
                    flame_bomb_ver.setDirection(1);
                    flame_last_bomb_down.setDirection(3);
                    flame_bombs.add(flame_bomb_ver);
                    flame_last_bombs.add(flame_last_bomb_down);
                }
            }
        } else {
            if (c[y][x + 1] == ' ' || c[y][x + 1] == '1' || c[y][x + 1] == '2') {
                Flame_last_bomb flame_last_bomb_right = new Flame_last_bomb(bomb.getX() / 32 + 1, bomb.getY() / 32, Sprite.explosion_horizontal_right_last);
                flame_last_bomb_right.setDirection(0);
                flame_last_bombs.add(flame_last_bomb_right);
            }
            if (c[y][x - 1] == ' ' || c[y][x - 1] == 'p' || c[y][x - 1] == '1' || c[y][x - 1] == '2') {
                Flame_last_bomb flame_last_bomb_left = new Flame_last_bomb(bomb.getX() / 32 - 1, bomb.getY() / 32, Sprite.explosion_horizontal_left_last);
                flame_last_bomb_left.setDirection(1);
                flame_last_bombs.add(flame_last_bomb_left);
            }
            if (c[y - 1][x] == ' ' || c[y - 1][x] == 'p' || c[y - 1][x] == '1' || c[y - 1][x] == '2') {
                Flame_last_bomb flame_last_bomb_top = new Flame_last_bomb(bomb.getX() / 32, bomb.getY() / 32 - 1, Sprite.explosion_vertical_top_last);
                flame_last_bomb_top.setDirection(2);
                flame_last_bombs.add(flame_last_bomb_top);
            }
            if (c[y + 1][x] == ' ' || c[y + 1][x] == '1' || c[y + 1][x] == '2') {
                Flame_last_bomb flame_last_bomb_down = new Flame_last_bomb(bomb.getX() / 32, bomb.getY() / 32 + 1, Sprite.explosion_vertical_down_last);
                flame_last_bomb_down.setDirection(3);
                flame_last_bombs.add(flame_last_bomb_down);
            }
        }
    }
}
