package be.kdg.dots.model.speler;

import be.kdg.dots.controller.SpelController;

public class Speler {
    private final SpelController controller;
    private String username = "";
    private final Score score;
    private final Level level;


    public Speler(SpelController controller, String username) {
        this.username = username;
        this.score = new Score(this);
        this.level = new Level(this);
        this.controller = controller;
    }

    public void setUsername(String username) {
        this.username = username;
        controller.getSettings().saveSettings();
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

    public Level getLevel(){
        return level;
    }


}
