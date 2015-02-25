import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * Created by alexander on 29/01/2015.
 */
public class Main {
    private static final int PORT = 25566;
    private static ServerSocket s;

    public static void main(String[] args) {
        try {
            //opent een lokale poort, als poort bezet is geeft hij een exception
            s = new ServerSocket(PORT, 10, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
        } catch (UnknownHostException e) {
            // shouldn't happen for localhost
        } catch (IOException e) {
            // port taken, so app is already running
            JOptionPane.showMessageDialog(null, "Application has already started.", "Dots", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Application has already started");
            System.exit(0);
        }
        new SpelController();
    }
    //TODO: Help, instellingen (kleur veranderen, reset highscores, ...), move modus, inloggen, (uitbreidingen: achievements)
}