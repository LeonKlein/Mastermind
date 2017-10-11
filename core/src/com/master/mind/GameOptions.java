package com.master.mind;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Leon on 08.10.2017.
 */
/* This class contains the options the user can set in the Menu. The options are set in the
Menu Screen and retrieved in the Play Screen for different behavior.
 */
public class GameOptions {
    private boolean isTimer;
    private boolean isPermute;
    private int turnTime;

    private Preferences prefs;

    GameOptions(){
        setPermute(false);
        prefs = Gdx.app.getPreferences("MyPreferences");
    }

    public void setPermute(boolean permute) {
        isPermute = permute;
    }

    void setTimer(boolean timer) {
        isTimer = timer;
    }

    private void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    public int getTurnTime() {
        return turnTime;
    }

    boolean isPermute() {
        return isPermute;
    }

    public boolean isTimer() {
        return isTimer;
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public void difficulty(String grade){
        prefs.putString("Difficulty", grade);
        prefs.flush();

        if (grade.equals("easy")) {
            setTimer(false);
            setTurnTime(0);
        }
        else if (grade.equals("medium")) {
            Gdx.app.log("Difficulty", "medium");
            setTimer(true);
            setTurnTime(30);
        }
        else if (grade.equals("hard")){
            setTimer(true);
            setTurnTime(15);
        }
        else{
            setTimer(true);
            setTurnTime(7);
        }
    }

}
