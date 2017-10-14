package com.master.mind.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.master.mind.Containers.BallArray;
import com.master.mind.Countdown;
import com.master.mind.GameLogic;
import com.master.mind.GameOptions;
import com.master.mind.MasterMaster;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 05.10.2017.
 */

public class MyButton extends Image {
    private BallArray arrayOfLines;
    private GameLogic gameLogic;
    private Countdown countdown;
    private GameOptions gameOptions;

    public MyButton(PlayScreen playScreen){
        super((Texture) playScreen.game.manager.get("gameAssets/button.png"));
        this.arrayOfLines = playScreen.ballArray;
        this.gameLogic = playScreen.gameLogic;
        this.countdown = playScreen.cd;
        this.gameOptions = playScreen.game.options;

        setSize(playScreen.game.res.x, playScreen.game.res.y * 2.5f / 16f);
        setPosition(0, arrayOfLines.getArrayOfLines().get(0).getBallLine().get(0).getHeight());
        setVisible(false);
        playScreen.stage.addActor(this);

        addListener(new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            pressButton();
            return super.touchDown(event, x, y, pointer, button);
        }
    });
}

    public void pressButton() {
        setVisible(false);
        gameLogic.computeStars();
        if (!gameLogic.isGameOver()) {
            arrayOfLines.incrementCurrentLine();
            if (gameOptions.isTimer())
                countdown.setCountdown();
        }
    }
}
