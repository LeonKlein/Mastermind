package com.master.mind;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.master.mind.Screens.LoadingScreen;

public class MasterMaster extends Game {
	public Vector2 res;
	public GameOptions options;
	public AssetManager manager;

	@Override
	public void create () {
		//resolution of the screen
		res = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		float aspectRatio = res.y / res.x;

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
