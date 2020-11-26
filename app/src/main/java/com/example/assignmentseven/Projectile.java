package com.example.assignmentseven;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import static java.lang.Math.pow;

public class Projectile extends Shape{
    int speedMod;
    int radius;
    float velocityX = 0;
    float velocityY = 0;
    float bounce = 0.6f;
    Bitmap bitmapImg;


    boolean selected = false;
    boolean thrown = false;

    int hitboxTouch = 50;

    int hitboxSwipe = 100;


    public Projectile(Bitmap bitmap, int _startX, int _startY, int _radius){
        super(_startX,_startY,null);
        bitmapImg = bitmap;
        speedMod = 1;
        radius = _radius;
        paint.setColor(Color.parseColor("#FFFB96"));
    }

    @Override
    public void draw(Canvas canvas) {

        bitmapImg.drawBitmap(canvas);
        //canvas.drawCircle(pos.x,pos.y,radius,paint);
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
        if (distance <= radius + hitboxTouch) {
            //selected = true;
            return true;
        }
        //selected = false;
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

    public Boolean SwipeIntersect(MotionEvent e){
        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        int distX = (int) e.getX() - pos.x;
        int distY = (int) e.getY() - pos.y;
        int distance = (int) Math.sqrt( (distX*distX) + (distY*distY) );


        // if the distance is less than the circle's
        // radius the point is inside!
        if (distance <= (1200)) {
            return true;
        }
        return false;
    }



}
