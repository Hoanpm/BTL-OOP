package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Sound {
    public static String bomb_Explose = "res/sound/bom_explode.wav";

    public static String bg_sound = "res/sound/bg_sound.wav";

    public static String bom_set = "res/sound/bom_set.wav";

    public static String dead = "res/sound/dead.wav";

    public static String endgame = "res/sound/end_game.wav";

    public static String item_get = "res/sound/item_get.wav";

    public Sound() {}
    public static void play(String s) {
        Media media = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }

    public static void playbomExplose() {
        Sound.play(bomb_Explose);
    }

    public static void playbgSound() {
        Sound.play(bg_sound);
    }

    public static void playbomSet() {
        Sound.play(bom_set);
    }

    public static void playEndgame() {
        Sound.play(endgame);
    }

    public static void playDead() {
        Sound.play(dead);
    }

    public static void playitemGet() {
        Sound.play(item_get);
    }
}

