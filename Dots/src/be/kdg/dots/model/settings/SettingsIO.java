package be.kdg.dots.model.settings;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.speler.Speler;
import be.kdg.dots.model.veld.Spel;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jens on 19-2-2015.
 */
public class SettingsIO {
    private Settings settings;
    private Path settingsPath;
    private Properties properties;
    private Speler speler;
    private SpelController controller;

    public SettingsIO(SpelController controller) {
        this.settings = controller.getSettings();
        this.speler = controller.getSpeler();
        this.controller = controller;
        try {
            settingsPath = Paths.get(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString(), "settings.dat");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void writeProperties(SpelController controller) {
        this.settings = controller.getSettings();
        this.speler = controller.getSpeler();
        try (FileOutputStream out = new FileOutputStream(settingsPath.toString())) {
            properties = new Properties();
            if (speler.getUsername() != null) {
                properties.setProperty("username", speler.getUsername());
            }
            // if (settings.getBackgroundColor() != null) {
            //String hex = "#" + Integer.toHexString(settings.getBackgroundColor().getRGB()).substring(2);
            //properties.setProperty("background", hex);
            // }
            properties.setProperty("column", Integer.toString(settings.getColumn()));
            properties.setProperty("row", Integer.toString(settings.getRow()));
            properties.setProperty("level", Integer.toString(speler.getLevel().getLevel()));
            properties.setProperty("score", Integer.toString(speler.getScore().getScore()));
            properties.setProperty("scoredoel", Integer.toString(speler.getScore().getScoreDoel()));
            properties.storeToXML(out, "Application properties");

        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void readProperties() {
        try (FileInputStream in = new FileInputStream(settingsPath.toString())) {
            properties = new Properties();
            properties.loadFromXML(in);
            properties.list(System.out);
            settings.setRow(Integer.parseInt(properties.getProperty("row")));
            settings.setColumn(Integer.parseInt(properties.getProperty("column")));
            if (properties.getProperty("username") != null) {
                speler.setUsername((properties.getProperty("username")));
            }
            if (properties.getProperty("level") != null) {
                speler.getLevel().setLevel(Integer.parseInt(properties.getProperty("level")));
            }
            controller.setNewVeld();
            if (properties.getProperty("background") != null) {
                String color = properties.getProperty("background");
                controller.getSettings().setBackgroundColor(new Color(Integer.valueOf(color.substring(1, 3), 16), Integer.valueOf(color.substring(3, 5), 16), Integer.valueOf(color.substring(5, 7), 16)));
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
    }

}
