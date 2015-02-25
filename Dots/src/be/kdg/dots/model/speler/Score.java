package be.kdg.dots.model.speler;

import java.util.ArrayList;

/**
 * Created by Jens on 19-2-2015.
 */
public class Score {
    private Speler speler;
    private int score;
    private int scoreDoel;

    public Score(Speler speler) {
        this.speler = speler;
        this.score = 0;
        scoreDoel = 100;
    }

    public void berekenScore(ArrayList<Integer> connectedDots) {
        score += Math.pow(connectedDots.size(), 2);
        System.out.println("Debug info - Score: " + score);
    }

    public void setScoreDoel(int level) {
        scoreDoel = 100 * level;
    }

    public int getScore() {
        return score;
    }

    public int getScoreDoel() {
        return scoreDoel;
    }

    public void resetScore() {
        score = 0;
        speler.getController().getGuiSpel().updateScore(score, scoreDoel);
    }

    public boolean controlScore(int achievedScore) {
        if (achievedScore >= scoreDoel) {
            return true;
        } else {
            return false;
        }
    }
}
