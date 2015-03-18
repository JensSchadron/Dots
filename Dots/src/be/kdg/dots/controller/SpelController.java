package be.kdg.dots.controller;

import be.kdg.dots.model.highscore.Highscore;
import be.kdg.dots.model.settings.Settings;
import be.kdg.dots.model.settings.UpdateChecker;
import be.kdg.dots.model.speler.Speler;
import be.kdg.dots.model.veld.Spel;
import be.kdg.dots.model.veld.Veld;
import be.kdg.dots.view.GUIHoofdMenu;

public class SpelController {
    private final Settings settings;
    private Veld veld;
    private final Highscore highscore;
    private final Speler speler;
    private final GUIHoofdMenu guiHoofdMenu;
    private final Spel spel;
    private boolean ladenCompleet = false;

    public GUIHoofdMenu getGuiHoofdMenu() {
        return guiHoofdMenu;
    }

    public SpelController() {

        settings = new Settings(this);
        veld = new Veld(settings.getRow(), settings.getColumn(), this);
        highscore = new Highscore(this);
        guiHoofdMenu = new GUIHoofdMenu(this);

        spel = new Spel(this);
        speler = new Speler(this, null);

        settings.loadSettings();

        ladenCompleet = true;

        if(UpdateChecker.checkForUpdates(settings.getLaatstGeupdate())){
            settings.setLaatstGeupdate(UpdateChecker.getLatestUpdateTime());
            guiHoofdMenu.getGuiFrame().toonUpdateDialog();
        }
    }

    public Veld getVeld() {
        return veld;
    }

    public Spel getSpel() {
        return spel;
    }

    public Highscore getHighscore() {
        return highscore;
    }

    public Speler getSpeler() {
        return speler;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setNewVeld() {
        veld = new Veld(settings.getRow(), settings.getColumn(), this);
    }

    public void startSpel(String modus) {
        spel.startSpel(modus);
        this.veld = new Veld(settings.getRow(), settings.getColumn(), this);
    }

    public boolean isLadenCompleet() {
        return ladenCompleet;
    }
}
