package com.example.assignmentseven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hello Josh
    }

    public void onClickStart(View v){
        //Toast.makeText(this, "You pressed my button!", Toast.LENGTH_SHORT).show();
        //create intent to start other activity
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

}