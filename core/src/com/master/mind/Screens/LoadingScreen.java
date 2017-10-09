package com.master.mind.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
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
        game.manager.finishLoading();

        stage = new Stage();
        Image loadPicture = new Image(game.manager.get("Leon.jpg", Texture.class));
        stage.addActor(loadPicture);
        loadPicture.setSize(stage.getWidth(), stage.getHeight());

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


        //load font
        String path="Font/SourceSansPro-Black.ttf";     //can be inside nested folder
        String fileName = "font.ttf" ;   // it can be any name with extension, w

        //important or wont work!!!!!!
        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parms = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parms.fontFileName = path;    // path of .ttf file where that exist
        parms.fontParameters.size = (int)(80 * Gdx.graphics.getDensity());
        game.manager.load(fileName, BitmapFont.class, parms);   // fileName with extension, sameName will use to get from manager



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
