package com.master.mind.Containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.master.mind.Actors.PermutableArray;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 08.10.2017.
 */

public class BallArray {
    private Array<Line> arrayOfLines;
    public Line solutionBalls;
    private PlayScreen playScreen;
    private int indexOfPressed;
    private Texture ballTexture;
    private int currentLine; //stars at zero
    private final int maxLine = 7;


    public BallArray(PlayScreen screen) {
        this.playScreen = screen;
        currentLine = 0;
        ballTexture = playScreen.game.manager.get("gameAssets/white.png");

        //construct the main ball array (8 lines with 4 balls each)
        arrayOfLines = new Array<Line>(true, 8);
        for (int i = 0; i < 8; i++) {
            arrayOfLines.add(new Line());
            for (int j = 0; j < 4; j++) {
                arrayOfLines.get(i).ballLine.get(j).setPosition(
                        playScreen.game.res.x / 9f * (1.5f + 1.875f * j + 0.3f),
                        playScreen.game.res.y / 16f * ((4f + 0.08f) + i * 1.5f));
            }
        }
        //set first line visible at startup
        for (int i = 0; i < 4; i++) {
            arrayOfLines.get(0).getBallLine().get(i).setVisible(true);
        }
    }

    //construct solution balls
    public void constructSolutionBalls(PermutableArray<Color> colorSet) {
        solutionBalls = new Line();
        for (int i = 0; i < 4; i++) {
            solutionBalls.getBallLine().get(i).
                    setColor(colorSet.get(i));
            solutionBalls.getBallLine().get(i).
                    setPosition(playScreen.game.res.x / 9f * (1.5f + 1.875f * i + 0.3f),
                            arrayOfLines.get(0).getBallLine().get(0).getHeight() + playScreen.game.res.y / 16f);
        }
    }
    public boolean hasGrey(){
        BallArray.Line currentBallLine = arrayOfLines.get(currentLine);
        for (int i = 0; i < 4 ; i++) {
            if (currentBallLine.getBallLine().get(i).getColor().equals(playScreen.color_def))
                return true;
        }
        return false;
    }

    public void incrementCurrentLine() {
        if(getIndexOfPressed() != -1) {
            arrayOfLines.get(getCurrentLine()).getBallLine().get(indexOfPressed).setPressed(false);
            setIndexOfPressed(-1);
        }
        for (int i = 0; i < 4; i++) {
            arrayOfLines.get(currentLine).getBallLine().get(i).setTouchable(Touchable.disabled);
        }
        this.currentLine++;
        for (int i = 0; i < 4; i++) {
            arrayOfLines.get(currentLine).getBallLine().get(i).setVisible(true);
        }
    }

    public void fillBalls(){
        Array<Color> usedColors = new Array<Color>(true, 4);
        for (int i = 0; i < 4 ; i++) {
            usedColors.add(arrayOfLines.get(getCurrentLine()).getBallLine().get(i).getColor());
        }
        for (int i = 0; i < 4 ; i++) {
            if (arrayOfLines.get(getCurrentLine()).getBallLine().get(i).getColor().equals(playScreen.color_def)){
                int randomNum = MathUtils.random(5);
                while (usedColors.contains(playScreen.colors.get(randomNum), false)) {
                    randomNum = MathUtils.random(5);
                }
                arrayOfLines.get(getCurrentLine()).getBallLine().get(i).setColor(playScreen.colors.get(randomNum));
                usedColors.add(playScreen.colors.get(randomNum));
            }
        }

    }

    //helper methods
    PlayScreen getPlayScreen() {
        return playScreen;
    }

    public Array<Line> getArrayOfLines() {
        return arrayOfLines;
    }

    Texture getBallTexture() {
        return ballTexture;
    }

    int getIndexOfPressed() {
        return indexOfPressed;
    }

    void setIndexOfPressed(int indexOfPressed) {
        this.indexOfPressed = indexOfPressed;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public class Line {
        Array<Ball> ballLine;

        Line() {
            ballLine = new Array<Ball>(true, 4);
            for (int i = 0; i < 4; i++) {
                ballLine.add(new Ball());
                ballLine.get(i).setColor(playScreen.color_def);
                ballLine.get(i).setSize(playScreen.game.res.y / 12.5f, playScreen.game.res.y / 12.5f); //quadratic
                ballLine.get(i).setVisible(false);
                playScreen.stage.addActor(ballLine.get(i));
                //Methoden für Reihe
            }
        }

        public Array<Ball> getBallLine() {
            return ballLine;
        }

        public class Ball extends Image {

            private boolean isPressed;
            private ShapeRenderer shapeRenderer;
            private boolean projectionMatrixSet;


            Ball(){
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
                            indexOfPressed = arrayOfLines.get(getCurrentLine()).ballLine.indexOf(Ball.this, true);
                            //Gdx.app.log("Num", String.format("%d", indexOfPressed));
                            setPressed(true);

                        }
                        else {
                            arrayOfLines.get(currentLine).ballLine.get(indexOfPressed).setPressed(false);
                            setPressed(true);
                            indexOfPressed = arrayOfLines.get(getCurrentLine()).ballLine.indexOf(Ball.this, true);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });


            }


            void setPressed(boolean pressed) {
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
