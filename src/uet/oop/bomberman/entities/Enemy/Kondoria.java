package uet.oop.bomberman.entities.Enemy;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayDeque;
import java.util.Queue;

public class Kondoria extends Enemy {
    int[] moveX = {0, 0, 1, -1};
    int[] moveY = {1, -1, 0, 0};

    int CD_time = 0;

    public Kondoria(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void caculated(Entity bomber) {
        int[][] distance = new int[BombermanGame.HEIGHT][BombermanGame.WIDTH];
        char[][] mapgame = BombermanGame.map_;
        boolean[][] visit = new boolean[BombermanGame.HEIGHT][BombermanGame.WIDTH];
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        int sx = bomber.getX() / 32;
        int sy = bomber.getY() / 32 - 2;

        q.add(new Pair<>(sx, sy));
        visit[sy][sx] = true;
        while (!q.isEmpty()) {
            Pair<Integer, Integer> e = q.element();
            int x1 = e.getKey();
            int y1 = e.getValue();
            q.poll();

            for (int i = 0; i < 4; ++i) {
                int u = x1 + moveX[i];
                int v = y1 + moveY[i];

                if (u > BombermanGame.WIDTH || u < 1) continue;
                if (v > BombermanGame.HEIGHT || v < 1) continue;
                if (mapgame[v][u] == '*' || mapgame[v][u] == '#' || mapgame[v][u] == 'x' || mapgame[v][u] == 'f' || mapgame[v][u] == 'o')
                    continue;

                if (!visit[v][u]) {
                    distance[v][u] = distance[y1][x1] + 1;
                    visit[v][u] = true;
                    if (distance[v][u] == 3) {
                        x = u * 32;
                        y = (v + 2) * 32;
                        return;
                    }
                    q.add(new Pair<>(u, v));
                }
            }
        }
    }

    protected void chooseSprite() {
        switch(direction_) {
            case 4:
            case 1:
                sprite_ = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, AnimatedEntity.animate_, 60);
                break;
            case 2:
            case 3:
                sprite_ = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, AnimatedEntity.animate_, 60);
                break;
        }
        if (checkdie) {
            sprite_ = Sprite.movingSprite(Sprite.kondoria_dead, Sprite.mob_dead1, Sprite.mob_dead3,AnimatedEntity.animate_, 60);
        }
    }

    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }

    public void update(Scene scene) {
        CD_time++;
        checkDie();
        if (x % 32 == 0 && y % 32 == 0)
            speed = 1;
        if (CD_time == 32*30) {
            CD_time = 0;
            caculated(BombermanGame.bomber);
        }

        checkbomberdie(BombermanGame.bomber);
        if (!checkdie)
                caculateRandom();
        else deleteEnemy();
    }

}
