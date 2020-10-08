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

    RectangleObstacle rectObstacle = new RectangleObstacle(1100,300,null,50,300);
    RectangleObstacle rectObstacle1 = new RectangleObstacle(1300,300,null,50,200);
    RectangleObstacle rectObstacle2 = new RectangleObstacle(1500,300,null,50,100);
    RectangleObstacle[] obstacles = {rectObstacle,rectObstacle1,rectObstacle2};

    RectangleObstacle testRect = new RectangleObstacle(1300,600,null,300,300);

    CircleObstacle circObstacle = new CircleObstacle(1000,200,null,100);
    Target target = new Target(500,300,null,70,50);



    public class GraphicsView extends View{
        private GestureDetector gestureDetector;
        Paint paint = new Paint();

        public GraphicsView(Context context){
            super(context);
            active = true;
            gestureDetector = new GestureDetector(context, new MyGestureListener());
            paint.setColor(getColor(R.color.colorPrimary));
        }

        private float velocity; // How fast the dot's moving

        private boolean active; // If our logic is still active

        // This just updates our position based on a delta that's given.
        public void update(int delta) {

            for (Projectile projectile : projectiles) {

                //some simple collision code
                circObstacle.collided(projectile.pos.x, projectile.pos.y, projectile.radius);
                testRect.collided(projectile.pos.x, projectile.pos.y, projectile.radius);

                //or can be used in IF statement
                if (target.collided(projectile.pos.x, projectile.pos.y, projectile.radius)) {
                    target.health -= 1;
                }
                //or in loop
                for (RectangleObstacle r : obstacles) {
                    r.collided(projectile.pos.x, projectile.pos.y, projectile.radius);
                }


                if (!projectile.selected) {
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
                } else {
                }

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

            for(RectangleObstacle r:obstacles){
                r.draw(canvas);
            }
            circObstacle.draw(canvas);
            target.draw(canvas);
            testRect.draw(canvas);
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

                        if (velocityX < -2000 || velocityX > 2000) {
                            projectile.velocityX = velocityX / 3000.0f;
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

        GraphicsView graphicsView = new GraphicsView(this);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.c1_game);
        constraintLayout.addView(graphicsView);

        graphicsView.PhysicsThread();
    }

}
