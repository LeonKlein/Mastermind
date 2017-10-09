package com.master.mind.Containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.master.mind.Actors.Ball;
import com.master.mind.MasterMaster;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 08.10.2017.
 */

public class BallArray {
    private Array<Line> ballArray;
    private PlayScreen playScreen;
    private int indexOfPressed;
    private Texture ballTexture;
    private int currentLine;
    private int nextLine;


    public BallArray(PlayScreen screen) {
        this.playScreen = screen;
        setCurrentLine(0);
        setNextLine(1);
        ballTexture = playScreen.game.manager.get("gameAssets/white.png");
        ballArray = new Array<Line>(true, 8);
        for (int i = 0; i < 8; i++) {
            ballArray.add(new Line());
            for (int j = 0; j < 4; j++) {
                ballArray.get(i).ballLine.get(j).setPosition(
                        playScreen.game.res.x / 9f * (1.5f + 1.875f * j + 0.3f),
                        playScreen.game.res.y / 16f * ((4f + 0.08f) + i * 1.5f));
            }
        }

    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public int getNextLine() {
        return nextLine;
    }

    public void setNextLine(int nextLine) {
        this.nextLine = nextLine;
    }

    class Line {
        Array<Ball> ballLine;

        Line() {

            ballLine = new Array<Ball>(true, 4);
            for (int i = 0; i < 4; i++) {
                ballLine.add(new Ball());
                ballLine.get(i).setColor(playScreen.color_def);
                ballLine.get(i).setSize(playScreen.game.res.y / 12.5f, playScreen.game.res.y / 12.5f); //quadratic
                playScreen.stage.addActor(ballLine.get(i));
                //Methoden für Reihe
            }
        }
        class Ball extends Image {

            private boolean isPressed;
            private ShapeRenderer shapeRenderer;
            private boolean projectionMatrixSet;


            public Ball(){
                super(ballTexture);
                isPressed = false;
                shapeRenderer = new ShapeRenderer();
                projectionMatrixSet = false;


                setBounds(getX(),getY(),getWidth(),getHeight());

                addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (indexOfPressed == -1) {
                            //Gdx.app.log("HIT", "ja");
                            indexOfPressed = ballArray.get(getCurrentLine()).ballLine.indexOf(Ball.this, true);
                            //Gdx.app.log("Num", String.format("%d", indexOfPressed));
                            setPressed(true);

                        }
                        else {
                            ballArray.get(currentLine).ballLine.get(indexOfPressed).setPressed(false);
                            setPressed(true);
                            indexOfPressed = ballArray.get(getCurrentLine()).ballLine.indexOf(Ball.this, true);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });


            }


            public boolean isPressed() {
                return isPressed;
            }

            public void setPressed(boolean pressed) {
                isPressed = pressed;
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
    }
}
