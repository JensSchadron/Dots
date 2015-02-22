package be.kdg.dots.model.speler;

import java.util.ArrayList;

/**
 * Created by Jens on 19-2-2015.
 */
public class Score {
    private Speler speler;
    private int score;

    public Score(Speler speler) {
        this.speler = speler;
        this.score = 0;
    }

    public void berekenScore(ArrayList<Integer> connectedDots) {
        score += Math.pow(connectedDots.size(), 2);
        System.out.println("Debug info - Score: " + score);
    }

    public int getScore() {
        return score;
    }

    public void resetScore(){
        score = 0;
        speler.getController().getGuiSpel().updateScore(score);
    }
}
