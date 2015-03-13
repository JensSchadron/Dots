package be.kdg.dots.model.speler;

import java.util.ArrayList;

public class Score {
    private final Speler speler;
    private int score=0;
    private int scoreDoel=0;

    public Score(Speler speler) {
        this.speler = speler;
        this.score = 0;
        scoreDoel = 100;
    }

    public void berekenScore(ArrayList<Integer> connectedDots) {
        score += Math.pow(connectedDots.size(), 2);
        while (controlScore(score)) {
            speler.getLevel().incrementLevel();
            setScoreDoel(speler.getLevel().getLevel());
        }
    }

    void setScoreDoel(int level) {
        scoreDoel = 100 * level;
    }

    public int getScore() {
        return score;
    }

    public int getScoreDoel() {
        return scoreDoel;
    }

    public void resetScore() {
        speler.getLevel().resetLevel();
        score = 0;
        setScoreDoel(speler.getLevel().getLevel());
        speler.getController().getGuiHoofdMenu().getGuiSpel().updateScore(score, scoreDoel);
    }

    private boolean controlScore(int achievedScore) {
        return achievedScore >= scoreDoel;
    }
}
