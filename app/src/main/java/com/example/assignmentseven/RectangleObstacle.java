package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;


public class RectangleObstacle extends Obstacle{

    int width;
    int height;

    public RectangleObstacle(int _x, int _y, Drawable _sprite, int _width, int _height) {
        super(_x, _y, _sprite);
        width = _width;
        height = _height;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(pos.x,pos.y,width,height,color);
    }

    @Override
    public void move(int dx, int dy) {

    }
}
