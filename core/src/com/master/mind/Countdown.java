package com.master.mind;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 06.10.2017.
 */

public class Countdown {
    private int turnTime;
    private BitmapFont timerFont;
    private int countdown;
    private float oneSecond;
    private PlayScreen playScreen;

    public Countdown(int turnTime, PlayScreen screen){
        this.turnTime = turnTime;
        this.playScreen = screen;
        timerFont = playScreen.game.manager.get("font.ttf");
        oneSecond = 0; //LOL
        setCountdown();
    }

    public void setCountdown() {
        this.countdown = turnTime;
    }

    //in render
    public boolean countingDown(float delta){
        oneSecond += delta;
        if (oneSecond > 1f ) {
            countdown -= 1;
            oneSecond = 0f;
        }
        if (countdown < 1){

            countdown = turnTime;
            return true;
        }
        else return false;
    }



    public void draw(Batch batch, float x, float y){
        batch.begin();
        timerFont.draw(batch, String.format("%d", countdown), x, y);
        batch.end();
    }
}
