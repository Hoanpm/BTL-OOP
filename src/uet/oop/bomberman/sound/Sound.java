package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class Sound {
    public static String bomb_Explode = "res/sound/bom_explode.wav";

    public static String bg_sound = "res/sound/bg_sound.mp3";

    public static String bom_set = "res/sound/bom_set.wav";

    public static String dead = "res/sound/dead.wav";

    public static String endgame = "res/sound/end_game.wav";

    public static String item_get = "res/sound/item_get.wav";

    public static String menu = "res/sound/Menu.mp3";

    public static String win = "res/sound/win.mp3";

    public static String start_game = "res/sound/Startgame.wav";

    public static String pointup = "res/sound/point_up.wav";

    static Media media1 = new Media(Paths.get(bg_sound).toUri().toString());
    static MediaPlayer mediaPlayer1 = new MediaPlayer(media1);

    static Media media2 = new Media(Paths.get(menu).toUri().toString());

    static MediaPlayer mediaPlayer2 = new MediaPlayer(media2);

    static Media media4 = new Media(Paths.get(win).toUri().toString());

    static MediaPlayer mediaPlayer4 = new MediaPlayer(media4);

    static Media media5 = new Media(Paths.get(endgame).toUri().toString());

    static MediaPlayer mediaPlayer5 = new MediaPlayer(media5);



    public static void play(String s) {
        Media media = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

    public static void playmenu() {
        mediaPlayer2.setVolume(2);
        mediaPlayer2.setCycleCount(100);
        mediaPlayer2.play();
    }


    public static void stopmenu() {
        mediaPlayer2.stop();
    }

    public static void playbgSound() {
        mediaPlayer1.setVolume(20);
        mediaPlayer1.setCycleCount(10);
        mediaPlayer1.play();
    }

    public static void pausebgSound() {
        mediaPlayer1.pause();
    }

    public static void StopbgSound() {
        mediaPlayer1.stop();
    }

    public static void playStartgame() {
        Sound.play(start_game);
    }


    public static void playbomExplode() {
        Sound.play(bomb_Explode);
    }

    public static void playbomSet() {
        Sound.play(bom_set);
    }

    public static void playPointup() {
        Sound.play(pointup);
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

    public static void playWin() {
        mediaPlayer4.setVolume(20);
        mediaPlayer4.play();
    }

}
