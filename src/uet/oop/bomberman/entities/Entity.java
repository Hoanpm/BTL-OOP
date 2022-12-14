package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected Image img;
    protected Sprite sprite_;
    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity() {}

    public Entity(int xUnit, int yUnit, Sprite sprite_) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.sprite_ = sprite_;
        this.img = sprite_.getFxImage();
    }

    public void setX(int n) {
        x = n;
    }

    public void setY(int n) {
        y = n;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Sprite getSprite_() {
        return sprite_;
    }

    public void setSprite_(Sprite sprite_) {
        this.sprite_ = sprite_;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update(Scene scene);

    public boolean checkCollision(Entity other) {
        int left_b = this.x;
        int right_b = this.x + this.sprite_.get_realWidth() * 2;
        int top_b = this.y;
        int bottom_b = this.y + this.sprite_.get_realHeight() * 2;

        int left_a = other.getX();
        int right_a = other.getX() + other.getSprite_().get_realWidth() * 2;
        int top_a = other.getY();
        int bottom_a = other.getY() + other.getSprite_().get_realHeight() * 2;

        if (left_b < left_a && right_b > left_a) {
            if (top_b < top_a && bottom_b > top_a) {
                return true;
            }
            if (top_b < bottom_a && bottom_b > bottom_a) {
                return true;
            }
        }

        if (left_b < right_a && right_b > right_a) {
            if (top_b < top_a && bottom_b > top_a) {
                return true;
            }
            if (top_b < bottom_a && bottom_b > bottom_a) {
                return true;
            }
        }

        if (top_b >= top_a && bottom_b <= bottom_a) {
            if (left_b < left_a && right_b > left_a) {
                return true;
            }
            if (left_b < right_a && right_b > right_a) {
                return true;
            }
        }

        if (left_b >= left_a && right_b <= right_a) {
            if (top_b < top_a && bottom_b > top_a) {
                return true;
            }
            if (top_b < bottom_a && bottom_b > bottom_a) {
                return true;
            }
        }

        if (left_b >= left_a && right_b <= right_a && top_b >= top_a && bottom_b <= bottom_a) {
            return true;
        }

        return false;
    }
}
