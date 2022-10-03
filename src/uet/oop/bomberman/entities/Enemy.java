package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {

    public Enemy(int xUnit, int yUnit, Sprite sprite_) {
        super(xUnit, yUnit, sprite_);
    }
    protected int direction_ = -1;
}
