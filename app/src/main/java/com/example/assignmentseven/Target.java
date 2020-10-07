package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Target extends CircleObstacle {

    int health;

    public Target(int _x, int _y, Drawable _sprite, int _radius, int _health) {
        super(_x, _y, _sprite, _radius);
        health = _health;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x,pos.y,radius,paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35);
        paint.setColor(Color.BLACK);
        canvas.drawText(String.valueOf(health),pos.x,pos.y,paint);
        paint.setColor(Color.GREEN);
    }

}
