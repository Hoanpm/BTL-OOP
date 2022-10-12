package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private int dx, dy;
    private static final int velocity = 2;
    private boolean isMove_ = false;
    private static final String MOVE_UP = "UP",
                                MOVE_DOWN = "DOWN",
                                MOVE_RIGHT = "RIGHT",
                                MOVE_LEFT = "LEFT";

    private String direction = MOVE_RIGHT;

    public static List<Bomb> bombList = new ArrayList<>();
    public static List<Flame_bomb> flame_bombs = new ArrayList<>();
    public static List<Flame_last_bomb> flame_last_bombs = new ArrayList<>();

    protected boolean isSetBomb_ = false;

    public Bomber(int x, int y, Sprite sprite_) {
        super(x, y, sprite_);
    }

    public void setKey(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = -velocity;
                        break;
                    case DOWN:
                    case S:
                        dy = velocity;
                        break;
                    case LEFT:
                    case A:
                        dx = -velocity;
                        break;
                    case RIGHT:
                    case D:
                        dx = velocity;
                        break;
                    case SPACE:
                        if (Bomb.delayTime == 0)
                            isSetBomb_ = true;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = 0;
                        break;
                    case DOWN:
                    case S:
                        dy = 0;
                        break;
                    case LEFT:
                    case A:
                        dx = 0;
                        break;
                    case RIGHT:
                    case D:
                        dx = 0;
                        break;
                }
            }
        });
    }

    public void setDirection() {
        if (dx > 0) direction = MOVE_RIGHT;
        if (dx < 0) direction = MOVE_LEFT;
        if (dy > 0) direction = MOVE_DOWN;
        if (dy < 0) direction = MOVE_UP;
    }

    public void chooseSprite() {
        switch (direction) {
            case MOVE_RIGHT:
                this.sprite_ = Sprite.player_right;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, AnimatedEntity.animate_, 20);
                break;
            case MOVE_LEFT:
                this.sprite_ = Sprite.player_left;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, AnimatedEntity.animate_, 20);
                break;
            case MOVE_UP:
                this.sprite_ = Sprite.player_up;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, AnimatedEntity.animate_, 20);
                break;
            case MOVE_DOWN:
                this.sprite_ = Sprite.player_down;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, AnimatedEntity.animate_, 20);
                break;
            default:
                this.sprite_ = Sprite.player_right;
                if (isMove_)
                    this.sprite_ = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, AnimatedEntity.animate_, 20);
                break;
        }
    }

    public void move()  {
        setDirection();

        x += dx;
        y += dy;

        if (dx != 0 || dy != 0) isMove_ = true;
        else if (dx == 0 && dy == 0) isMove_ = false;

        for (int i=0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                if (checkCollision(BombermanGame.stillObjects.get(i))) {
                    x -= dx;
                    y -= dy;
                }
            }
            if (i < BombermanGame.entities.size()) {
                if (BombermanGame.entities.get(i) instanceof Brick) {
                    if (checkCollision(BombermanGame.entities.get(i))) {
                        x -= dx;
                        y -= dy;
                    }
                }
            }
        }
    }

    public void checkFlame(Bomb bomb) {
        char[][] c = BombermanGame.map_;
        int y = bomb.getY() / 32;
        int x = bomb.getX() / 32;
        if (c[y][x + 1] == ' ' || c[y][x + 1] == '1' || c[y][x + 1] == '2') {
            if (c[y][x + 2] != ' ' && c[y][x + 2] != '1' && c[y][x + 2] != '2') {
                Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX()/32 + 1, bomb.getY()/32, Sprite.explosion_horizontal);
                flame_bomb_hor.setDirection(0);
                flame_bombs.add(flame_bomb_hor);
            } else {
                Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX()/32 + 1, bomb.getY()/32, Sprite.explosion_horizontal);
                Flame_last_bomb flame_last_bomb_right = new Flame_last_bomb(bomb.getX()/32 + 2, bomb.getY()/32, Sprite.explosion_horizontal_right_last);
                flame_bomb_hor.setDirection(0);
                flame_last_bomb_right.setDirection(0);
                flame_bombs.add(flame_bomb_hor);
                flame_last_bombs.add(flame_last_bomb_right);
            }
        }

        if (c[y][x - 1] == ' ' || c[y][x - 1] == 'p' || c[y][x - 1] == '1' || c[y][x - 1] == '2') {
            if (c[y][x - 2] != 'p' && c[y][x - 2] != ' ' && c[y][x - 2] != '1' && c[y][x - 2] != '2') {
                Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX()/32 - 1, bomb.getY()/32, Sprite.explosion_horizontal);
                flame_bomb_hor.setDirection(0);
                flame_bombs.add(flame_bomb_hor);
            } else {
                Flame_bomb flame_bomb_hor = new Flame_bomb(bomb.getX()/32 - 1, bomb.getY()/32, Sprite.explosion_horizontal);
                Flame_last_bomb flame_last_bomb_left = new Flame_last_bomb(bomb.getX()/32 - 2, bomb.getY()/32, Sprite.explosion_horizontal_left_last);
                flame_bomb_hor.setDirection(0);
                flame_last_bomb_left.setDirection(1);
                flame_bombs.add(flame_bomb_hor);
                flame_last_bombs.add(flame_last_bomb_left);
            }
        }

        if (c[y - 1][x] == ' ' || c[y - 1][x] == 'p' || c[y - 1][x] == '1' || c[y - 1][x] == '2') {
            if (c[y - 2][x] != 'p' && c[y - 2][x] != ' ' && c[y - 2][x] != '1' && c[y - 2][x] != '2') {
                Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX()/32, bomb.getY()/32 - 1, Sprite.explosion_vertical);
                flame_bomb_ver.setDirection(1);
                flame_bombs.add(flame_bomb_ver);
            } else {
                Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX()/32, bomb.getY()/32 - 1, Sprite.explosion_vertical);
                Flame_last_bomb flame_last_bomb_top = new Flame_last_bomb(bomb.getX()/32, bomb.getY()/32 - 2, Sprite.explosion_vertical_top_last);
                flame_bomb_ver.setDirection(1);
                flame_last_bomb_top.setDirection(2);
                flame_bombs.add(flame_bomb_ver);
                flame_last_bombs.add(flame_last_bomb_top);
            }
        }

        if (c[y + 1][x] == ' ' || c[y + 1][x] == '1' || c[y + 1][x] == '2') {
            if (c[y + 2][x] != ' ' && c[y + 2][x] != '1' && c[y + 2][x] != '2') {
                Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX()/32, bomb.getY()/32 + 1, Sprite.explosion_vertical);
                flame_bomb_ver.setDirection(1);
                flame_bombs.add(flame_bomb_ver);
            } else {
                Flame_bomb flame_bomb_ver = new Flame_bomb(bomb.getX()/32, bomb.getY()/32 + 1, Sprite.explosion_vertical);
                Flame_last_bomb flame_last_bomb_down = new Flame_last_bomb(bomb.getX()/32, bomb.getY()/32 + 2, Sprite.explosion_vertical_down_last);
                flame_bomb_ver.setDirection(1);
                flame_last_bomb_down.setDirection(3);
                flame_bombs.add(flame_bomb_ver);
                flame_last_bombs.add(flame_last_bomb_down);
            }
        }
    }

    public void checkDes(Bomb bomb) {
        List<Entity> c_ = BombermanGame.entities;
        char[][] b = BombermanGame.map_;
        int x = bomb.getX();
        int y = bomb.getY();

        for (Entity entity : c_) {
            if (entity instanceof Brick) {
                int x_ = entity.getX();
                int y_ = entity.getY();
                if (y_ == y) {
                    if (x_ == x + 64 && b[y/32][x/32 + 1] == ' '
                        || x_ == x - 64 && b[y/32][x/32 - 1] == ' '
                        || x_ == x + 32 || x_ == x - 32) {
                        entity.isDes = true;
                    }
                } else if (x_ == x) {
                    if (y_ == y + 64 && b[y/32 + 1][x/32] == ' '
                            || y_ == y - 64 && b[y/32 - 1][x/32] == ' '
                            || y_ == y + 32 || y_ == y - 32) {
                        entity.isDes = true;
                    }
                }
            }
        }
    }

    public void setBomb() {
        if (isSetBomb_) {
            Bomb new_b = new Bomb(getX() / 32, getY() / 32, Sprite.bomb);
            bombList.add(new_b);
            checkFlame(new_b);
            checkDes(new_b);
            isSetBomb_ = false;
        }
    }

    @Override
    public void update(Scene scene) {
        setKey(scene);
        AnimatedEntity.animate();
        move();
        setBomb();
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        Image new_img = sprite_.getFxImage();
        gc.drawImage(new_img, x, y);
    }
}
