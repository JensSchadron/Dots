package be.kdg.dots.model.highscore;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Jens on 10-2-2015.
 */
public class Highscore {
    private HighScoreIO highScoreIO;
    public Highscore() {
        highScoreIO = new HighScoreIO();
    }
}
