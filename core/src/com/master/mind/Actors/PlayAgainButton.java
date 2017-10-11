package com.master.mind.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.master.mind.MasterMaster;
import com.master.mind.Screens.MenuScreen;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 05.10.2017.
 */

public class PlayAgainButton extends Image {
    private MasterMaster game;


    public PlayAgainButton(PlayScreen playScreen){
        super((Texture) playScreen.game.manager.get("gameAssets/playAgain.png"));
        this.game = playScreen.game;
        playScreen.stage.addActor(this);
        setVisible(false);
        setPosition(0, 0);
        setSize(playScreen.game.res.x, playScreen.game.res.y * 0.2f);


        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new MenuScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    public void playAgainAnimation() {
        this.setVisible(true);
        this.setTouchable(Touchable.enabled);
        Action fadeLoop = Actions.forever(Actions.sequence(Actions.fadeOut(1f), Actions.fadeIn(1f)));
        this.addAction(fadeLoop);
    }

    //playAgainButton

}
