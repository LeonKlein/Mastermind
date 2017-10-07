package com.master.mind.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 05.10.2017.
 */

public class SelectColorBall extends Image {
    private PlayScreen playScreen;


    public SelectColorBall(Texture texture, PlayScreen screen){
        super(texture);
        this.playScreen = screen;
        setBounds(getX(),getY(),getWidth(),getHeight());

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (playScreen.indexOfPressed != -1) {
                    playScreen.balls.get(playScreen.indexOfPressed).setColor(SelectColorBall.this.getColor());
                    playScreen.balls.get(playScreen.indexOfPressed).isPressed = false;
                    playScreen.indexOfPressed = -1;
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
