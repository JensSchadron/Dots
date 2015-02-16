package be.kdg.dots.model.highscore;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by Jens on 16-2-2015.
 */
public class HighScoreIO {
    private Path filePath;

    public HighScoreIO() {
        try {
            this.filePath = Paths.get(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString(), "highscores.txt");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println(filePath);

        //Encode / decode highscores
        String originalString = "Hallo";
        System.err.println(originalString);

        /*if(Files.exists(filePath)) {
            try {
                highScores = Files.readAllLines(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }

    public void saveHighScores(String highScores) {
        //Files.write(filePath, encodeHighScore(highScores), Charset.defaultCharset());
    }

    public String loadHighScores() {
        List<String> encodedHighScores = new ArrayList<>();
        try {
            encodedHighScores = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodeHighScore(encodedHighScores.get(0));
    }

    protected String encodeHighScore(String decodedHighScores) {
        return Base64.getEncoder().encodeToString(decodedHighScores.getBytes());
    }

    protected String decodeHighScore(String encodedHighScores) {
        String decodedString = "";
        try {
            decodedString = new String(Base64.getDecoder().decode(encodedHighScores));
        } catch (IllegalArgumentException e) {
            decodedString = "You tried to cheat, you little bastard! :p";
        }
        return decodedString;
    }
}

