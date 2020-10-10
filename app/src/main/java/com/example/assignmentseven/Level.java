package com.example.assignmentseven;

import android.graphics.Canvas;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Level {

    public List<Obstacle> obstacles;
    int number;



    public Level(InputStream is, int lvlNum) throws IOException {
        obstacles = new ArrayList<Obstacle>();
        number = lvlNum;
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line;
        while((line = br.readLine()) != null){
            Obstacle o;

            //Split
            String[] token = line.split(",");

            //Read data
            switch (token[0]) {
                case "rect":
                    o = new RectangleObstacle(Integer.parseInt(token[1]), Integer.parseInt(token[2]), null, Integer.parseInt(token[4]), Integer.parseInt(token[5]));
                    obstacles.add(o);
                    break;
                case "circ":
                    o = new CircleObstacle(Integer.parseInt(token[1]), Integer.parseInt(token[2]), null, Integer.parseInt(token[4]));
                    obstacles.add(o);
                    break;
                case "targ":
                    o = new Target(Integer.parseInt(token[1]), Integer.parseInt(token[2]), null, Integer.parseInt(token[4]), Integer.parseInt(token[5]));
                    obstacles.add(o);
                    break;
            }

        }
    }

    public void draw(Canvas canvas){
        for(Obstacle o : obstacles){
            o.draw(canvas);
        }
    }


    public boolean TestCollisions(int x, int y, int r){
        boolean occurance = false;
        for(Obstacle o : obstacles){
            o.collided(x,y,r);
            occurance = true;
        }
        return occurance;
    }

    public Obstacle getCollision(Projectile p){
        for(Obstacle o : obstacles){
            if (o.collided(p.pos.x,p.pos.y,p.radius)){
                return o;
            }
        }
        return null;
    }

}
