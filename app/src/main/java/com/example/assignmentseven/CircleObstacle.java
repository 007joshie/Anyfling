package com.example.assignmentseven;


import android.graphics.*;
import android.graphics.drawable.Drawable;

public class CircleObstacle extends Obstacle{

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
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x,pos.y,radius,paint);
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public int getHeight() {
        return radius*2;
    }

    @Override
    public boolean collided(int x, int y, int rad) {
        int dx = pos.x-x;
        int dy = pos.y-y;
        float distance = (float) Math.sqrt((dx*dx) + (dy*dy));
        if(distance < radius + rad){
            paint.setColor(Color.RED);
            return true;
        }
        else {
            paint.setColor(Color.GREEN);
            return false;
        }
    }
}
