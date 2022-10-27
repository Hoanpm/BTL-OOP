package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

public abstract class Buff extends Entity{
    private boolean isRevealed = false;
    private Brick brickCover;
    public Buff(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public void setBrickCover(Brick brickCover) {
        this.brickCover = brickCover;
    }

    public Brick getBrickCover() {
        return brickCover;
    }
}
