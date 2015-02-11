package be.kdg.dots.model;

import be.kdg.dots.view.GUIView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jens on 10-2-2015.
 */

//TODO: Niet meer nodig, zit nu in spelcontroller class

public class Timer {
    private static int aantalSeconden;
    private static final int MAX_AANTAL_SECONDEN = 45;
    private javax.swing.Timer timer;

    public Timer() {
        aantalSeconden = MAX_AANTAL_SECONDEN;
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aantalSeconden == 1) { //aantalSeconden moet 1 zijn omdat stopTimer() deze methode nog eens triggerd
                    stopTimer();
                }
                aantalSeconden--;

                System.out.println("Time: " + aantalSeconden); //DEBUG INFO
            }
        });
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resetTimer() {
        aantalSeconden = MAX_AANTAL_SECONDEN;
    }

    public int getAantalSeconden() {
        return aantalSeconden;
    }

    public int getMaxAantalSeconden() {
        return MAX_AANTAL_SECONDEN;
    }


}
