package com.master.mind.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Leon on 13.10.2017.
 */

public class LoadingBall extends Actor {
    protected Animation<TextureRegion> animation = null;
    TextureRegion reg;
    float stateTime = 0;

    public LoadingBall(Animation<TextureRegion> ani) {
        //super(ani.getKeyFrame(0));
        this.animation = ani;
        reg = animation.getKeyFrame(0);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        reg = animation.getKeyFrame(stateTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(reg, getX(), getY(), getWidth(), getHeight());
    }


}