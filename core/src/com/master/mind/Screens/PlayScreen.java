package com.master.mind.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.master.mind.Actors.Ball;
import com.master.mind.Actors.PermutableArray;
import com.master.mind.Countdown;
import com.master.mind.MasterMaster;
import com.master.mind.Actors.MyButton;
import com.master.mind.Actors.PlayAgainButton;
import com.master.mind.Actors.SelectColorBall;


/**
 * Created by Leon on 28.09.2017.
 */

public class PlayScreen implements Screen {
    public MasterMaster game;
    private Image background;
    private Stage stage;
    
    public Array<Ball> balls;
    private Array<SelectColorBall> bar;
    public Array<Ball> solutionBalls;
    private Array<Image> stars;
    private Array<Color> colors;
    private PermutableArray<Color> colorSet;

    private Color color_def;

    private MyButton confirmButton;
    //fixxxxx (und ganze win lose sache.....)))
    public PlayAgainButton playAgainButton;

    private Image winBanner;
    private Image loseBanner;


    private Texture ball;
    private Texture star;
    private Texture win;
    private Texture lose;
    private Texture playAgain;
    private Texture bg;

    public int line;
    public final int maxLine = 8; //should be 8 since code relies on number of lines + 1
    public int indexOfPressed;

    //
    private int permuteDirection = MathUtils.random(1);

    //countdown
    public Countdown cd;

    private boolean foundNotGrey;



    public PlayScreen(MasterMaster game) {
        this.game = game;
    }
    @Override
    public void show() {
        //line gehen von 0-7
        line = 0;

        //nothing pressed
        indexOfPressed = -1;

        //initialize color array
        colors = new Array<Color>();
        colors.add(Color.CYAN);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.PINK);

        //initilize colorSet and solution Vector
        colorSet = new PermutableArray<Color>();
        solutionBalls = new Array<Ball>(true, 4);

        //default color
        color_def = Color.LIGHT_GRAY;


        //initialze stage + background
        stage = new Stage(new ScreenViewport());

        bg = new Texture(Gdx.files.internal("gameAssets/background.png"));
        background = new Image(bg);
        background.setSize(game.res.x, game.res.y);
        background.setTouchable(Touchable.disabled);
        stage.addActor(background);


        ////// fixxxxxxxxxxxxxxxxxxxxx!!!!!!!!!!!!!!!!!!!! number of stars index....
        star = new Texture(Gdx.files.internal("gameAssets/whiteStar.png"));
        //initialize stars
        stars = new Array<Image>(true, 36);
        for (int i = 0; i < 36; i++) {
            stars.add(new Image(star));
            stage.addActor(stars.get(i));
            stars.get(i).setSize(game.res.x / 6f / 2.6f, game.res.x / 6f / 2.6f); //quadratic
            if (i % 4 < 2)
                stars.get(i).setPosition((i % 2) * stars.get(i).getHeight() + game.res.x * 0.02f, game.res.y / 16f * ((4f + 0.08f) + i / 4 * 1.5f)); //#physik
            else
                stars.get(i).setPosition((i % 2) * stars.get(i).getHeight() + game.res.x * 0.02f, game.res.y / 16f * ((4f + 0.08f) + i / 4 * 1.5f) + stars.get(i).getHeight());
            stars.get(i).setVisible(false);
        }
        ////// fixxxxxxxxxxxxxxxxxxxxx!!!!!!!!!!!!!!!!!!!!


        //initialize balls
        ball = new Texture(Gdx.files.internal("gameAssets/white.png"));
        balls = new Array<Ball>(true, 36);
        for (int i = 0; i < 36; i++) {
            balls.add(new Ball(ball, this));
            stage.addActor(balls.get(i));
            balls.get(i).setSize(game.res.y / 12.5f, game.res.y / 12.5f); //quadratic
            balls.get(i).setPosition(game.res.x / 9f * (1.5f + 1.875f * (i % 4) + 0.3f), game.res.y / 16f * ((4f + 0.08f) + i / 4 * 1.5f)); //#physik
            balls.get(i).setColor(color_def);
            if (i >= 4) balls.get(i).setVisible(false);
        }


        //confirmButton to continue
        confirmButton = new MyButton(new Texture(Gdx.files.internal("gameAssets/button2.png")), this);
        confirmButton.setSize(game.res.x, game.res.y / 8f);
        confirmButton.setPosition(0, balls.get(0).getHeight() + 20f); //scheis hardcoded kack
        confirmButton.setVisible(false);
        stage.addActor(confirmButton);

        //initialize colored balls bar
        bar = new Array<SelectColorBall>(true, 6);
        for (int i = 0; i < 6; i++) {
            bar.add(new SelectColorBall(ball, this));
            stage.addActor(bar.get(i));
            bar.get(i).setSize(game.res.y / 12.5f, game.res.y / 12.5f); //quadratic
            bar.get(i).setPosition(game.res.x / 6.2f * (i + 0.15f), game.res.y / 16f * 0.08f);
            bar.get(i).setColor(colors.get(i).r, colors.get(i).g, colors.get(i).b, colors.get(i).a);
        }

        //initilize random colorset
        for (int i = 0; i < 4; i++) {
            int randomNum = MathUtils.random(5);
            while (colorSet.contains(colors.get(randomNum), true)) {
                randomNum = MathUtils.random(5);
            }
            Gdx.app.log("RandomNum:", String.format("%d", randomNum));
            colorSet.add(colors.get(randomNum));
            solutionBalls.add(new Ball(ball, this));
            stage.addActor(solutionBalls.get(i));
            solutionBalls.get(i).setColor(colorSet.get(i));
            solutionBalls.get(i).setSize(balls.get(0).getWidth(), balls.get(0).getHeight());
            solutionBalls.get(i).setPosition(game.res.x / 9f * (1.5f + 1.875f * i + 0.3f), balls.get(0).getHeight() + game.res.y / 16f);
            //solutionBalls.get(i).setPosition(game.res.x / 5f * (i + 0.5f) , balls.get(0).getHeight() + game.res.y / 16f * 0.4f);
            solutionBalls.get(i).setVisible(false);
        }
        //initialize Textures of win/ lose
        win = new Texture(Gdx.files.internal("gameAssets/win.png"));
        lose = new Texture(Gdx.files.internal("gameAssets/lose.png"));
        winBanner = new Image(win);
        loseBanner = new Image(lose);
        stage.addActor(winBanner);
        winBanner.setVisible(false);
        stage.addActor(loseBanner);
        loseBanner.setVisible(false);

        loseBanner.setSize(game.res.x / 2, game.res.y / 8);
        loseBanner.setOrigin(loseBanner.getWidth() / 2, loseBanner.getHeight() / 2);
        loseBanner.setPosition(game.res.x / 4, game.res.y / 2);
        winBanner.setPosition(0, game.res.y);
        winBanner.setSize(game.res.x, game.res.y / 4);

        //playAgainButton
        playAgain = new Texture(Gdx.files.internal("gameAssets/playAgain.png"));
        playAgainButton = new PlayAgainButton(playAgain, game);
        stage.addActor(playAgainButton);
        playAgainButton.setVisible(false);
        playAgainButton.setPosition(0, 0);
        playAgainButton.setSize(game.res.x, balls.get(0).getHeight() * 1.5f);

        //countdown
        cd = new Countdown(game.turnTime);

        Gdx.input.setInputProcessor(stage);
    }



    public boolean computeStars() {

        int countRed = 0;
        int countWhite = 0;
        int countToFour = 0;
        for (int i = line * 4; i < 4 * (line + 1); i++) {
            if (balls.get(i).getColor().equals(colorSet.get(i % 4)))
                countRed += 1;
            else if (colorSet.contains(balls.get(i).getColor(), false)) {
                countWhite += 1;
            }
        }
        for (int i = 0; i < countRed; i++) {
            stars.get((line + 1) * 4 - i - 1).setVisible(true);
            stars.get((line + 1) * 4 - i - 1).setTouchable(Touchable.disabled);
            stars.get((line + 1) * 4 - i - 1).setColor(Color.RED);
            countToFour += 1;
        }
        for (int i = 0; i < countWhite; i++) {
            stars.get((line + 1) * 4 - i - 1 - countToFour).setVisible(true);
            stars.get((line + 1) * 4 - i - 1 - countToFour).setTouchable(Touchable.disabled);
        }

        if (game.isPermute){
            switch (permuteDirection){
                case 0:
                    Gdx.app.log("permute", "left");
                    colorSet.permuteLeft();
                    break;
                case 1:
                    Gdx.app.log("permute", "Right");
                    colorSet.permuteRight();
                    break;
            }
        }

        if (countRed == 4) {
            youWin();
            return true;
        } else
            return false;
    }




    public void youLose() {
        loseBanner.setVisible(true);
        ScaleByAction sba = new ScaleByAction();
        sba.setAmount(1f);
        sba.setDuration(1f);
        loseBanner.addAction(sba);
        game.isTimer = false;
    }

    public void youWin() {
        winBanner.setVisible(true);
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(0, -game.res.y / 2);
        moveToAction.setDuration(5f);
        winBanner.addAction(moveToAction);
        game.isTimer = false;
    }

    private void fillBalls(){
        Array<Color> usedColors = new Array<Color>(true, 4);
        for (int i = 4 * line; i < 4 * (line +1); i++) {
            usedColors.add(balls.get(i).getColor());
        }
        for (int i = 4 * line; i < 4 * (line +1); i++) {
            if (balls.get(i).getColor().equals(color_def)){
                int randomNum = MathUtils.random(5);
                while (usedColors.contains(colors.get(randomNum), false)) {
                    randomNum = MathUtils.random(5);
                }
                Gdx.app.log("RandomNum:", String.format("%d", randomNum));
                balls.get(i).setColor(colors.get(randomNum));
                usedColors.add(colors.get(randomNum));
            }
        }

    }


    @Override
    public void render(float delta) {
        for (int i = line * 4; i < 4 * (line + 1); i++) {
            foundNotGrey = false;
            if (balls.get(i).getColor().equals(Color.LIGHT_GRAY)) break;
            foundNotGrey = true;

        }
        if (foundNotGrey && line != maxLine) {
            confirmButton.setVisible(true);
            confirmButton.setTouchable(Touchable.enabled);
        }

        if (game.isTimer){
           if(cd.countingDown(delta)){
               Gdx.app.log("RandomNum:", String.format("%3f", delta));
               fillBalls();
               confirmButton.pressButton();
           }
        }

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (game.isTimer)
            cd.draw(stage.getBatch(), game.res.x / 36 , 2.5f * balls.get(0).getHeight());

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
        bg.dispose();
        ball.dispose();
        star.dispose();
        win.dispose();
        lose.dispose();
        playAgain.dispose();

    }
}
/*
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       // boolean isWon;
        Vector2 coord = stage.screenToStageCoordinates(new Vector2((float)screenX,(float)screenY));
        Actor hitActor = (Actor) stage.hit(coord.x,coord.y, true);



        if(bar.contains((Ball) hitActor, true) && indexOfPressed != -1){
            balls.get(indexOfPressed).setColor(hitActor.getColor().r, hitActor.getColor().g, hitActor.getColor().b, hitActor.getColor().a);
            balls.get(indexOfPressed).isPressed = false;
            indexOfPressed = -1;
        }

        if (hitActor == confirmButton) {
            isWon = computeStars();
            line += 1;
            if (indexOfPressed != -1) {
                balls.get(indexOfPressed).isPressed = false;
                indexOfPressed = -1;
            }
            //this happens if you win during any state of game
            if(isWon) {
                youWin();
                for (int i = line * 4; i < 4 * (line + 1); i++) {
                    balls.get(i - 4).setTouchable(Touchable.disabled);
                    confirmButton.setVisible(false);
                    playAgainAnimation();
                }
            }
            //this happens if you reach the last row and dont make it
            else if(!isWon && line == maxLine){
                confirmButton.setVisible(false);
                youLose();
                for (int i = 0; i < 4; i++) {
                    solutionBalls.get(i).setVisible(true);
                    solutionBalls.get(i).setTouchable(Touchable.disabled);
                    balls.get((line - 1) * 4 + i).setTouchable(Touchable.disabled);
                    playAgainAnimation();
                }
                //this happens if you go to next row without losing
            } else {
                for (int i = line * 4; i < 4 * (line + 1); i++) {
                    balls.get(i).setVisible(true);
                    balls.get(i - 4).setTouchable(Touchable.disabled);
                    confirmButton.setVisible(false);
                }
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
*/