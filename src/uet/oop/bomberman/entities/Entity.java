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
        int left_a = this.x;
        int right_a = this.x + this.sprite_.get_realWidth() * 2;
        int top_a = this.y;
        int bottom_a = this.y + this.sprite_.get_realHeight() * 2;

        int left_b = other.getX();
        int right_b = other.getX() + other.getSprite_().get_realWidth() * 2;
        int top_b = other.getY();
        int bottom_b = other.getY() + other.getSprite_().get_realHeight() * 2;

        if (left_a > left_b && left_a < right_b)
        {
            if (top_a > top_b && top_a < bottom_b)
            {
                return true;
            }
        }

        if (left_a > left_b && left_a < right_b)
        {
            if (bottom_a > top_b && bottom_a < bottom_b)
            {
                return true;
            }
        }

        if (right_a > left_b && right_a < right_b)
        {
            if (top_a > top_b && top_a < bottom_b)
            {
                return true;
            }
        }

        if (right_a > left_b && right_a < right_b)
        {
            if (bottom_a > top_b && bottom_a < bottom_b)
            {
                return true;
            }
        }

        if (top_b == top_a || bottom_b == bottom_a) {
            if (right_b > left_a && right_b < right_a) {
                return true;
            }
        }

        if (top_b == top_a || bottom_b == bottom_a) {
            if (right_a > left_b && left_b > left_a) {
                return true;
            }
        }

        return top_a == top_b && right_a == right_b && bottom_a == bottom_b;
    }
}
