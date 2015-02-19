package be.kdg.dots.model.highscore;

import java.util.ArrayList;

/**
 * Created by Jens on 19-2-2015.
 */
public class Score {
    private Highscore highscore;
    private int score;

    public Score(Highscore highscore) {
        this.highscore = highscore;
        this.score = 0;
    }

    public void berekenScore(ArrayList<Integer> connectedDots) {
        score += Math.pow(connectedDots.size(), 2);
        highscore.getController().getGuiSpel().updateScore(score);
        System.out.println("Debug info - Score: " + score);
    }

    public int getScore() {
        return score;
    }
}
