package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

public abstract class Buff extends Entity{
    private boolean isRevealed = false;
    private int indexBrick = -1;
    public Buff(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getIndexBrick() {
        return indexBrick;
    }

    public void setIndexBrick(int indexBrick) {
        this.indexBrick = indexBrick;
    }
}
