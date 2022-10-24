package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Sound {
    public static String bomb_Explose = "res/sound/bom_explode.wav";

    public static String bg_sound = "res/sound/bg_sound.mp3";

    public static String bom_set = "res/sound/bom_set.wav";

    public static String dead = "res/sound/dead.wav";

    public static String endgame = "res/sound/end_game.wav";

    public static String item_get = "res/sound/item_get.wav";

    public Sound() {}
    public static void play(String s,int n) {
        Media media = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(n);
        mediaPlayer.play();
    }

    public static void playbomExplose() {
        Sound.play(bomb_Explose, 1);
    }

    public static void playbgSound() {
        Sound.play(bg_sound, 10);

    }

    public static void playbomSet() {
        Sound.play(bom_set, 1);
    }

    public static void playEndgame() {
        Sound.play(endgame,1);
    }

    public static void playDead() {
        Sound.play(dead,1);
    }

    public static void playitemGet() {
        Sound.play(item_get,1);
    }
}

