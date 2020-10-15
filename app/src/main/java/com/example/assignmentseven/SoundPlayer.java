package com.example.assignmentseven;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool soundpool;
    private static int startSound;
    private static int destroySound;
    private static int zoomSound;
    private static int collideSound;
    private static int boost;


    public SoundPlayer(Context context){
        soundpool = new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        destroySound = soundpool.load(context, R.raw.boom,1);
        collideSound = soundpool.load(context, R.raw.boof,1);
        zoomSound = soundpool.load(context,R.raw.zoom,1);
        boost = soundpool.load(context,R.raw.boost,1);
    }

    public void playBoom(){
        soundpool.play(destroySound,1.0f,1.0f,1,0,1.0f);
    }

    public void playBoof(){
        soundpool.play(collideSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playZoom(){
        soundpool.play(zoomSound,1.0f,1.0f,1,0,1.0f);
    }

    public void playBoost(){
        soundpool.play(boost,1.0f,1.0f,1,0,1.0f);
    }




}
