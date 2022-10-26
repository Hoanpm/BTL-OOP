package uet.oop.bomberman.entities.Enemy;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Oneal extends Enemy {

    int[] moveX = {0, 0, 1, -1};
    int[] moveY = {1, -1, 0, 0};

    public Oneal(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void caculated(Entity bomber) {
        int[][] distance = new int[Game.HEIGHT][Game.WIDTH];
        char[][] mapgame = Game.map_;
        boolean[][] visit = new boolean[Game.HEIGHT][Game.WIDTH];
        Queue <Pair <Integer, Integer> > q = new ArrayDeque<>();
        int sx = bomber.getX() / 32 ;
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

                if (u > Game.WIDTH || u < 1) continue;
                if (v > Game.HEIGHT || v < 1) continue;
                if (mapgame[v][u] == '*' || mapgame[v][u] == '#' || mapgame[v][u] == 'x' || mapgame[v][u] == 'f' || mapgame[v][u] == 'o') continue;

                if (!visit[v][u]) {
                    distance[v][u] = distance[y1][x1] + 1;
                    visit[v][u] = true;
                    q.add(new Pair<>(u, v));
                }
            }
        }
        for (int i = 0; i < 4; ++i) {
            int u = x/32 + moveX[i];
            int v = y/32 + moveY[i] - 2;
            if ( u > 0 && v > 0 && u < Game.WIDTH && v < Game.HEIGHT) {
                if (mapgame[v][u] == '*' || mapgame[v][u] == '#' || mapgame[v][u] == 'x' || mapgame[v][u] == 'f' || mapgame[v][u] == 'o') continue;
                if (distance[v][u] == distance[y/32 - 2][x/32] - 1 ) {
                    switch (i) {
                        case 0:
                            direction_ = 2;
                            break;
                        case 1:
                            direction_ = 1;
                            break;
                        case 2:
                            direction_ = 4;
                            break;
                        case 3:
                            direction_ = 3;
                            break;
                    }
                }
            }
        }
        if (distance[y/32 - 2][x/32] == 0) {
            check = x / 32 != sx || y / 32 - 2 != sy;
        } else check = distance[y / 32 - 2][x / 32] > 10;
    }

    protected void chooseSprite() {
        switch(direction_) {
            case 4:
            case 1:
                sprite_ = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, AnimatedEntity.animate_, 60);
                break;
            case 2:
            case 3:
                sprite_ = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, AnimatedEntity.animate_, 60);
                break;
        }
        if (checkdie) {
            sprite_ = Sprite.movingSprite(Sprite.oneal_dead, Sprite.mob_dead1, Sprite.mob_dead3,AnimatedEntity.animate_, 60);
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }

    public void update(Scene scene) {
        if (x % 64 == 0 || y % 64 == 0)
            speed = ThreadLocalRandom.current().nextInt(1, 3);
        caculated(Game.bomber);
        checkbomberdie(Game.bomber);
        checkDie();
        if (!checkdie) {
            if (check)
                randomDirection();
            calculateMove();
        }
        else deleteEnemy();
    }
}
