package uet.oop.bomberman.graphics;

import uet.oop.bomberman.GameDisplay.Game;

public class TimeCount {
    public int time = 300;
    public int count = 0;

    public void update() {
        if (count > 0 && count%60 == 0) {
            time--;
            Game.time_.setText("Time: " + time);
        }
        count++;
    }
}