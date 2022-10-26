package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Map extends Game {
    public List<String> createMap() {
        List<String> str = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level" + Level + ".txt");
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            while (true) {
                line = br.readLine();
                if (line == null) break;
                str.add(line);
            }

            String[] numb = str.get(0).trim().split(" ");
            HEIGHT = Integer.parseInt(numb[1]);
            WIDTH = Integer.parseInt(numb[2]);

            for (int i=0; i<HEIGHT; i++) {
                for (int j=0; j<WIDTH; j++) {
                    map_[i][j] = str.get(i+1).charAt(j);
                }
            }

            for (int i=0; i<HEIGHT; i++) {
                for (int j=0; j<WIDTH; j++) {
                    Entity obj;
                    Enemy obj_;
                    Buff buff;
                    Brick brick;
                    int i_ = i + 2;
                    switch (str.get(i+1).charAt(j)) {
                        case '#' :
                            obj = new Wall(j, i_, Sprite.wall);
                            stillObjects.add(obj);
                            break;
                        case ' ' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            break;
                        case '*' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            brick = new Brick(j, i_, Sprite.brick);
                            bricks.add(brick);
                            break;
                        case 'p' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            bomber = new Bomber(1, 3, Sprite.player_right);
                            break;
                        case 'x' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            buff = new Portal(j, i_, Sprite.portal);
                            buffs.add(buff);
                            brick = new Brick(j, i_, Sprite.brick);
                            bricks.add(brick);
                            break;
                        case '1' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Balloon(j ,i_, Sprite.balloom_right1);
                            enemies.add(obj_);
                            break;
                        case '2' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Oneal(j, i_, Sprite.oneal_right1);
                            enemies.add(obj_);
                            break;
                        case '3' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Doll(j, i_, Sprite.doll_right1);
                            enemies.add(obj_);
                            break;
                        case '4' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            obj_ = new Kondoria(j, i_, Sprite.kondoria_right1);
                            enemies.add(obj_);
                            break;
                        case 'f' :
                            obj = new Grass(j, i_, Sprite.grass);
                            stillObjects.add(obj);
                            buff = new Flame_Item(j, i_, Sprite.powerup_flames);
                            buffs.add(buff);
                            brick = new Brick(j, i_, Sprite.brick);
                            bricks.add(brick);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cant load file!");
        }
        return str;
    }
}
