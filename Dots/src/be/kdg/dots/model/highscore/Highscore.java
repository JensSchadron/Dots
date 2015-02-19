package be.kdg.dots.model.highscore;

import be.kdg.dots.controller.SpelController;

/**
 * Created by Jens on 10-2-2015.
 */
public class Highscore {
    private SpelController controller;
    private Score score;
    private HighScoreIO highScoreIO;

    public Highscore(SpelController controller) {
        this.controller = controller;
        this.highScoreIO = new HighScoreIO();
        this.score = new Score(this);
    }

    public Score getScore(){
        return score;
    }

    public SpelController getController() {
        return controller;
    }
}
