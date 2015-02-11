package be.kdg.dots.controller;

import be.kdg.dots.model.*;
import be.kdg.dots.view.GUIStartScreen;
import be.kdg.dots.view.GUIView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexander on 4/02/2015.
 */
public class SpelController{
    private Veld veld;
    private Highscore highscore;
    private javax.swing.Timer timer;
    private GUIStartScreen guiStartScreen;
    private GUIView guiView;

    //Timer attributen
    private static final int MAX_AANTAL_SECONDEN = 45;
    private int aantalSeconden = MAX_AANTAL_SECONDEN;

    public SpelController() {
        veld = new Veld(6, 6);
        highscore = new Highscore();
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aantalSeconden == 1) { //aantalSeconden moet 1 zijn omdat stopTimer() deze methode nog eens triggerd
                    timer.stop();
                }
                guiView.updateTimer(--aantalSeconden);
                //aantalSeconden--;

                System.out.println("Time: " + aantalSeconden); //DEBUG INFO
            }
        });
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

    public void startSpel(){
        guiView = new GUIView(this);
        timer.start();
    }

}
