package be.kdg.dots.model.highscore;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class HighScoreIO {
    private Path filePath;
    private Highscore highscore;

    HighScoreIO(Highscore highscore) {
        this.highscore = highscore;
        try {
            this.filePath = Paths.get(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString(), "highscores.txt");
        } catch (URISyntaxException e) {
            highscore.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het ophalen van het highscore bestand.", true);
        }
        loadHighScores();
    }

    void saveHighScores(ArrayList<String> decodedHighScores) {
        ArrayList<String> encodedHighScores = new ArrayList<>();
        for (int i = 0; i < decodedHighScores.size(); i++) {
            encodedHighScores.add(i, encodeHighScore(decodedHighScores.get(i)));
        }
        try {
            Files.write(filePath, encodedHighScores, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            highscore.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het opslaan van het highscore bestand.", true);
        }
    }

    ArrayList<String> loadHighScores() {
        ArrayList<String> decodedHighScores = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            decodedHighScores.add("");
        }
        if (Files.exists(filePath)) {
            List<String> encodedHighScores;
            try {
                encodedHighScores = Files.readAllLines(filePath);
                for (int i = 0; i < encodedHighScores.size(); i++) {
                    decodedHighScores.set(i, decodeHighScore(encodedHighScores.get(i)));
                }
            } catch (IOException e) {
                highscore.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het inlezen van het highscore bestand.", false);
            }
        } else {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                highscore.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het creëren van een nieuw highscore bestand.", true);
            }
        }
        return decodedHighScores;
    }

    private String encodeHighScore(String decodedHighScores) {
        return Base64.getEncoder().encodeToString(decodedHighScores.getBytes());
    }

    private String decodeHighScore(String encodedHighScores) {
        String decodedString;
        try {
            decodedString = new String(Base64.getDecoder().decode(encodedHighScores));
        } catch (IllegalArgumentException e) {
            highscore.getController().getGuiHoofdMenu().getGuiFrame().toonFoutBoodschap("Er is iets foutgelopen bij het decoderen van de highscore bestand.", true);
            return "";
        }
        return decodedString;
    }
}

