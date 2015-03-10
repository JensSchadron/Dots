package be.kdg.dots.model.highscore;

import be.kdg.dots.model.exception.DotsException;

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

public class HighScoreIO {
    private Path filePath;

    protected HighScoreIO() {
        try {
            this.filePath = Paths.get(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString(), "highscores.txt");
        } catch (URISyntaxException e) {
            throw new DotsException("Er is een fout opgetreden bij het ophalen van het highscore bestand.");
        }
        loadHighScores();
    }

    protected void saveHighScores(ArrayList<String> decodedHighScores) {
        ArrayList<String> encodedHighScores = new ArrayList<>();
        for (int i = 0; i < decodedHighScores.size(); i++) {
            encodedHighScores.add(i, encodeHighScore(decodedHighScores.get(i)));
        }
        try {
            Files.write(filePath, encodedHighScores, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new DotsException("Er is een fout opgetreden bij het opslaan van het highscore bestand.");
        }
    }

    protected ArrayList<String> loadHighScores() {
        ArrayList<String> decodedHighScores = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            decodedHighScores.add("");
        }
        if (Files.exists(filePath)) {
            List<String> encodedHighScores = new ArrayList<>();
            try {
                encodedHighScores = Files.readAllLines(filePath);
            } catch (IOException e) {
                throw new DotsException("Er is een fout opgetreden bij het encoderen van de highscore bestand.");
            }

            for (int i = 0; i < encodedHighScores.size(); i++) {
                decodedHighScores.set(i, decodeHighScore(encodedHighScores.get(i)));
            }
        } else {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new DotsException("Er is een fout opgetreden bij het creëren van de highscore bestand.");
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
            decodedString = "You tried to cheat, you little bastard!";
            throw new DotsException("Er is iets foutgelopen bij het decoderen van de highscore bestand.");
        }
        return decodedString;
    }
}

