package com.master.mind.Containers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 09.10.2017.
 */

public class ColorBallBar {
    private BallArray arrayOfLines;

    public ColorBallBar(BallArray array){
        this.arrayOfLines = array;
        Array<SelectColorBall> bar;
        PlayScreen playScreen;

        //marcel findert es blöd
        playScreen = this.arrayOfLines.getPlayScreen();
        bar = new Array<SelectColorBall>(true, 6);
        for (int i = 0; i < 6; i++) {
            bar.add(new SelectColorBall());
            playScreen.stage.addActor(bar.get(i));
            bar.get(i).setSize(playScreen.game.res.y / 12.5f, playScreen.game.res.y / 12.5f); //quadratic
            bar.get(i).setPosition(playScreen.game.res.x / 6.2f * (i + 0.15f),
                    playScreen.game.res.y / 16f * 0.08f);
            bar.get(i).setColor(playScreen.colors.get(i));
        }
    }

    private class SelectColorBall extends Image {

        private SelectColorBall() {
            super(arrayOfLines.getBallTexture());

            setBounds(getX(), getY(), getWidth(), getHeight());

            addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    //write current Line of Balls into variable
                    BallArray.Line ballLine = arrayOfLines.getArrayOfLines().get(arrayOfLines.getCurrentLine());

                    if (arrayOfLines.getIndexOfPressed() != -1) {
                        ballLine.getBallLine().get(arrayOfLines.getIndexOfPressed()).setColor(SelectColorBall.this.getColor());
                        ballLine.getBallLine().get(arrayOfLines.getIndexOfPressed()).setPressed(false);
                        arrayOfLines.setIndexOfPressed(-1);
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }
}
