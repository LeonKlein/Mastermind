package com.master.mind;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.master.mind.Screens.LoadingScreen;
import com.master.mind.Screens.MenuScreen;
import com.master.mind.Screens.PlayScreen;

public class MasterMaster extends Game {
	public float aspectRatio;
	public Vector2 res;
	public GameOptions options;
	public AssetManager manager;

	@Override
	public void create () {
		//resolution of the screen
		res = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		aspectRatio = res.y / res.x;

		manager = new AssetManager();
		options = new GameOptions();

		this.setScreen(new LoadingScreen(this));

	}
    public void render() {
        super.render(); // important!

    }

	@Override
	public void dispose () {
		//batch.dispose()
	}
}
