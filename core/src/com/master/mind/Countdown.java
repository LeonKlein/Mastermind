package com.master.mind;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Leon on 06.10.2017.
 */

public class Countdown {
    private int turnTime;
    private BitmapFont timerFont;
    private int countdown;
    private float oneSecond;

    public Countdown(int turnTime){
        this.turnTime = turnTime;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("basicFont/BasicFontRegular.ttf"));
        timerFont = createFont(generator, 106);
        oneSecond = 0;
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

    //timer font
    BitmapFont createFont(FreeTypeFontGenerator ftfg, float dp)
    {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(dp * Gdx.graphics.getDensity());
        return ftfg.generateFont(parameter);
    }

    public void draw(Batch batch, float x, float y){
        batch.begin();
        timerFont.draw(batch, String.format("%d", countdown), x, y);
        batch.end();
    }
}
