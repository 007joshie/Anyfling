package com.example.assignmentseven;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    Projectile ball = new Projectile(startWidth,startHeight,100);

    public class GraphicsView extends View{
        private GestureDetector gestureDetector;
        Paint paint = new Paint();

        public GraphicsView(Context context){
            super(context);
            gestureDetector = new GestureDetector(context, new MyGestureListener());

            paint.setColor(getColor(R.color.colorPrimary)) ;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(ball.pos.x,ball.pos.y,ball.radius,paint);
            debugPosition(ball.pos.x,ball.pos.y);
            invalidate();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(gestureDetector.onTouchEvent(event)){
                return true;
            }else {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "Touch DOWN at " + event.getX() + "," + event.getY());
//                        ball.x = (int) event.getX();
//                        ball.y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "Touch MOVE at " + event.getX() + "," + event.getY());
                        if (ball.touched((int)event.getX(),(int)event.getY())){
                        ball.pos.x = (int) event.getX();
                        ball.pos.y = (int) event.getY();
                    }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "Touch UP at " + event.getX() + "," + event.getY());
//                        ball.x = (int) event.getX();
//                        ball.y = (int) event.getY();
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

        debugX.setText("posX: "+_x);
        debugY.setText("posY: "+_y);
    }



    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.i("TAG", "onDOWN");
//            ball.y = (int) e.getX();
//            ball.x = (int) e.getY();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            Log.i("TAG", "onFLING" );
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
    }
}
