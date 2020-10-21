package com.example.assignmentseven;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    MediaPlayer endMusic;
    int score;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_score);

        endMusic = MediaPlayer.create(this,R.raw.chiptuneending);
        endMusic.setLooping(false);
        endMusic.start();
        TextView totalScoreLabel = findViewById(R.id.totalScoreLabel);

        // Hide Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        score = getIntent().getIntExtra("SCORE", 0);
        int totalLevels = getIntent().getIntExtra("TOTAL_LEVELS", 0);

        totalScoreLabel.setText("Total Score : " + score);
    }

    public void saveScore(View v){
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("last", score);
        editor.apply();

        Intent i = new Intent(getApplicationContext(), HighScoreActivity.class);
        startActivity(i);
    }

    public void returnTop(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}