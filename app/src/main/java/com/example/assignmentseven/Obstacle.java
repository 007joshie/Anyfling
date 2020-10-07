package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public abstract class Obstacle extends Shape{

    boolean breakable; //can obj be broken
    boolean bouncy; //is obj something that repels
    boolean mover; //does the object move

    public Obstacle(int _x, int _y, Drawable _sprite) {
        super(_x, _y, _sprite);
    }

    public void isDestroyable(boolean value){
        breakable = value;
    }
    public void isBouncy(boolean value){
        bouncy = value;
    }
    public void isMoving(boolean value){
        mover = value;
    }
}
