package com.example.assignmentseven;


import android.graphics.*;
import android.graphics.drawable.Drawable;

public abstract class CircleObstacle extends Obstacle{

    int radius;

    public CircleObstacle(int _x, int _y, Drawable _sprite, int _radius){
        super(_x,_y,_sprite);
        radius = _radius;
    }

    @Override
    public int getWidth() {
        return radius*2;
    }

    @Override
    public void move(int dx, int dy) {
    }

    @Override
    public int getHeight() {
        return radius*2;
    }



}
