package com.example.assignmentseven;

import android.graphics.Canvas;
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

    public Shape(int _x, int _y, Drawable _sprite){
        pos = new Point(_x,_y);
        sprite = _sprite;
        rotation = 0.0f;
        breakable = false;
        color = new Paint(Color.GREEN);
        color.setColor(Color.GREEN);

    }

    public abstract void draw(Canvas canvas);
    public abstract void move(int dx, int dy);
}
