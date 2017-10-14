package com.master.mind;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.master.mind.Actors.PermutableArray;
import com.master.mind.Containers.BallArray;
import com.master.mind.Containers.StarArray;
import com.master.mind.Screens.PlayScreen;


/**
 * Created by Leon on 10.10.2017.
 */

public class GameLogic {
    public Image loseBanner;
    public Image winBanner;
    public PermutableArray<Color> colorSet;
    private PlayScreen playScreen;
    private BallArray arrayOfLines;

    private StarArray arrayOfStarGroups;
    private boolean isGameOver;
    //
    private int permuteDirection = MathUtils.random(1);

    public GameLogic(PlayScreen screen, BallArray bArray, StarArray sArray){
        this.playScreen = screen;
        this.arrayOfLines = bArray;
        this.arrayOfStarGroups = sArray;
        colorSet = new PermutableArray<Color>(true, 4);

        //initialize Textures of win/ lose
        Texture winTexture = playScreen.game.manager.get("gameAssets/win.png");
        Texture loseTexture = playScreen.game.manager.get("gameAssets/lose.png");

        winBanner = new Image(winTexture);
        loseBanner = new Image(loseTexture);

        playScreen.stage.addActor(winBanner);
        winBanner.setVisible(false);
        playScreen.stage.addActor(loseBanner);
        loseBanner.setVisible(false);

        loseBanner.setSize(playScreen.game.res.x / 2, playScreen.game.res.y / 8);
        loseBanner.setOrigin(loseBanner.getWidth() / 2, loseBanner.getHeight() / 2);
        loseBanner.setPosition(playScreen.game.res.x / 4, playScreen.game.res.y / 2);

        winBanner.setSize(playScreen.game.res.x / 2, playScreen.game.res.y / 8);
        winBanner.setOrigin(loseBanner.getWidth() / 2, loseBanner.getHeight() / 2);
        winBanner.setPosition(playScreen.game.res.x / 4, playScreen.game.res.y / 2);

        //construct color set
        for (int i = 0; i < 4; i++) {
            int randomNum = MathUtils.random(5);
            while (colorSet.contains(playScreen.colors.get(randomNum), true)) {
                randomNum = MathUtils.random(5);
            }
            Gdx.app.log("ColorSet", String.format("%d", randomNum));
            colorSet.add(playScreen.colors.get(randomNum));
        }
    }

    public void computeStars() {
        StarArray.StarGroup starGroup;
        BallArray.Line ballLine;

        ballLine = arrayOfLines.getArrayOfLines().get(arrayOfLines.getCurrentLine());
        starGroup = arrayOfStarGroups.getStarArray().get(arrayOfLines.getCurrentLine());
        int countRed = 0;
        int countWhite = 0;

        for (int i = 0; i < 4 ; i++) {
            if (ballLine.getBallLine().get(i).getColor().equals(colorSet.get(i)))
                countRed += 1;
            else if (colorSet.contains(ballLine.getBallLine().get(i).getColor(), false)) {
                countWhite += 1;
            }
        }
        //Gdx.app.log(String.format("%d", countRed), String.format("%d", countWhite));

        //starts painting stars red at right top star
        for (int i = 3; i >= 4 - countRed; i--) {
            starGroup.getStarGroup().get(i).setVisible(true);
            starGroup.getStarGroup().get(i).setTouchable(Touchable.disabled);
            starGroup.getStarGroup().get(i).setColor(Color.RED);
        }
        //rest of stars is white
        for (int i = 3 - countRed; i >= 4 - countRed - countWhite; i--) {
            starGroup.getStarGroup().get(i).setVisible(true);
            starGroup.getStarGroup().get(i).setTouchable(Touchable.disabled);
        }

        if (playScreen.game.options.isPermute()){
            switch (permuteDirection){
                case 0:
                    Gdx.app.log("permute", "left");
                    colorSet.permuteLeft();
                    break;
                case 1:
                    Gdx.app.log("permute", "Right");
                    colorSet.permuteRight();
                    break;
            }
        }

        if (countRed == 4) {
            youWin();
        }
        else if (arrayOfLines.getCurrentLine() == arrayOfLines.getMaxLine()){
            youLose();
        }
    }

    private void youWin(){
        setGameOver(true);
        winBanner.setVisible(true);
        ScaleByAction sba = new ScaleByAction();
        sba.setAmount(1f);
        sba.setDuration(1f);
        winBanner.addAction(sba);
    }

    private void youLose() {
        setGameOver(true);
        for (int i = 0; i < 4; i++) {
            arrayOfLines.solutionBalls.getBallLine().get(i).setVisible(true);
            arrayOfLines.solutionBalls.getBallLine().get(i).setTouchable(Touchable.disabled);
        }
        loseBanner.setVisible(true);
        ScaleByAction sba = new ScaleByAction();
        sba.setAmount(1f);
        sba.setDuration(1f);
        loseBanner.addAction(sba);

    }

    private void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
        for (int i = 0; i < 4; i++) {
            arrayOfLines.getArrayOfLines().get(arrayOfLines.getCurrentLine()).
                    getBallLine().get(i).setTouchable(Touchable.disabled);
        }
        if(arrayOfLines.getIndexOfPressed()!= -1)
        arrayOfLines.getArrayOfLines().get(arrayOfLines.getCurrentLine())
                .getBallLine().get(arrayOfLines.getIndexOfPressed()).setPressed(false);
        playScreen.playAgainButton.playAgainAnimation();
        playScreen.game.options.setTimer(false);
        playScreen.overlay.setVisible(true);
    }


    public boolean isGameOver() {
        return isGameOver;
    }
}


