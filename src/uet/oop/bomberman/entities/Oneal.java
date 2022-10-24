package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayDeque;
import java.util.Queue;

public class Oneal extends Enemy {

    int[] moveX = {0, 0, 1, -1};
    int[] moveY = {1, -1, 0, 0};

    public Oneal(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void caculated(Entity bomber) {
        int[][] distance = new int[BombermanGame.HEIGHT][BombermanGame.WIDTH];
        char[][] mapgame = BombermanGame.map_;
        boolean[][] visit = new boolean[BombermanGame.HEIGHT][BombermanGame.WIDTH];
        Queue <Pair <Integer, Integer> > q = new ArrayDeque<>();
        int sx = bomber.x / 32 ;
        int sy = bomber.y / 32 - 2;

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
            if ( u > 0 && v > 0 && u < BombermanGame.WIDTH && v < BombermanGame.HEIGHT) {
                if (mapgame[v][u] == '*' || mapgame[v][u] == '#' || mapgame[v][u] == 'x' || mapgame[v][u] == 'f' || mapgame[v][u] == 'o') continue;
                if (distance[v][u] == distance[y/32 - 2][x/32] - 1) {
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
            //direction_ = 0;
            check = x / 32 != sx || y / 32 - 2 != sy;
        } else check = false;
        System.out.println(check);
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
        if (checkdie)
            sprite_ = (Sprite.oneal_dead);
    }
    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }

    public void update(Scene scene) {
        caculated(BombermanGame.bomber);
        checkbomberdie(BombermanGame.bomber);
        checkDie();
        if (!checkdie)
            if (check)
                caculateBalloon();
            else
                calculateMove();
        else deleteEnemy();
    }
}
