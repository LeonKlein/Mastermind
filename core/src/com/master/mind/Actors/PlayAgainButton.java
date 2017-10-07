package com.master.mind.Actors;

import com.badlogic.gdx.graphics.Texture;
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
    MasterMaster game;


    public PlayAgainButton(Texture texture, MasterMaster gam){
        super(texture);
        this.game = gam;


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
        this.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.fadeIn(0.5f)));
    }
}
