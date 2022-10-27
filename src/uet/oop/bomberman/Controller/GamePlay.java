package uet.oop.bomberman.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.GameDisplay.Game;
import uet.oop.bomberman.sound.Sound;

import java.io.IOException;

public class GamePlay {
    public static Stage stage;
    private Scene scene;

    public void gamedisplay(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Game game = new Game();
        game.gamescreen(actionEvent);
    }

    public void switchToControlGuide(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_File/controlGuide.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Sound.playmenu();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_File/menu.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void switchToGameOverScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Sound.playEndgame();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_File/GameoverScene.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToVictoryScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_File/VictoryScene.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}