package com.master.mind.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.master.mind.Containers.BallArray;
import com.master.mind.Containers.ColorBallBar;
import com.master.mind.Containers.StarArray;
import com.master.mind.Countdown;
import com.master.mind.GameLogic;
import com.master.mind.MasterMaster;
import com.master.mind.Actors.MyButton;
import com.master.mind.Actors.PlayAgainButton;


/**
 * Created by Leon on 28.09.2017.
 */

public class PlayScreen implements Screen {
    public MasterMaster game;

    public Stage stage;

    public BallArray ballArray;
    private StarArray starArray;
    private ColorBallBar bar;

    public GameLogic gameLogic;
    public Array<Color> colors;
    public Color color_def;

    private MyButton confirmButton;
    public PlayAgainButton playAgainButton;

    public Countdown cd;


    PlayScreen(MasterMaster game) {
        this.game = game;
    }

    @Override
    public void show() {

        //initialize color array
        colors = new Array<Color>();
        colors.add(Color.CYAN);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.PINK);

        //default color
        color_def = Color.LIGHT_GRAY;

        //initialize stage + background
        stage = new Stage(new ScreenViewport());
        Texture bg = game.manager.get("gameAssets/background.png");
        Image background = new Image(bg);
        background.setSize(game.res.x, game.res.y);
        background.setTouchable(Touchable.disabled);
        stage.addActor(background);

        ballArray = new BallArray(this);
        starArray = new StarArray(this);
        bar = new ColorBallBar(ballArray);

        gameLogic = new GameLogic(this, ballArray, starArray);
        ballArray.constructSolutionBalls(gameLogic.colorSet);

        //countdown
        cd = new Countdown(game.options.getTurnTime(), game.manager);

        //Important: Initialize Button after countdown!!!
        confirmButton = new MyButton(this);
        playAgainButton = new PlayAgainButton(this);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if (!ballArray.hasGrey() && !gameLogic.isGameOver()) {
            confirmButton.setVisible(true);
            confirmButton.setTouchable(Touchable.enabled);
        }

        if (game.options.isTimer()){
           if(cd.timeIsUp(delta)){
               ballArray.fillBalls();
               confirmButton.pressButton();
           }
        }

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (game.options.isTimer())
            cd.draw(stage.getBatch(), game.res.x / 36 ,
                    2.5f * ballArray.getArrayOfLines().get(0).getBallLine().get(0).getHeight());

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
        stage.dispose();
    }
}
