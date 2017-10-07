package com.master.mind.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.master.mind.MasterMaster;

/**
 * Created by Leon on 07.10.2017.
 */

public class LoadingScreen implements Screen {
    private MasterMaster game;
    private Stage stage;

    public LoadingScreen(MasterMaster gam){
        this.game = gam;
    }
    @Override
    public void show() {
        game.manager.load("Leon.jpg", Texture.class);
        // First, let's define the params and then load our smaller font
        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = "basicfont.ttf";
        parameter.fontParameters.size = 10;
        game.manager.load("basicfont.ttf", BitmapFont.class, parameter);
        game.manager.finishLoading();

        stage = new Stage();
        Image loadPicture = new Image(game.manager.get("Leon.jpg", Texture.class));
        stage.addActor(loadPicture);
        loadPicture.setSize(stage.getWidth(), stage.getHeight());
/*
        game.manager.load("gameAssets/background.png", Texture.class);
        game.manager.load("gameAssets/lose.png", Texture.class);
        game.manager.load("gameAssets/button.png", Texture.class);
        game.manager.load("gameAssets/lose.png", Texture.class);
        game.manager.load("gameAssets/playAgain.png", Texture.class);
        game.manager.load("gameAssets/redStar.png", Texture.class);
        game.manager.load("gameAssets/white.png", Texture.class);
        game.manager.load("gameAssets/whiteStar.png", Texture.class);
        game.manager.load("gameAssets/win.png", Texture.class);

        game.manager.load("shad/uiskin.json", Skin.class, new SkinLoader.SkinParameter("shad/uiskin.atlas"));

        // First, let's define the params and then load our smaller font
        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = "basicfont.ttf";
        parameter.fontParameters.size = 10;
        game.manager.load("basicfont.ttf", BitmapFont.class, parameter);
*/


    }

    @Override
    public void render(float delta) {
        if (game.manager.update()) { // Load some, will return true if done loading
                game.setScreen(new MenuScreen(game));
        }

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.manager.unload("Leon.jpg");
    }
}
