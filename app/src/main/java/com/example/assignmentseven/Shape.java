package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public abstract class Shape {
    Point pos;
    Drawable sprite;
    Paint paint = new Paint(Color.GREEN);
    float rotation;

    public Shape(int _x, int _y, Drawable _sprite){
        pos = new Point(_x,_y);
        sprite = _sprite;
        rotation = 0.0f;
        paint.setColor(Color.RED);
    }

    public abstract void draw(Canvas canvas);
    public abstract void move(int dx, int dy);
    public boolean collided(int x, int y, int radius) {
        return false;
    }
}
