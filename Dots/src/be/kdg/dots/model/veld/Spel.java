package be.kdg.dots.model.veld;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Spel {
    private final SpelController controller;
    private Timer timer;
    //Timer attributen
    private static final int MAX_AANTAL_SECONDEN = 45;
    private static final int MAX_AANTAL_MOVES = 30;
    private int aantalSeconden;
    private int aantalMoves;

    public Spel(SpelController controller) {
        this.controller = controller;
    }

    public void startSpel(String modus) {
        if (controller.getSpeler().getUsername() == null) {
            JOptionPane.showMessageDialog(null, "Gelieve u eerst in te loggen alvorens te spelen", "InfoBox: " + "Inloggen", JOptionPane.INFORMATION_MESSAGE);
        } else {
            switch (modus) {
                case "Time":
                    aantalSeconden = MAX_AANTAL_SECONDEN;
                    controller.getGuiHoofdMenu().getGuiSpel().updateTimerOrMoves(aantalSeconden);
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controller.getGuiHoofdMenu().getGuiSpel().updateTimerOrMoves(--aantalSeconden);
                            if (aantalSeconden == 0) {
                                timer.stop();
                                controller.getGuiHoofdMenu().getGuiSpel().eindigSpel(false);
                            }
                        }
                    });
                    break;
                case "Move":
                    aantalMoves = MAX_AANTAL_MOVES;
                    controller.getGuiHoofdMenu().getGuiSpel().updateTimerOrMoves(aantalMoves);
                    break;
                case "Infinity":
                    aantalSeconden = 0;
                    controller.getGuiHoofdMenu().getGuiSpel().updateTimerOrMoves(aantalSeconden);
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controller.getGuiHoofdMenu().getGuiSpel().updateTimerOrMoves(++aantalSeconden);
                        }
                    });
                    break;
            }
            controller.getGuiHoofdMenu().getGuiFrame().updateFrame("startSpel");
            controller.getSpeler().getScore().resetScore();
            if (!modus.equals("Move")) {
                timer.start();
            }
        }
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

    public void decrementMoves() {
        if (controller.getGuiHoofdMenu().getGuiSpel().getModus().equals("Move")) {
            if (aantalMoves == 0) {
                controller.getGuiHoofdMenu().getGuiSpel().eindigSpel(false);
            }
            controller.getGuiHoofdMenu().getGuiSpel().updateTimerOrMoves(--aantalMoves);
        }
    }
}
