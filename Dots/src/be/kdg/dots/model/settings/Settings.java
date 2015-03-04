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
        this.column = 6;
        this.row = 6;
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
        settingsIO = new SettingsIO(controller);
        settingsIO.readProperties();
    }

    public void saveSettings() {
        settingsIO.writeProperties(controller);
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

}
