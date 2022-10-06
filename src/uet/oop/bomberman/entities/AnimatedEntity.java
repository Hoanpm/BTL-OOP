package uet.oop.bomberman.entities;

public abstract class AnimatedEntity {
    public static int animate_ = 0;
    private static final int max_ = 100;

    public static void animate() {
        if (animate_ < max_)
            animate_++;
        else animate_ = 0;
    }
}
