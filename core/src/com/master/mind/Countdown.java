package com.master.mind;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


/**
 * Created by Leon on 06.10.2017.
 */

public class Countdown {
    private int turnTime;
    private BitmapFont timerFont;
    private int countdown;
    private float oneSecond;


    public Countdown(int turnTime, AssetManager manager){
        this.turnTime = turnTime;
        timerFont = manager.get("font.ttf");
        //timerFont.setColor(Color.BLACK);
        oneSecond = 0; //LOL
        setCountdown();
    }

    public void setCountdown()
    {
        this.countdown = turnTime;
    }

    //in render
    public boolean timeIsUp(float delta){
        oneSecond += delta;
        if (oneSecond > 1f ) {
            countdown -= 1;
            oneSecond = 0f;
        }
        return (countdown < 1);
    }



    public void draw(Batch batch, float x, float y){
        batch.begin();
        timerFont.draw(batch, String.format("%d", countdown), x, y);
        batch.end();
    }
}
