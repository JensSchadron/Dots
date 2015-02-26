package be.kdg.dots.controller;

import be.kdg.dots.model.highscore.Highscore;
import be.kdg.dots.model.settings.Settings;
import be.kdg.dots.model.speler.Level;
import be.kdg.dots.model.speler.Speler;
import be.kdg.dots.model.veld.Spel;
import be.kdg.dots.model.veld.Veld;
import be.kdg.dots.view.GUIFrame;
import be.kdg.dots.view.GUIHoofdMenu;
import be.kdg.dots.view.GUISpel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexander on 4/02/2015.
 */
public class SpelController {
    private Settings settings;
    private Veld veld;
    private Highscore highscore;
    private Speler speler;
    //private Timer timer;
    private GUIHoofdMenu guiHoofdMenu;
    private GUISpel guiSpel;
    private GUIFrame guiFrame;
    private Spel spel;

    //Timer attributen
    /*private static final int MAX_AANTAL_SECONDEN = 45;
    private static final int MAX_AANTAL_MOVES = 30;
    private int aantalSeconden;
    private int aantalMoves;*/

    public SpelController() {
        settings = new Settings(this);
        veld = new Veld(settings.getRow(), settings.getColum(), this);
        highscore = new Highscore(this);
        guiHoofdMenu = new GUIHoofdMenu(this);
        guiSpel = new GUISpel(this);
        spel = new Spel(this);
        guiFrame = new GUIFrame(this);
        speler = new Speler(this, null);
        guiFrame.getContentPane().add("hoofdMenu", guiHoofdMenu);
        guiFrame.getContentPane().add("startSpel", guiSpel);
    }

    public Veld getVeld() {
        return veld;
    }

    public Spel getSpel() {
        return spel;
    }

    public GUIFrame getGuiFrame() {
        return guiFrame;
    }

    public Highscore getHighscore() {
        return highscore;
    }

    public GUISpel getGuiSpel() {
        return guiSpel;
    }

    public Speler getSpeler() {
        return speler;
    }

    public void setSpeler(String username) {
        speler.setUsername(username);
    }

   /* public void stopTimer() {
        timer.stop();
    }

    public void startTimer() {
        timer.start();
    }

    public int getAantalSeconden() {
        return aantalSeconden;
    }*/

    public void startSpel(String modus){
        spel.startSpel(modus);
        this.veld = new Veld(settings.getRow(), settings.getColum(), this);
    }

   /* public void startSpel(String modus) {
        if (speler.getUsername()==null){
            JOptionPane.showMessageDialog(null, "Gelieve u eerst in te loggen alvorens te spelen", "InfoBox: " + "Inloggen", JOptionPane.INFORMATION_MESSAGE);
        }else{
            switch (modus) {
                case "Time":
                    aantalSeconden = MAX_AANTAL_SECONDEN;
                    guiSpel.updateTimer(aantalSeconden);
                    //guiSpel.updateLevel(level.getLevel());
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            guiSpel.updateTimer(--aantalSeconden);
                            if (aantalSeconden == 0) {
                                timer.stop(); //actionPerformed wordt nog eens getriggerd als timer.stop(); wordt aangeroepen!
                                //JOptionPane.showMessageDialog(null, "Proficiat! U hebt level " + level.getLevel() + " behaald", "InfoBox: " + "Winner", JOptionPane.INFORMATION_MESSAGE);
                                guiSpel.eindigSpel();
                            }
                            System.out.println("Debug info - Time: " + aantalSeconden);

                        }
                    });
                    break;
                case "Infinity":
                    aantalSeconden = 0;
                    guiSpel.updateTimer(aantalSeconden);
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            guiSpel.updateTimer(++aantalSeconden);
                            System.out.println("Debug info - Time: " + aantalSeconden);
                        }
                    });
                    break;
                case "Move":
                    aantalSeconden = 0;
                    guiSpel.updateTimer(aantalSeconden);
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            guiSpel.updateTimer(++aantalSeconden);
                            System.out.println("Debug info - Time: " + aantalSeconden);
                        }
                    });
                    break;
            }
            veld = new Veld(6, 6, this);
            guiSpel.setModus(modus);

            guiFrame.updateFrame("startSpel");
            speler.getScore().resetScore();
            timer.start();
        }
    }*/
}
