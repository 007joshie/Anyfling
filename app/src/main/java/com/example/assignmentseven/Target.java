package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Target extends CircleObstacle {

    int health;
    boolean destroyed = false;

    public Target(int _x, int _y, Drawable _sprite, int _radius, int _health) {
        super(_x, _y, _sprite, _radius);
        health = _health;
        paint.setColor(Color.CYAN);

    }

    @Override
    public void draw(Canvas canvas) {
        if (destroyed == false) {
            canvas.drawCircle(pos.x, pos.y, radius, paint);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(35);
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(health), pos.x, pos.y, paint);
            paint.setColor(Color.CYAN);
        }
        else {
            this.pos.x = -500;
            this.pos.y = -500;
        }
    }

    @Override
    public boolean collided(int x, int y, int rad) {
        int dx = pos.x-x;
        int dy = pos.y-y;
        float distance = (float) Math.sqrt((dx*dx) + (dy*dy));
        //paint.setColor(Color.RED);
        //paint.setColor(Color.GREEN);
        return distance < radius + rad;
    }

}
