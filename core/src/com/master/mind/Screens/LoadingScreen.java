package com.master.mind.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.master.mind.Actors.LoadingBall;
import com.master.mind.MasterMaster;

/**
 * Created by Leon on 07.10.2017.
 */

public class LoadingScreen implements Screen {
    private MasterMaster game;
    private Stage stage;
    private float elapsedTime = 0;
    private Animation<TextureRegion> ani;

    public LoadingScreen(MasterMaster gam){
        this.game = gam;
    }
    @Override
    public void show() {




        game.manager.load("Loading/LoadingPic.atlas", TextureAtlas.class);
        game.manager.finishLoading();


        stage = new Stage(new StretchViewport(game.res.x, game.res.y));

        TextureAtlas atlas = game.manager.get("Loading/LoadingPic.atlas", TextureAtlas.class);
        ani = new Animation<TextureRegion>(0.5f, atlas.getRegions());

        ani.setPlayMode(Animation.PlayMode.LOOP);
        LoadingBall loadingBall = new LoadingBall(ani);

        stage.addActor(loadingBall);
        loadingBall.setSize(game.res.x / 1.4f, game.res.y / 4);
        loadingBall.setPosition(game.res.x / 8, game.res.y / 2);


        game.manager.load("gameAssets/background.png", Texture.class);
        game.manager.load("gameAssets/lose.png", Texture.class);
        game.manager.load("gameAssets/button.png", Texture.class);
        game.manager.load("gameAssets/lose.png", Texture.class);
        game.manager.load("gameAssets/playAgain.png", Texture.class);
        game.manager.load("gameAssets/redStar.png", Texture.class);
        game.manager.load("gameAssets/white.png", Texture.class);
        game.manager.load("gameAssets/whiteStar.png", Texture.class);
        game.manager.load("gameAssets/win.png", Texture.class);
        //load font
        String path = "Font/SourceSansPro-Black.ttf";     //can be inside nested folder


        //important or wont work!!!!!!
        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parms2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parms2.fontFileName = path;    // path of .ttf file where that exist
        parms2.fontParameters.size = (int)Math.ceil(game.res.y * 1.0f / 16f);
        parms2.fontParameters.genMipMaps = true;
        parms2.fontParameters.minFilter = Texture.TextureFilter.Linear;
        parms2.fontParameters.magFilter = Texture.TextureFilter.Linear;
        game.manager.load("menufont.ttf", BitmapFont.class, parms2);

        game.manager.finishLoading();


        BitmapFont myBitmapFont = game.manager.get("menufont.ttf");
        ObjectMap<String, Object> skinResources = new ObjectMap<String, Object>();
        skinResources.put("ourfont", myBitmapFont);
        game.manager.load("skin/ourskin.json", Skin.class, new SkinLoader.SkinParameter("skin/ourskin.atlas", skinResources));



        FreetypeFontLoader.FreeTypeFontLoaderParameter parms = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parms.fontFileName = path;    // path of .ttf file where that exist
        parms.fontParameters.size = (int)(game.res.y * 1.5f / 16f);
        game.manager.load("font.ttf", BitmapFont.class, parms);   // fileName with extension, sameName will use to get from manager






    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        if (game.manager.update() && elapsedTime > 3f) { // Load some, will return true if done loading
                game.setScreen(new MenuScreen(game));
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
