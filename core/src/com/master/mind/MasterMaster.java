package com.master.mind;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MasterMaster extends ApplicationAdapter {
	//SpriteBatch batch;
	//Sprite sprite;
	Texture img;
	Stage stage;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		img = new Texture(Gdx.files.internal("background.png"));
		ScreenViewport viewport = new ScreenViewport();
		Gdx.input.setInputProcessor(stage);

		MyActor actor = new MyActor();
		stage.addActor(actor);
		stage.setKeyboardFocus(actor);



		//sprite = new Sprite(img);
		//sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		/*batch.begin();

		sprite.draw(batch);
		batch.end();*/
	}


	@Override
	public void dispose () {
		//batch.dispose();
	}
}
