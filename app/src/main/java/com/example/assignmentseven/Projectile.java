package com.example.assignmentseven;

public class Projectile extends Shape{
    int speedMod;
    int radius;

    public Projectile(int _startX, int _startY, int _radius){
        super(_startX,_startY,null);
        speedMod = 1;
        radius = _radius;
    }

    @Override
    public void draw() {

    }

    @Override
    public void move() {

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
            return true;
        }
        return false;
    }


}
