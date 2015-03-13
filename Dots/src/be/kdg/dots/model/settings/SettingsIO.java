package be.kdg.dots.model.settings;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.speler.Speler;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

class SettingsIO {
    private Settings settings;
    private Path settingsPath;
    private Speler speler;
    private final SpelController controller;

    public SettingsIO(Settings settings) {
        this.settings = settings;
        this.speler = settings.getController().getSpeler();
        this.controller = settings.getController();
        try {
            settingsPath = Paths.get(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString(), "settings.dat");
        } catch (URISyntaxException e) {
            settings.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het creëren van het path van het settings bestand.", true);
        }
    }

    public void writeProperties(Settings settings) {
        this.settings = settings;
        this.speler = settings.getController().getSpeler();
        try (FileOutputStream out = new FileOutputStream(settingsPath.toString())) {
            Properties propertiesWrite = new Properties();
            if (speler.getUsername() != null && !speler.getUsername().isEmpty()) {
                propertiesWrite.setProperty("username", speler.getUsername());
            }
            if (settings.getBackgroundColor() != null) {
                String hex = "#" + Integer.toHexString(settings.getBackgroundColor().getRGB()).substring(2);
                propertiesWrite.setProperty("background", hex);
            }
            propertiesWrite.setProperty("column", Integer.toString(settings.getColumn()));
            propertiesWrite.setProperty("row", Integer.toString(settings.getRow()));
            propertiesWrite.setProperty("level", Integer.toString(speler.getLevel().getLevel()));
            propertiesWrite.setProperty("score", Integer.toString(speler.getScore().getScore()));
            propertiesWrite.setProperty("scoredoel", Integer.toString(speler.getScore().getScoreDoel()));
            propertiesWrite.setProperty("hintsenabled", Boolean.toString(settings.isHintsEnabled()));
            propertiesWrite.setProperty("hintvertraging", Integer.toString(settings.getHintVertraging()));
            propertiesWrite.setProperty("achievements", settings.getAchievements());

            propertiesWrite.storeToXML(out, "Application properties");
        } catch (IOException e) {
            settings.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het wegschrijven van het settings bestand.", true);
        }
    }

    public void readProperties() {
        if (!Files.exists(settingsPath)) {
            return;
        }
        try (FileInputStream in = new FileInputStream(settingsPath.toString())) {

            Properties propertiesRead = new Properties();
            propertiesRead.loadFromXML(in);

            settings.setRow(Integer.parseInt(propertiesRead.getProperty("row")));
            settings.setColumn(Integer.parseInt(propertiesRead.getProperty("column")));
            if (propertiesRead.getProperty("username") != null) {
                speler.setUsername((propertiesRead.getProperty("username")));
            }
            if (propertiesRead.getProperty("level") != null) {
                speler.getLevel().setLevel(Integer.parseInt(propertiesRead.getProperty("level")));
            }
            controller.setNewVeld();
            if (propertiesRead.getProperty("background") != null) {
                String color = propertiesRead.getProperty("background");
                controller.getSettings().setBackgroundColor(new Color(Integer.valueOf(color.substring(1, 3), 16), Integer.valueOf(color.substring(3, 5), 16), Integer.valueOf(color.substring(5, 7), 16)));
            }
            if(propertiesRead.getProperty("hintsenabled") != null) {
                settings.setHintsEnabled(Boolean.parseBoolean(propertiesRead.getProperty("hintsenabled")));
            } else {
                settings.setHintsEnabled(true);
            }
            if(propertiesRead.getProperty("hintvertraging") != null) {
                settings.setHintVertraging(Integer.parseInt(propertiesRead.getProperty("hintvertraging")));
            } else {
                settings.setHintVertraging(1000);
            }
            if(propertiesRead.getProperty("achievements") != null){
                settings.setAchievements(propertiesRead.getProperty("achievements"));
            } else {
                settings.setAchievements("");
            }
        } catch (IOException e) {
            settings.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het lezen van het settings bestand.", true);
        }
    }

}
