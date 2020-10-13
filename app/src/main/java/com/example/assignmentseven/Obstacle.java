package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public abstract class Obstacle extends Shape{

    boolean breakable; //can obj be broken
    boolean mover; //does the object move
    int xMoveSpeed;
    int yMoveSpeed;
    int xBound;
    int yBound;

    public Obstacle(int _x, int _y, Drawable _sprite) {
        super(_x, _y, _sprite);
    }

    public void isDestroyable(boolean value){
        breakable = value;
    }
    public void isMoving(boolean value){ mover = value; }
    public abstract boolean collided(int x, int y, int radius);

    public abstract int getHeight();
    public abstract int getWidth();
}
