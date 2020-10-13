package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;


public class RectangleObstacle extends Obstacle{

    int originX;
    int originY;
    int width;
    int height;

    public RectangleObstacle(int _x, int _y, Drawable _sprite, int _width, int _height) {
        super(_x, _y, _sprite);
        width = _width;
        height = _height;
        mover = false;
        originX = pos.x;
        originY = pos.y;
    }
    public RectangleObstacle(int _x, int _y, Drawable _sprite, int _width, int _height, int _xmovespeed,int _ymovespeed, int _dx, int _dy) {
        super(_x, _y, _sprite);
        width = _width;
        height = _height;
        xBound = pos.x + _dx;
        yBound = pos.y + _dy;
        yMoveSpeed = _ymovespeed;
        xMoveSpeed = _xmovespeed;
        originX = pos.x;
        originY = pos.y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(pos.x,pos.y,pos.x+width,pos.y+height,paint);
    }

    @Override
    public void move(int dx, int dy) {
        if(yMoveSpeed >= 0){
            //Log.d("MOVER", String.valueOf(moveSpeed));
            pos.y += yMoveSpeed;
            if(pos.y >= yBound){
                yMoveSpeed = -yMoveSpeed;
            }
        }
        else if(yMoveSpeed < 0){
            //Log.d("MOVER", String.valueOf(moveSpeed));
            pos.y += yMoveSpeed;
            if(pos.y <= originY){
                yMoveSpeed = -yMoveSpeed;
            }
        }

        if(xMoveSpeed >= 0){
            //Log.d("MOVER", String.valueOf(moveSpeed));
            pos.x += xMoveSpeed;
            if(pos.x >= xBound){
                xMoveSpeed = -xMoveSpeed;
            }
        }
        else if(xMoveSpeed < 0){
            //Log.d("MOVER", String.valueOf(moveSpeed));
            pos.x += xMoveSpeed;
            if(pos.x <= originX){
                xMoveSpeed = -xMoveSpeed;
            }
        }
        

    }

    @Override
    public boolean collided(int x, int y, int radius) {
        //x+=radius;
        //y+=radius;
        float testX = x;
        float testY = y;

        if (x < pos.x)testX = pos.x;                         // left edge
        else if (x > pos.x+width) testX = pos.x+width;       // right edge

        if (y < pos.y) testY = pos.y;                        // top edge
        else if (y > pos.y+height) testY = pos.y+height;     // bottom edge

        float distX = x-testX;
        float distY = y-testY;
        float distance = (float) Math.sqrt((distX*distX)+(distY*distY));

        if(distance < radius){
            //paint.setColor(Color.RED);
            return true;
        }
        else{
            //paint.setColor(Color.GREEN);
            return false;
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
}
