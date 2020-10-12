package com.example.assignmentseven;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    int startWidth = height/2;
    int startHeight = width/2;
    boolean enabled = true;

    Projectile ball = new Projectile(300,300,100);
    Projectile[] projectiles = {ball};



    //Holds all the levels
    Level[] levels;
    //current level number 0 1 or 2
    int lvlNum = 0;

    Portal p = new Portal(100,100,null,40);
    Portal p1 = new Portal(1000,100,null,40);


    public class GraphicsView extends View{
        private GestureDetector gestureDetector;
        Paint paint = new Paint();

        public GraphicsView(Context context){
            super(context);
            active = true;
            gestureDetector = new GestureDetector(context, new MyGestureListener());
            paint.setColor(getColor(R.color.colorPrimary));
        }

        private boolean active; // If our logic is still active

        // This just updates our position based on a delta that's given.
        public void update(int delta) {

            for (Projectile projectile : projectiles) {
                //levels[lvlNum].TestCollisions(projectile.pos.x,projectile.pos.y,projectile.radius);
                Obstacle collision = levels[lvlNum].getCollision(projectile);


                if (!projectile.selected) {

                    if (projectile.velocityX < 0.05f && projectile.velocityX > 0 || projectile.velocityX > -0.05f && projectile.velocityX < 0){
                        projectile.velocityX = 0;
                    }
                    if (projectile.pos.y + projectile.radius < 1080) {

                        projectile.velocityY = projectile.velocityY + (Forces.Gravity * delta);
                        projectile.pos.y += (int) projectile.velocityY;
                    }
                    if (projectile.pos.y + projectile.radius > 1080) {
                        projectile.pos.y = 1080 - projectile.radius - 1;
                        projectile.velocityY *= -projectile.bounce;
                    }
//                if (ball.pos.y - ball.radius < 0){
//                    ball.pos.y = ball.radius+1;
//                }


                    if (collision != null) {

                        if (collision instanceof RectangleObstacle) {
                            if (projectile.pos.x < collision.pos.x + 5) {
                                if (projectile.velocityX == 0 && projectile.velocityY > 0.5){
                                    projectile.velocityX += 0.2f;
                                }
                                projectile.velocityX *= -projectile.bounce;                         // left edge
                            }
                            if (projectile.pos.x > collision.pos.x + collision.getWidth() - 5) {
                                projectile.velocityX *= -projectile.bounce;       // right edge
                                if (projectile.velocityX == 0 && projectile.velocityY > 0.5){
                                    projectile.velocityX += 0.2f;
                                }
                            }

                            if (projectile.pos.y > collision.pos.y + collision.getHeight()) {
                                projectile.pos.y = collision.pos.y + collision.getHeight() + projectile.radius;
                                projectile.velocityY *= -projectile.bounce;     // bottom edge
                            }
                            if (projectile.pos.y < collision.pos.y) {
                                projectile.pos.y = collision.pos.y - projectile.radius;
                                projectile.velocityY *= -projectile.bounce;                        // top edge
                            }
                        } else if (collision instanceof CircleObstacle) {

                            int collisionPointX =
                                    ((projectile.pos.x * ((CircleObstacle)collision).radius) + (collision.pos.x * projectile.radius))
                                            / (projectile.radius + ((CircleObstacle)collision).radius);

                            int collisionPointY = ((projectile.pos.y * ((CircleObstacle)collision).radius) + (collision.pos.y * projectile.radius)) / (projectile.radius + ((CircleObstacle)collision).radius);

                            //newVelX1 = (firstBall.speed.x * (firstBall.mass – secondBall.mass) + (2 * secondBall.mass * secondBall.speed.x)) / (firstBall.mass + secondBall.mass);
                            //newVelY1 = (firstBall.speed.y * (firstBall.mass – secondBall.mass) + (2 * secondBall.mass * secondBall.speed.y)) / (firstBall.mass + secondBall.mass);


                            Log.i("TAG", "Circle Collision at" + collision.pos.x + "    " + collision.pos.y);
//                            if (projectile.pos.y < collision.pos.y){
//                                //projectile.pos.y = collision.pos.y- collision.getHeight() - projectile.radius;
//                                projectile.velocityY *= -projectile.bounce;
//                            } else {
//                               // projectile.pos.y = collision.pos.y+ collision.getHeight() + projectile.radius;
//                                projectile.velocityY *= -projectile.bounce;
//                            }
//                            projectile.velocityY *= -projectile.bounce;
//                            if (projectile.pos.x > collision.pos.x && projectile.velocityX == 0){
//                                projectile.velocityX += -2f;
//                            } if (projectile.pos.x < collision.pos.x && projectile.velocityX == 0) {
//                                projectile.velocityX += 2f;
//                            } if (projectile.pos.x == collision.pos.x && projectile.velocityX == 0) {
//
//                                // If bounced directly ontop of ball
//
//                                // Add random amount
//                                projectile.velocityX *= -projectile.bounce;
//                            }else {
//                                projectile.velocityX *= -projectile.bounce;
//                            }
                        } if (collision instanceof Target) {
                            ((Target) collision).health -= Math.abs(projectile.velocityY + projectile.velocityY);
                            if (((Target) collision).health <= 0) {
                                ((Target) collision).destroyed = true;
                            }
                        } if (collision instanceof Portal){
                            projectile.move(((Portal) collision).linked.pos.x,((Portal) collision).linked.pos.y);
                        }

                    }

                    if (projectile.pos.x + projectile.radius < 2220 && projectile.pos.x - projectile.radius > 0) {
                        projectile.pos.x += delta * projectile.velocityX;
                        projectile.velocityX = projectile.velocityX * Forces.Resistance;

                    }
                    if (projectile.pos.x + projectile.radius > 2220) {
                        projectile.pos.x = 2220 - projectile.radius - 1;
                        projectile.velocityX *= -projectile.bounce;
                    }
                    if (projectile.pos.x - projectile.radius < 0) {
                        projectile.pos.x = projectile.radius + 1;
                        projectile.velocityX *= -projectile.bounce;
                    }
                }
                else {/*?*/}

                postInvalidate(); // Tells our view to redraw itself, since our position changed.
            }
        }


        // The important part!
        // This starts another thread (let's call this THREAD_B). THREAD_B will run completely
        // independent from THREAD_A (above); therefore, FPS changes will not affect how
        // our velocity increases our position.
        public void PhysicsThread() {
            new Thread() {
                public void run() {
                    // Store the current time values.
                    long time1 = System.currentTimeMillis();
                    long time2;

                    // Once active is false, this loop (and thread) terminates.
                    while (active) {
                        try {
                            // This is your target delta. 25ms = 40fps
                            Thread.sleep(5);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                        time2 = System.currentTimeMillis(); // Get current time
                        int delta = (int) (time2 - time1); // Calculate how long it's been since last update
                        update(delta); // Call update with our delta
                        time1 = time2; // Update our time variables.
                    }
                }
            }.start(); // Start THREAD_B
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (Projectile projectile: projectiles){
                projectile.draw(canvas);
                debugPosition(projectile.pos.x,projectile.pos.y);
            }
            levels[lvlNum].draw(canvas);
            postInvalidate();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(gestureDetector.onTouchEvent(event)){
                return true;
            }else {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "Touch DOWN at " + event.getX() + "," + event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "Touch MOVE at " + event.getX() + "," + event.getY());
                        //if we are touching the main ball projectile
                        for (Projectile projectile: projectiles)
                        {
                            if (projectile.touched((int)event.getX(),(int)event.getY())){
                                projectile.move((int) event.getX(),(int) event.getY());
                                projectile.velocityY = 0;
                                projectile.velocityX = 0;
                                projectile.selected = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "Touch UP at " + event.getX() + "," + event.getY());
                        for (Projectile projectile: projectiles)
                        {
                            projectile.selected = false;
                        }
                        break;
                }
                //return true;

                return super.onTouchEvent(event);
            }
        }
    }

    public void debugPosition(int _x, int _y){
        //setContentView(R.layout.activity_game);
        TextView debugX = (TextView) findViewById(R.id.positionX);
        TextView debugY = (TextView) findViewById(R.id.positionY);
        TextView debugVX = (TextView) findViewById(R.id.velocityX);
        TextView debugVY = (TextView) findViewById(R.id.velocityY);

        debugX.setText("posX: "+_x);
        debugY.setText("posY: "+_y);
        debugVX.setText("veloX: "+ ball.velocityX);
        debugVY.setText("veloY: "+ball.velocityY);
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.i("TAG", "onDOWN");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i("TAG", "onFLING wit VX:" + velocityX + " & VY: " + velocityY +"/t/t/t" );


            // Fix this so the projectile that is selected gets rendered.
            for (Projectile projectile: projectiles)
            {
                projectile.selected = false;
                if (projectile.SwipeIntersect(e2)) {
                    if (velocityX < -2000 || velocityX > 2000) {
                        projectile.velocityX = velocityX / 2400.0f;
                    } else {
                        projectile.velocityX = velocityX / 840.0f;
                    }

                    if (velocityY < -2000 || velocityY > 2000) {
                        projectile.velocityY = velocityY / 300.0f;
                    } else {
                        projectile.velocityY = velocityY / 100.0f;
                    }
                    //projectile.thrown = true;
                }
            }

            return false;
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Hide Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        String version = BuildConfig.VERSION_NAME;

        Toast.makeText(getApplicationContext(),"Version Number: " + version,Toast.LENGTH_SHORT).show();
        //Get all the levels from CSV files
        p.setLinked(p1);

        try {
            loadLevels();
        } catch (IOException e) {
            e.printStackTrace();
        }
        levels[0].addObject(p);
        levels[0].addObject(p1);
        GraphicsView graphicsView = new GraphicsView(this);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.c1_game);
        constraintLayout.addView(graphicsView);

        graphicsView.PhysicsThread();
    }

    private void loadLevels() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.level1);
        Level l1 = new Level(is, 1);
        is = getResources().openRawResource(R.raw.level2);
        Level l2 = new Level(is, 2);
        is = getResources().openRawResource(R.raw.level3);
        Level l3 = new Level(is, 3);
        levels = new Level[]{l1,l2,l3};
    }

}
