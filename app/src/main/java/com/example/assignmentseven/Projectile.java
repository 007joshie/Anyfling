package com.example.assignmentseven;

public class Projectile {
    int x = 0;
    int y = 0;
    int speedMod;
    int radius;

    public Projectile(int startX, int startY, int width, int height){
        x = startX;
        y = startY;
        speedMod = 1;
        radius = 50;
    }

    public Projectile(int _startX, int _startY, int _radius){
        x = _startX;
        y = _startY;
        speedMod = 1;
        radius = _radius;
    }

    public boolean touched(int px, int py, int cx, int cy, int r){
        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        int distX = px - cx;
        int distY = py - cy;
        int distance = (int) Math.sqrt( (distX*distX) + (distY*distY) );


        // if the distance is less than the circle's
        // radius the point is inside!
        if (distance <= r) {
            return true;
        }
        return false;
    }


}
