package com.master.mind.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.master.mind.MasterMaster;


/**
 * Created by Leon on 28.09.2017.
 */

public class MenuScreen implements Screen {
    private MasterMaster game;
    private Stage stage;


    public MenuScreen(MasterMaster gam){
        this.game = gam;

        //set permute false, cause button is off
        game.options.setPermute(false);
    }
    @Override
    public void show() {
        Skin skin = game.manager.get("shad/uiskin.json");
        stage = new Stage(new StretchViewport(game.res.x / 4, game.res.y / 4));
        Table table = new Table();

        final TextButton playButton = new TextButton("Play", skin, "default");
        final TextButton permuteButton = new TextButton("Permute", skin, "toggle");
        final SelectBox selectBox = new SelectBox(skin);


        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.options.difficulty((String) selectBox.getSelected());
                game.setScreen(new PlayScreen(game));
            }
        });

        //permute button (can only be toggled on/off)
        permuteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (permuteButton.isChecked()) game.options.setPermute(true);
                else game.options.setPermute(false);
            }
        });



        selectBox.setItems("easy", "medium", "hard", "godlike");
        selectBox.setSelected(game.options.getPrefs().getString("Difficulty", "easy"));

        stage.addActor(table);

        //Layouting of menu
        table.align(Align.center | Align.top);
        table.setFillParent(true);
        table.setDebug(false);
        table.padTop(game.res.y / 16);
        table.add(playButton).fillX().uniformX();
        table.row();
        table.add(permuteButton).fillX().padTop(playButton.getHeight());
        table.row();
        table.add(selectBox).fillX().padTop(playButton.getHeight());

        Gdx.input.setInputProcessor(stage);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //important
        stage.getViewport().update(width, height, true);

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
