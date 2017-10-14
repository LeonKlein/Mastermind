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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.master.mind.Actors.LoadingBall;
import com.master.mind.MasterMaster;

/**
 * Created by Leon on 07.10.2017.
 */

public class LoadingScreen implements Screen {
    private MasterMaster game;
    private Stage stage;
    //SpriteBatch batch;
    private float elapsedTime = 0;
    private Animation<TextureRegion> ani;

    public LoadingScreen(MasterMaster gam){
        this.game = gam;
    }
    @Override
    public void show() {




        game.manager.load("Loading/LoadingPic.atlas", TextureAtlas.class);
        //game.manager.load("Loading/Loading.png", Texture.class);
        game.manager.finishLoading();

        //batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(game.res.x, game.res.y));

        TextureAtlas atlas = game.manager.get("Loading/LoadingPic.atlas", TextureAtlas.class);
        Gdx.app.log("Size", String.format("%d",atlas.getRegions().size));
        ani = new Animation<TextureRegion>(0.7f, atlas.getRegions());
        //Gdx.app.log("Size", String.format("%d",atlas.getRegions().size));
        ani.setPlayMode(Animation.PlayMode.LOOP);
        LoadingBall loadingBall = new LoadingBall(ani);

        stage.addActor(loadingBall);
        loadingBall.setSize(game.res.x / 1.4f, game.res.y / 4);
        loadingBall.setPosition(game.res.x / 8, game.res.y / 2);
        //Image loadPicture = new Image(game.manager.get("Loading/Loading.png", Texture.class));
        //stage.addActor(loadPicture);
        //loadPicture.setSize(stage.getWidth(), stage.getHeight());

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
        //parms.fontParameters.size = (int)(80 * Gdx.graphics.getDensity());
        parms.fontParameters.size = (int)(game.res.y * 1.5f / 16f);
        game.manager.load(fileName, BitmapFont.class, parms);   // fileName with extension, sameName will use to get from manager



    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        if (game.manager.update() && elapsedTime > 5f) { // Load some, will return true if done loading
                game.setScreen(new MenuScreen(game));
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //sprite.draw(batch);
        //elapsedTime += Gdx.graphics.getDeltaTime();
        //batch.draw((TextureRegion) ani.getKeyFrame(elapsedTime, true), 0, 0);
        //batch.end();

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
