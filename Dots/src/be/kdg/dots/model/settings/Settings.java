package be.kdg.dots.model.settings;

import be.kdg.dots.controller.SpelController;
import java.awt.*;
import java.time.LocalDateTime;

public class Settings {
    private final SpelController controller;
    private SettingsIO settingsIO;
    private int column;
    private int row;
    private Color backgroundColor;
    private boolean hintsEnabled;
    private int hintVertraging;
    private String achievements;
    private LocalDateTime laatstGeupdate;

    public Settings(SpelController controller) {
        this.controller = controller;
        this.column = 6;
        this.row = 6;
        this.hintsEnabled = true;
        this.hintVertraging = 1000;
    }

    public LocalDateTime getLaatstGeupdate() {
        return laatstGeupdate;
    }

    public void setLaatstGeupdate(LocalDateTime laatstGeupdate) {
        this.laatstGeupdate = laatstGeupdate;
    }

    public boolean isHintsEnabled() {
        return hintsEnabled;
    }

    public void setHintsEnabled(boolean hintsEnabled) {
        this.hintsEnabled = hintsEnabled;
    }

    public int getHintVertraging() {
        return hintVertraging;
    }

    public void setHintVertraging(int hintVertraging) {
        this.hintVertraging = hintVertraging;
    }

    public String getAchievements() {
        if (achievements == null) {
            return "";
        }
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public void addAchievements(String achievements) {
        String afkorting = "";
        achievements = achievements.replace("BLUE", "Blauw").replace("RED", "Rood").replace("GREEN", "Groen").replace("YELLOW", "Geel").replace("PURPLE", "Paars");
        switch (achievements) {
            case "Missing common sense...":
                afkorting = "mcs";
                break;

            case "Geel domineert het spel!":
                afkorting = "ged";
                break;
            case "Paars domineert het spel!":
                afkorting = "pad";
                break;
            case "Blauw domineert het spel!":
                afkorting = "bld";
                break;
            case "Groen domineert het spel!":
                afkorting = "grd";
                break;
            case "Rood domineert het spel!":
                afkorting = "rod";
                break;
            case "Geel is uitgeroeid!":
                afkorting = "geu";
                break;
            case "Paars is uitgeroeid!":
                afkorting = "pau";
                break;
            case "Blauw is uitgeroeid!":
                afkorting = "blu";
                break;
            case "Groen is uitgeroeid!":
                afkorting = "gru";
                break;
            case "Rood is uitgeroeid!":
                afkorting = "rou";
                break;
        }
        if ((this.achievements == null || this.achievements.isEmpty()) || !this.achievements.contains(afkorting)) {
            if (this.achievements == null) {
                this.achievements = afkorting + ";";
            } else {
                this.achievements += afkorting + ";";
            }
            controller.getGuiHoofdMenu().getGuiFrame().toonAchievement(achievements);
            saveSettings();
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        controller.getGuiHoofdMenu().setBackgroundColor(this.backgroundColor);
    }

    public void loadSettings() {
        settingsIO = new SettingsIO(this);
        settingsIO.readProperties();
    }

    public void saveSettings() {
        settingsIO.writeProperties(this);
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public SpelController getController() {
        return controller;
    }
}
