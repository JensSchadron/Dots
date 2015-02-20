package be.kdg.dots.model.highscore;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.speler.Score;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jens on 10-2-2015.
 */
public class Highscore {
    private SpelController controller;
    private HighScoreIO highScoreIO;

    private ArrayList<String> timeHighScores;
    private ArrayList<String> moveHighScores;
    private ArrayList<String> infinityHighScores;

    public Highscore(SpelController controller) {
        this.controller = controller;
        this.highScoreIO = new HighScoreIO();

        loadHighScores();
    }

    public void addHighScore(String modus) {
        switch (modus) {
            case "Time":
                if (timeHighScores.size() == 0) {
                    timeHighScores.add(String.format("%-20s %11d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore()));
                    break;
                }
                for (int i = 0; i < timeHighScores.size(); i++) {
                    if (controller.getSpeler().getScore().getScore() > Integer.parseInt(timeHighScores.get(i).substring(20).trim())) {
                        timeHighScores.add(i, String.format("%-20s %11d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore()));
                    }
                }
                break;
            case "Move":
                if (moveHighScores.size() == 0) {
                    moveHighScores.add(String.format("%-20s %11d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore()));
                }
                for (int i = 0; i < moveHighScores.size(); i++) {
                    if (controller.getSpeler().getScore().getScore() > Integer.parseInt(moveHighScores.get(i).substring(20).trim())) {
                        moveHighScores.add(i, String.format("%-20s %11d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore()));
                    }
                }
                break;
            case "Infinity":
                for (int i = 0; i < infinityHighScores.size(); i++) {
                    if (controller.getSpeler().getScore().getScore() > Integer.parseInt(infinityHighScores.get(i).substring(21, 26).trim())) {
                        infinityHighScores.add(i, String.format("%-20s %5d %5d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore(), controller.getAantalSeconden()));
                    }
                }
                break;
        }
        saveHighScores();
    }

    private void loadHighScores() {
        ArrayList<String> decodedHighScores = highScoreIO.loadHighScores();
        System.out.println("Debug info - decodedHighScores.size(): " + decodedHighScores.size());
        System.out.println("Debug info - TimeHighScores: " + decodedHighScores.get(0));
        timeHighScores = new ArrayList<>(Arrays.asList(decodedHighScores.get(0).split("㏠")));
        System.out.println("Debug info - MoveHighScores: " + decodedHighScores.get(1));
        moveHighScores = new ArrayList<>(Arrays.asList(decodedHighScores.get(1).split("㏠")));
        System.out.println("Debug info - InfinityHighScores: " + decodedHighScores.get(2));
        infinityHighScores = new ArrayList<>(Arrays.asList(decodedHighScores.get(2).split("㏠")));
    }

    private void saveHighScores() {
        ArrayList<String> decodedHighScores = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();

        //Delimiter: Unicode number: U+33E0 --> ㏠ (Ideographic Telegraph Symbol for Day One)

        //timeHighScores omvormen naar datastructuur om op te slaan
        for (String timeHighScore : timeHighScores) {
            tmp.append(timeHighScore).append("㏠");
        }
        decodedHighScores.add(0, tmp.deleteCharAt(tmp.length()-1).toString());
        System.out.println(tmp.toString());
        tmp.delete(0, tmp.length());

        //moveHighScores omvormen naar datastructuur om op te slaan
        for (String moveHighScore : moveHighScores) {
            tmp.append(moveHighScore).append("㏠");
        }
        decodedHighScores.add(1, tmp.deleteCharAt(tmp.length()-1).toString());
        System.out.println(tmp.toString());
        tmp.delete(0, tmp.length());

        //infinityHighScores omvormen naar datastructuur om op te slaan
        for (String infinityHighScore : infinityHighScores) {
            tmp.append(infinityHighScore).append("㏠");
        }
        decodedHighScores.add(2, tmp.deleteCharAt(tmp.length()-1).toString());
        System.out.println(tmp.toString());
        tmp.delete(0, tmp.length());


        highScoreIO.saveHighScores(decodedHighScores);
    }


}
