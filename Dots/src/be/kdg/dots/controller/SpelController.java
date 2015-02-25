package be.kdg.dots.controller;

import be.kdg.dots.model.highscore.HighScoreIO;
import be.kdg.dots.model.highscore.Highscore;
import be.kdg.dots.model.speler.Level;
import be.kdg.dots.model.speler.Score;
import be.kdg.dots.model.speler.Speler;
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
    private Veld veld;
    private Highscore highscore;
    private Speler speler;
    private Score score;
    private Level level;
    private Timer timer;
    private GUIHoofdMenu guiHoofdMenu;
    private GUISpel guiSpel;
    private GUIFrame guiFrame;

    //Timer attributen
    private static final int MAX_AANTAL_SECONDEN = 45;
    private int aantalSeconden;

    public SpelController() {
        veld = new Veld(6, 6, this);
        highscore = new Highscore(this);
        guiHoofdMenu = new GUIHoofdMenu(this);
        guiSpel = new GUISpel(this);
        guiFrame = new GUIFrame(this);
        speler = new Speler(this, "Jens");
        guiFrame.getContentPane().add("hoofdMenu", guiHoofdMenu);
        guiFrame.getContentPane().add("startSpel", guiSpel);
        level = new Level(speler);
        //guiFrame.getCl().addLayoutComponent(GUISpel, "guiSpel");
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

    public GUIFrame getGuiFrame() {
        return guiFrame;
    }

    public Highscore getHighscore() {
        return highscore;
    }

    public Level getlevel() {
        return level;
    }

    public void setLevel(int lvl) {
        level.setLevel(lvl);
    }

    public void setscoreDoel(int lvl) {
        score.setScoreDoel(lvl);
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

    public void stopTimer() {
        timer.stop();
    }

    public void startTimer() {
        timer.start();
    }

    public int getAantalSeconden() {
        return aantalSeconden;
    }

    public void startSpel(String modus) {
        switch (modus) {
            case "Time":
                aantalSeconden = MAX_AANTAL_SECONDEN;
                guiSpel.updateTimer(aantalSeconden);
                guiSpel.updateLevel(level.getLevel());
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        guiSpel.updateTimer(--aantalSeconden);

                        /*else{
                            //JOptionPane.showMessageDialog(null, "Je hebt het level niet gehaald", "InfoBox: " + "Lose", JOptionPane.INFORMATION_MESSAGE);
                            getGuiSpel().eindigSpel();
                            speler.getScore().setScoreDoel(level.getLevel());
                        }*/
                        if (aantalSeconden == 0) {
                            timer.stop(); //actionPerformed wordt nog eens getriggerd als timer.stop(); wordt aangeroepen!
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
        }
        veld = new Veld(6, 6, this);
        guiSpel.setModus(modus);

        guiFrame.updateFrame("startSpel");
        speler.getScore().resetScore();
        timer.start();
    }

    public void checkScore() {

        if (speler.getScore().controlScore(getSpeler().getScore().getScore())) {
            setLevel(getlevel().getLevel() + 1);
            speler.getScore().setScoreDoel(level.getLevel());
            guiSpel.updateLevel(level.getLevel());
            //JOptionPane.showMessageDialog(null, "Proficiat! Op naar level " + level.getLevel(), "InfoBox: " + "Win", JOptionPane.INFORMATION_MESSAGE);
            //startSpel("Time");
        }
    }
}
