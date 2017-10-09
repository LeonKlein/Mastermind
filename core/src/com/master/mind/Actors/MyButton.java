package com.master.mind.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 05.10.2017.
 */

public class MyButton extends Image {
    PlayScreen playScreen;
    private boolean isWon;

    public MyButton(Texture texture, PlayScreen screen){
        super(texture);
        this.playScreen = screen;



        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressButton();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void pressButton(){
        isWon = playScreen.computeStars();
        playScreen.line += 1;
        if (playScreen.game.options.isTimer()) playScreen.cd.setCountdown();

        if (playScreen.indexOfPressed != -1) {
            playScreen.balls.get(playScreen.indexOfPressed).isPressed = false;
            playScreen.indexOfPressed = -1;
        }
        //this happens if you win during any state of game
        if(isWon) {
            playScreen.youWin();
            for (int i = playScreen.line * 4; i < 4 * (playScreen.line + 1); i++) {
                playScreen.balls.get(i - 4).setTouchable(Touchable.disabled);
                MyButton.this.setVisible(false);
                playScreen.playAgainButton.playAgainAnimation();
            }
        }
        //this happens if you reach the last row and dont make it
        else if(!isWon && playScreen.line == playScreen.maxLine){
            MyButton.this.setVisible(false);
            playScreen.youLose();
            for (int i = 0; i < 4; i++) {
                playScreen.solutionBalls.get(i).setVisible(true);
                playScreen.solutionBalls.get(i).setTouchable(Touchable.disabled);
                playScreen.balls.get((playScreen.line - 1) * 4 + i).setTouchable(Touchable.disabled);
                playScreen.playAgainButton.playAgainAnimation();
            }
            //this happens if you go to next row without losing
        } else {
            for (int i = playScreen.line * 4; i < 4 * (playScreen.line + 1); i++) {
                playScreen.balls.get(i).setVisible(true);
                playScreen.balls.get(i - 4).setTouchable(Touchable.disabled);
                MyButton.this.setVisible(false);
            }
        }
    }

}
