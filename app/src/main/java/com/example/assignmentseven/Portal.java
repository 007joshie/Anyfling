package com.example.assignmentseven;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class Portal extends CircleObstacle {

    //IMPORTANT -- Portals are 1 - way. Therefore there is an entry and exit portal and the projectile can not return back through the portal.
    Portal linked;
    Boolean out;

    public Portal(int _x, int _y, Drawable _sprite, int _radius) {
        super(_x, _y, _sprite, _radius);
        out = false;
    }

    public Portal getLinked(){
        return linked;
    }

    public void setLinked(Portal p){
        linked = p;
        linked.paint.setColor(Color.parseColor("#0023BD"));
        paint.setColor(Color.parseColor("#C98000"));
        linked.out = true;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x,pos.y,radius*2,paint);
    }

    @Override
    public boolean collided(int x, int y, int rad) {
        //MINOR change here, as ive made it you need to be quite inside the portal for it to proc
        if (out) {
            return false;
        } else {
            int dx = pos.x - x;
            int dy = pos.y - y;
            float distance = (float) Math.sqrt((dx * dx) + (dy * dy));
            return distance < radius + rad;
        }
    }
}

