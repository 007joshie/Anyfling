package com.example.assignmentseven;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public abstract class Shape {
    Point pos;
    Drawable sprite;
    Paint color;
    float rotation;
    boolean breakable;

    public Shape(int x, int y, Drawable _sprite){
        pos = new Point(x,y);
        sprite = _sprite;
        rotation = 0;
        breakable = false;
        color = new Paint(Color.GREEN);
    }

    public abstract void draw();
    public abstract void move();
}
