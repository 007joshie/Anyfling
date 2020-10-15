package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class Booster extends CircleObstacle {

    int strength;

    public Booster(int _x, int _y, Drawable _sprite, int _radius, int _strength) {
        super(_x, _y, _sprite, _radius);
        strength = _strength;
        paint.setColor(Color.parseColor("#B967FF"));

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x,pos.y,radius,paint);
    }

    @Override
    public boolean collided(int x, int y, int rad) {
        int dx = pos.x-x;
        int dy = pos.y-y;
        float distance = (float) Math.sqrt((dx*dx) + (dy*dy));
        return distance < radius + rad;
    }
}
