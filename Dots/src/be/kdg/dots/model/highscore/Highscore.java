package be.kdg.dots.model.highscore;

import be.kdg.dots.controller.SpelController;

import java.util.ArrayList;
import java.util.Arrays;

public class Highscore {
    private SpelController controller;
    private HighScoreIO highScoreIO;

    private ArrayList<String> timeHighScores;
    private ArrayList<String> moveHighScores;
    private ArrayList<String> infinityHighScores;

    public Highscore(SpelController controller) {
        this.controller = controller;
        this.highScoreIO = new HighScoreIO();

        timeHighScores = new ArrayList<>(3);
        moveHighScores = new ArrayList<>(3);
        infinityHighScores = new ArrayList<>(3);

        loadHighScores();
    }

    public String getHighScores(String modus) {
        String result = "";
        switch (modus) {
            case "Time":
                result = String.format("%-20s %7s %7s\n", "Naam", "Score", "Level");
                for (String s : timeHighScores) {
                    result += s + "\n";
                }
                break;
            case "Move":
                result = String.format("%-20s %15s\n", "Naam", "Score");
                for (String s : moveHighScores) {
                    result += s + "\n";
                }
                break;
            case "Infinity":
                result = String.format("%-20s %7s %7s\n", "Naam", "Score", "Tijd");
                for (String s : infinityHighScores) {
                    result += s + "\n";
                }
                break;
        }
        return result;
    }

    public void addHighScore(String modus) {
        switch (modus) {
            case "Time":
                if (timeHighScores.get(0).isEmpty() || timeHighScores.size() == 0) {
                    timeHighScores.set(0, String.format("%-20s %7d %7d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore(), controller.getSpeler().getLevel().getLevel()));
                    break;
                }
                for (int i = 0; i < timeHighScores.size(); i++) {
                    if (controller.getSpeler().getScore().getScore() > Integer.parseInt(timeHighScores.get(i).substring(20, 28).trim())) {
                        timeHighScores.add(i, String.format("%-20s %7d %7d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore(), controller.getSpeler().getLevel().getLevel()));
                        break;
                    }
                    if (i == timeHighScores.size() - 1) {
                        timeHighScores.add(timeHighScores.size(), String.format("%-20s %7d %7d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore(), controller.getSpeler().getLevel().getLevel()));
                        break;
                    }
                }
                break;
            case "Move":
                if (moveHighScores.get(0).isEmpty() || moveHighScores.size() == 0) {
                    moveHighScores.set(0, String.format("%-20s %15d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore()));
                    break;
                }
                for (int i = 0; i < moveHighScores.size(); i++) {
                    if (controller.getSpeler().getScore().getScore() > Integer.parseInt(moveHighScores.get(i).substring(20).trim())) {
                        moveHighScores.add(i, String.format("%-20s %15d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore()));
                        break;
                    }
                    if (i == moveHighScores.size() - 1) {
                        moveHighScores.add(moveHighScores.size(), String.format("%-20s %15d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore()));
                        break;
                    }
                }
                break;
            case "Infinity":
                if (infinityHighScores.get(0).isEmpty() || infinityHighScores.size() == 0) {
                    infinityHighScores.set(0, String.format("%-20s %7d %7d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore(), controller.getSpel().getAantalSeconden()));
                    break;
                }
                for (int i = 0; i < infinityHighScores.size(); i++) {
                    if (controller.getSpeler().getScore().getScore() > Integer.parseInt(infinityHighScores.get(i).substring(20, 28).trim())) {
                        infinityHighScores.add(i, String.format("%-20s %7d %7d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore(), controller.getSpel().getAantalSeconden()));
                        break;
                    }
                    if (i == infinityHighScores.size() - 1) {
                        infinityHighScores.add(infinityHighScores.size(), String.format("%-20s %7d %7d", controller.getSpeler().getUsername(), controller.getSpeler().getScore().getScore(), controller.getSpel().getAantalSeconden()));
                        break;
                    }
                }
                break;
        }
        saveHighScores();
    }

    public void resetHighScores() {
        resetHighScores("Time");
        resetHighScores("Move");
        resetHighScores("Infinity");
    }

    public void resetHighScores(String modus) {
        switch (modus) {
            case "Time":
                timeHighScores.clear();
                timeHighScores.add("");
                break;
            case "Move":
                moveHighScores.clear();
                moveHighScores.add("");
                break;
            case "Infinity":
                infinityHighScores.clear();
                infinityHighScores.add("");
                break;
        }
        saveHighScores();
    }

    private void loadHighScores() {
        ArrayList<String> decodedHighScores = highScoreIO.loadHighScores();
        ArrayList<String> tmp;
        for (int i = 0; i < decodedHighScores.size(); i++) {
            tmp = new ArrayList<>(Arrays.asList(decodedHighScores.get(i).split("㏠")));
            switch (i) {
                case 0:
                    for (int j = 0; j < tmp.size(); j++) {
                        if (tmp.get(0).isEmpty()) {
                            timeHighScores.add("");
                            break;
                        }
                        timeHighScores.add(j, String.format("%-20s %7s %7s", tmp.get(j).split(";")[0], tmp.get(j).split(";")[1], tmp.get(j).split(";")[2]));
                    }
                    break;
                case 1:
                    for (int j = 0; j < tmp.size(); j++) {
                        if (tmp.get(0).isEmpty()) {
                            moveHighScores.add("");
                            break;
                        }
                        moveHighScores.add(j, String.format("%-20s %15s", tmp.get(j).split(";")[0], tmp.get(j).split(";")[1]));
                    }
                    break;
                case 2:
                    for (int j = 0; j < tmp.size(); j++) {
                        if (tmp.get(0).isEmpty()) {
                            infinityHighScores.add("");
                            break;
                        }
                        infinityHighScores.add(j, String.format("%-20s %7s %7s", tmp.get(j).split(";")[0], tmp.get(j).split(";")[1], tmp.get(j).split(";")[2]));
                    }
                    break;
            }
        }
    }

    private void saveHighScores() {
        ArrayList<String> decodedHighScores = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();

        //Delimiter: Unicode number: U+33E0 --> ㏠ (Ideographic Telegraph Symbol for Day One)

        //timeHighScores omvormen naar datastructuur om op te slaan
        for (int i = 0; i < timeHighScores.size(); i++) {
            if (!timeHighScores.get(i).isEmpty()) {
                tmp.append(timeHighScores.get(i).substring(0, 20).trim()).append(";").append(timeHighScores.get(i).substring(20).trim().replaceAll("[ ]+", ";")).append("㏠");
            } else {
                tmp.append("㏠");
            }
        }
        decodedHighScores.add(0, tmp.deleteCharAt(tmp.length() - 1).toString());
        tmp.delete(0, tmp.length());

        //moveHighScores omvormen naar datastructuur om op te slaan
        for (int i = 0; i < moveHighScores.size(); i++) {
            if (!moveHighScores.get(i).isEmpty()) {
                tmp.append(moveHighScores.get(i).substring(0, 20).trim()).append(";").append(moveHighScores.get(i).substring(20).trim().replaceAll("[ ]+", ";")).append("㏠");
            } else {
                tmp.append("㏠");
            }
        }
        decodedHighScores.add(1, tmp.deleteCharAt(tmp.length() - 1).toString());
        tmp.delete(0, tmp.length());

        //infinityHighScores omvormen naar datastructuur om op te slaan
        for (int i = 0; i < infinityHighScores.size(); i++) {
            if (!infinityHighScores.get(i).isEmpty()) {
                tmp.append(infinityHighScores.get(i).substring(0, 20).trim()).append(";").append(infinityHighScores.get(i).substring(20).trim().replaceAll("[ ]+", ";")).append("㏠");
            } else {
                tmp.append("㏠");
            }
        }
        decodedHighScores.add(2, tmp.deleteCharAt(tmp.length() - 1).toString());
        tmp.delete(0, tmp.length());
        highScoreIO.saveHighScores(decodedHighScores);
    }
}
