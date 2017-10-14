package com.master.mind.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 13.10.2017.
 */

public class BlackOverlay extends Actor{
    private PlayScreen playScreen;
    ShapeRenderer shapeRenderer;


    public BlackOverlay(PlayScreen screen){
        this.playScreen = screen;
        shapeRenderer = new ShapeRenderer();
        playScreen.stage.addActor(this);
        this.setVisible(false);
    }




    public void draw(Batch batch, float parentAlpha) {

            batch.end();
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(0, 0, 0, 0.7f));
            shapeRenderer.rect(0, 0, playScreen.game.res.x, playScreen.game.res.y);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            batch.begin();
    }
}
