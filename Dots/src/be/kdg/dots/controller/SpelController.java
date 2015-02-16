package be.kdg.dots.controller;

import be.kdg.dots.model.highscore.Highscore;
import be.kdg.dots.model.veld.Veld;
import be.kdg.dots.view.GUIFrame;
import be.kdg.dots.view.GUIHoofdMenu;
import be.kdg.dots.view.GUISpel;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexander on 4/02/2015.
 */
public class SpelController{
    private Veld veld;
    private Highscore highscore;
    private Timer timer, debugTimer;
    private GUIHoofdMenu guiHoofdMenu;
    private GUISpel guiSpel;
    private GUIFrame guiFrame;

    //Timer attributen
    private static final int MAX_AANTAL_SECONDEN = 45;
    private int aantalSeconden;

    public SpelController() {
        veld = new Veld(6, 6);
        highscore = new Highscore();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aantalSeconden == 1) { //aantalSeconden moet 1 zijn omdat stopTimer() deze methode nog eens triggerd
                    timer.stop();
                }
                guiSpel.updateTimer(--aantalSeconden);
                System.out.println("Debug info - Time: " + aantalSeconden);
            }
        });
        guiFrame = new GUIFrame(this, new GUIHoofdMenu(this));

        /*debugTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.printf("Width: %d - Height: %d\n",guiSpel.getWidth(),guiSpel.getHeight());
            }
        });*/
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
        aantalSeconden = MAX_AANTAL_SECONDEN;
        guiFrame = new GUIFrame(this, new GUISpel(this));
        timer.start();
        //debugTimer.start();
    }

}
