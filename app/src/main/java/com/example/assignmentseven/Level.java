package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.Point;
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
    String name;
    Point startPos;
    int targets;

    public Level(InputStream is) throws IOException {
        obstacles = new ArrayList<Obstacle>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line;
        while((line = br.readLine()) != null){
            Obstacle o;

            //Split
            String[] token = line.split(",");

            //Read data
            switch (token[0]) {
                case "rect":
                    if(token.length == 10){
                        o = new RectangleObstacle(Integer.parseInt(token[1])
                                ,Integer.parseInt(token[2])
                                ,null
                                ,Integer.parseInt(token[4])
                                ,Integer.parseInt(token[5])
                                ,Integer.parseInt(token[6])
                                ,Integer.parseInt(token[7])
                                ,Integer.parseInt(token[8])
                                ,Integer.parseInt(token[9]));

                    }
                    else{ o = new RectangleObstacle(Integer.parseInt(token[1]), Integer.parseInt(token[2]), null, Integer.parseInt(token[4]), Integer.parseInt(token[5])); }
                    obstacles.add(o);
                    break;
                case "targ":
                    o = new Target(Integer.parseInt(token[1]), Integer.parseInt(token[2]), null, Integer.parseInt(token[4]), Integer.parseInt(token[5]));
                    obstacles.add(o);
                    targets++;
                    break;
                case "port":
                    o = new Portal(Integer.parseInt(token[1]), Integer.parseInt(token[2]), null, Integer.parseInt(token[4]));
                    Portal link = new Portal(Integer.parseInt(token[5]), Integer.parseInt(token[6]), null, Integer.parseInt(token[8]));
                    ((Portal)o).setLinked(link);
                    obstacles.add(o);
                    obstacles.add(link);
                    break;
                case "boos":
                    o = new Booster(Integer.parseInt(token[1]), Integer.parseInt(token[2]), null, Integer.parseInt(token[4]),Integer.parseInt(token[5]));
                    obstacles.add(o);
                    break;
                case "meta":
                    startPos = new Point(Integer.parseInt(token[1]), Integer.parseInt(token[2]));
                    name = token[3];
                    number = Integer.parseInt(token[4]);
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

    public void addObject(Obstacle o){
        obstacles.add(o);
    }

    public void reset(){
        for(Obstacle o : obstacles){
            if(o instanceof Target){
                ((Target) o).destroyed = false;
            }
        }
    }

}
