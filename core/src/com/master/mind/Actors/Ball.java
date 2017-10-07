package com.master.mind.Actors;

/**
 * Created by Leon on 28.09.2017.
 */

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.master.mind.Screens.PlayScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction;



public class Ball extends Image {

    public boolean isPressed;
    private ShapeRenderer shapeRenderer;
    private boolean projectionMatrixSet;
    private PlayScreen playScreen;


    public Ball(Texture texture, PlayScreen screen){
        super(texture);
        this.playScreen = screen;
        isPressed = false;
        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;


        setBounds(getX(),getY(),getWidth(),getHeight());

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (playScreen.indexOfPressed == -1) {
                    //Gdx.app.log("HIT", "ja");
                    playScreen.indexOfPressed = playScreen.balls.indexOf(Ball.this, true);
                    //Gdx.app.log("Num", String.format("%d", indexOfPressed));
                    isPressed = true;

                }
                else {
                    playScreen.balls.get(playScreen.indexOfPressed).isPressed = false;
                    isPressed = true;
                    playScreen.indexOfPressed = playScreen.balls.indexOf(Ball.this, true);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        if (isPressed){
            batch.end();
            if(!projectionMatrixSet){
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
            shapeRenderer.end();
            batch.begin();
        }

        ((TextureRegionDrawable)getDrawable()).draw(batch, getX(),getY(),
                getOriginX(),getOriginY(),
                getWidth(),getHeight(),
                getScaleX(),getScaleY(),
                getRotation());
    }
}
