package be.kdg.dots.model.settings;

import be.kdg.dots.controller.SpelController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jens on 15-3-2015.
 */
public class UpdateChecker {
    public static boolean checkForUpdates(LocalDateTime laatstGeupdate) {
        return laatstGeupdate != null && getLatestUpdateTime().isAfter(laatstGeupdate);
    }

    public static LocalDateTime getLatestUpdateTime() {
        String date = "";
        String time = "";
        try {
            URL url = new URL("https://api.github.com/repos/JensSchadron/Dots/commits");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String[] strings = in.readLine().split(",");
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].contains("\"date\"")) {
                    date = strings[i].split(":")[1].replaceAll("\"", "").split("T")[0];
                    time = strings[i].split("T")[1].split("Z")[0];
                    break;
                }
            }
        } catch (IOException e) {
            //Niets doen, de methode parseDateTime is hierop voorzien.
        }
        return parseDateTime(date, time);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        return parseDateTime(dateTime.split("T")[0], dateTime.split("T")[1]);
    }

    public static LocalDateTime parseDateTime(String date, String time) {
        if (date.isEmpty() || time.isEmpty()) {
            return LocalDateTime.MIN;
            //Deze er is iets foutgelopen bij het ingeven van de datum of tijd,
            //we returnen hierbij dus de minimum LocalDateTime.
        } else {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-d");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m:s");
            return LocalDateTime.of(LocalDate.parse(date, dateFormatter), LocalTime.parse(time, timeFormatter));
        }
    }
}

