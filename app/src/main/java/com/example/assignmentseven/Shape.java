package com.example.assignmentseven;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public abstract class Shape {
    Point Pos;
    Drawable Sprite;
    Paint Colour = new Paint(Color.GREEN);
    float Rotation;
    boolean Destroyable;
}
