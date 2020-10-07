package com.example.assignmentseven;

import android.graphics.Canvas;

public class Projectile extends Shape{
    int speedMod;
    int radius;
    float velocityX = 0;
    float velocityY = 0;
    float bounce = 0.5f;
    boolean selected = false;

    int hitbox = 10;

    public Projectile(int _startX, int _startY, int _radius){
        super(_startX,_startY,null);
        speedMod = 1;
        radius = _radius;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x,pos.y,radius,paint);
    }

    @Override
    public void move(int dx, int dy) {
        pos.x = dx;
        pos.y = dy;
    }

    public boolean touched(int px, int py){
        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        int distX = px - pos.x;
        int distY = py - pos.y;
        int distance = (int) Math.sqrt( (distX*distX) + (distY*distY) );


        // if the distance is less than the circle's
        // radius the point is inside!
        if (distance <= radius) {
            selected = true;
            return true;
        }
        selected = false;
        return false;
    }

    public Projectile Selected(int px, int py){
        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        int distX = px - pos.x;
        int distY = py - pos.y;
        int distance = (int) Math.sqrt( (distX*distX) + (distY*distY) );


        // if the distance is less than the circle's
        // radius the point is inside!
        if (distance <= radius) {
            return this;
        }
        return null;
    }


}
