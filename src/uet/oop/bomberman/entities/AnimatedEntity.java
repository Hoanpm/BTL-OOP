package uet.oop.bomberman.entities;

import javafx.scene.Scene;

public abstract class AnimatedEntity {
        public static int animate_ = 0;
        public static final int MAX_ANIMATE = 6000;

        public static void animate() {
            if(animate_ < MAX_ANIMATE) animate_++; else animate_ = 0;
        }
}

