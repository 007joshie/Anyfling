package com.example.assignmentseven;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HighScoreActivity extends AppCompatActivity {

    TextView tv_score;
    int lastscore;
    int best1,best2,best3,best4,best5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        // Hide Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tv_score = (TextView)findViewById(R.id.tv_score);

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        lastscore = preferences.getInt("last",0);
        best1 = preferences.getInt("best1",0);
        best2 = preferences.getInt("best2",0);
        best3 = preferences.getInt("best3",0);
        best4 = preferences.getInt("best4",0);
        best5 = preferences.getInt("best5",0);

        if(lastscore > best5) {
            best5 = lastscore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best5", best5);
            editor.apply();
        }
        if(lastscore > best4) {
            int temp = best4;
            best4 = lastscore;
            best5 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best4", best4);
            editor.putInt("best5", best5);
            editor.apply();
        }
        if(lastscore > best3) {
            int temp = best3;
            best3 = lastscore;
            best4 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.putInt("best4", best4);
            editor.apply();
        }
        if(lastscore > best2) {
            int temp = best2;
            best2 = lastscore;
            best3 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best2);
            editor.putInt("best3", best3);
            editor.apply();

        }
        if(lastscore > best1) {
            int temp = best1;
            best1 = lastscore;
            best2 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best2);
            editor.putInt("best1", best1);
            editor.apply();
        }

        tv_score.setText("Top Score: " + best1 + "\n" +
                "Seccond Score: " + best2 + "\n" +
                "Third Score: " + best3 + "\n" +
                "Fourth Score: " + best4 + "\n" +
                "Fifth Score: " + best5 + "\n");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void returnToStart(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void reset(View view){
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("last", 0);
        editor.putInt("best1", 0);
        editor.putInt("best2", 0);
        editor.putInt("best3", 0);
        editor.putInt("best4", 0);
        editor.putInt("best5", 0);
        editor.apply();
        tv_score.setText("Top Score: " + 0 + "\n" +
                "Seccond Score: " + 0 + "\n" +
                "Third Score: " + 0 + "\n" +
                "Fourth Score: " + 0 + "\n" +
                "Fifth Score: " + 0 + "\n");
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
}
