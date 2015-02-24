package be.kdg.dots.model.speler;

import be.kdg.dots.controller.SpelController;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;

import java.util.ArrayList;

/**
 * Created by Jens on 19-2-2015.
 */
public class Speler {
    private SpelController controller;
    private String username;
    private Score score;


    public Speler(SpelController controller, String username) {
        this.username = username;
        this.score = new Score(this);
        this.controller = controller;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public SpelController getController() {
        return controller;
    }

    public Score getScore() {
        return score;
    }


}
