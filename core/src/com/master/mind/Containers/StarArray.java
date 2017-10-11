package com.master.mind.Containers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.master.mind.Screens.PlayScreen;

/**
 * Created by Leon on 09.10.2017.
 */

public class StarArray {
    private PlayScreen playScreen;
    private Array<StarGroup> starArray;
    private Texture starTexture;

    public StarArray(PlayScreen screen){
        playScreen = screen;
        starTexture = playScreen.game.manager.get("gameAssets/whiteStar.png");
        starArray = new Array<StarGroup>(true, 8);
        for (int i = 0; i < 8; i++) {
            starArray.add(new StarGroup());
            for (int j = 0; j < 4; j++) {
                if (j % 4 < 2) {
                    starArray.get(i).starGroup.get(j).setPosition(
                            (j % 2) * starArray.get(i).starGroup.get(j).getHeight()
                                    + playScreen.game.res.x * 0.02f,
                            playScreen.game.res.y / 16f * ((4f + 0.08f) + i * 1.5f));
                }
                else{
                    starArray.get(i).starGroup.get(j).setPosition(
                            (j % 2) * starArray.get(i).starGroup.get(j).getHeight()
                                    + playScreen.game.res.x * 0.02f,
                            playScreen.game.res.y / 16f * ((4f + 0.08f) + i * 1.5f) +
                                    starArray.get(i).starGroup.get(j).getHeight());
                }
            }
        }
    }

    public Array<StarGroup> getStarArray() {
        return starArray;
    }


    public class StarGroup{
        private Array<Star> starGroup;

        private StarGroup(){
            starGroup = new Array<Star>(true, 4);
            for (int i = 0; i < 4; i++) {
                starGroup.add(new Star());
                starGroup.get(i).setVisible(false);
                starGroup.get(i).setSize(
                        playScreen.game.res.x / 6f / 2.6f,
                        playScreen.game.res.x / 6f / 2.6f); //quadratic
                playScreen.stage.addActor(starGroup.get(i));
            }
        }

        public Array<Star> getStarGroup() {
            return starGroup;
        }

       public class Star extends Image{
            private Star(){
                super(starTexture);
            }
        }
    }
}
