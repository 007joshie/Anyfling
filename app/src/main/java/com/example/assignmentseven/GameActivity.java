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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends AppCompatActivity {

    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    int startWidth = height/2;
    int startHeight = width/2;

    Projectile ball = new Projectile(startWidth,300,100);

    public class GraphicsView extends View{
        private GestureDetector gestureDetector;
        Paint paint = new Paint();

        public GraphicsView(Context context){
            super(context);
            velocity = .6f;
            active = true;
            gestureDetector = new GestureDetector(context, new MyGestureListener());
            paint.setColor(getColor(R.color.colorPrimary));
        }

        private float velocity; // How fast the dot's moving

        private boolean active; // If our logic is still active

        // This just updates our position based on a delta that's given.
        public void update(int delta) {
            if (ball.pos.y + ball.radius > width) return;
            if (!ball.selected) {
                ball.pos.y += delta * velocity;
                postInvalidate(); // Tells our view to redraw itself, since our position changed.
            }
        }

        // The important part!
        // This starts another thread (let's call this THREAD_B). THREAD_B will run completely
        // independent from THREAD_A (above); therefore, FPS changes will not affect how
        // our velocity increases our position.
        public void startMyLogicThread() {
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

        // Method that's called by the activity
        public void setActive(boolean active) {
            this.active = active;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            ball.draw(canvas);
            debugPosition(ball.pos.x,ball.pos.y);
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
                        if (ball.touched((int)event.getX(),(int)event.getY())){
                            ball.move((int) event.getX(),(int) event.getY());
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "Touch UP at " + event.getX() + "," + event.getY());
                        ball.selected = false;
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
            Log.i("TAG", "onFLING" );
            ball.velocityX = velocityX/10;
            ball.velocityY = velocityY/10;
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
        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.c1_game);
        constraintLayout.addView(graphicsView);

        graphicsView.startMyLogicThread();
    }

//    public void onPause() {
//        super.onPause();
//        // When our activity pauses, we want our view to stop updating its logic.
//        // This prevents your application from running in the background, which eats up the battery.
//        graphicsView.setActive(false);
//    }


}
