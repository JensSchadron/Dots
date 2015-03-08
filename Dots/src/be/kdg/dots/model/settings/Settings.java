package be.kdg.dots.model.settings;

import be.kdg.dots.controller.SpelController;

import java.awt.*;

/**
 * Created by Jens on 23-2-2015.
 */
public class Settings {
    private SpelController controller;
    private SettingsIO settingsIO;
    private int column;
    private int row;
    private Color backgroundColor;
    private boolean hintsEnabled;
    private int hintVertraging;

    public Settings(SpelController controller) {
        this.controller = controller;
        this.column = 6;
        this.row = 6;
        this.hintVertraging = 1000;
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
