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

    public Settings(SpelController controller) {
        this.controller = controller;
        settingsIO = new SettingsIO(this);
        this.column = 3;
        this.row = 3;
        this.backgroundColor = Color.white;

        loadSettings();
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

    public void loadSettings(){

    }

    public void saveSettings(){

    }
}
