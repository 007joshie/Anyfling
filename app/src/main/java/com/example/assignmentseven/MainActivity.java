package com.example.assignmentseven;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mainMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainMusic = MediaPlayer.create(this,R.raw.mainmenu);
        mainMusic.setLooping(true);
        // Hide Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        // Set to fullscreen
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //mainMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mainMusic.start();
    }

    public void onClickStart(View v){

        //Toast.makeText(this, "You pressed my button!", Toast.LENGTH_SHORT).show();
        //create intent to start other activity
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    public void info(View v){
        Intent i = new Intent(this, InfoActivity.class);
        startActivity(i);
    }

}