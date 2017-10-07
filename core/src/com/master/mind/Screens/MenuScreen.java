package com.master.mind.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.master.mind.MasterMaster;

import java.awt.Checkbox;

/**
 * Created by Leon on 28.09.2017.
 */

public class MenuScreen implements Screen {
    private MasterMaster game;
    private Skin skin;
    private Stage stage;
    private Table table;

    public MenuScreen(MasterMaster gam){
        this.game = gam;
    }
    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("shad/uiskin.json"));
        stage = new Stage(new StretchViewport(game.res.x / 4, game.res.y / 4));

        table = new Table();


        final TextButton button = new TextButton("Play", skin, "default");
        final TextButton toggleButton = new TextButton("Permute", skin, "toggle");
        final SelectBox selectBox = new SelectBox(skin);


        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                diffilculty((String) selectBox.getSelected());
                game.setScreen(new PlayScreen(game));
            }
        });

        selectBox.setItems("easy", "medium", "hard", "godlike");





        //toggle button
        toggleButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.turnTime = 30;
                if (toggleButton.isChecked() == true) game.isPermute = true;
                else game.isPermute = false;
            }
        });




        stage.addActor(table);

        table.align(Align.center | Align.top);
        table.setFillParent(true);
        table.setDebug(true);
        table.padTop(game.res.y / 16);
        table.add(button).fillX().uniformX();
        table.row();
        table.add(toggleButton).fillX().padTop(button.getHeight());
        table.row();
        table.add(selectBox).fillX().padTop(button.getHeight());
        //button.setSize(game.res.x / 2, game.res.x / 2 / buttonRatio); ///flasch gescaled!!!!!!!!!!!!!!!!!!!!/

        Gdx.input.setInputProcessor(stage);
    }

    public void diffilculty(String grade){
        if (grade == "easy") {
            game.isTimer = false;
            game.turnTime = 0;
        }
        else if (grade == "medium") {
            game.isTimer = true;
            game.turnTime = 20;
        }
        else if (grade == "hard"){
            game.isTimer = true;
            game.turnTime = 10;
        }
        else {
            game.isTimer = true;
            game.turnTime = 5;
        }
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
