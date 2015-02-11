package be.kdg.dots.controller;

import be.kdg.dots.model.*;
import be.kdg.dots.view.GUIStartScreen;
import be.kdg.dots.view.GUIView;

/**
 * Created by alexander on 4/02/2015.
 */
public class SpelController {
    private Veld veld;
    private Highscore highscore;
    private Timer timer;
    private GUIStartScreen guiStartScreen;
    private GUIView guiView;
    //private int row=6;
    //private int colum=6;

    public SpelController() {
        veld = new Veld(6, 6);
        highscore = new Highscore();
        timer = new Timer();
        guiStartScreen = new GUIStartScreen(this);

    }

    public Veld getVeld() {
        return veld;
    }

    public int getRow() {
        return veld.getRow();
    }

    public int getColum() {
        return veld.getColum();
    }

    public Timer getTimer(){
        return this.timer;
    }

    public void startSpel(){
        guiView = new GUIView(this);
        timer.startTimer();
    }
}
