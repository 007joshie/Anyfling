package com.example.assignmentseven;


import android.graphics.*;
import android.graphics.drawable.Drawable;

public class CircleObstacle extends Obstacle{

    int radius;

    public CircleObstacle(int _x, int _y, Drawable _sprite, int _radius){
        super(_x,_y,_sprite);
        radius=_radius;
        color = new Paint(Color.RED);
    }


        @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x,pos.y,radius,color);
    }

    @Override
    public void move(int dx, int dy) {

    }
}
