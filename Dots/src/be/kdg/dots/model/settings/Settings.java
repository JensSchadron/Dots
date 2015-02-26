package be.kdg.dots.model.settings;

import be.kdg.dots.controller.SpelController;

/**
 * Created by Jens on 23-2-2015.
 */
public class Settings {
    private SpelController controller;
    private SettingsIO settingsIO;
    private int colum;
    private int row;

    public Settings(SpelController controller) {
        this.controller = controller;
        settingsIO = new SettingsIO(this);
        this.colum = 6;
        this.row = 6;
    }

    public int getColum() {
        return colum;
    }

    public int getRow() {
        return row;
    }
}
