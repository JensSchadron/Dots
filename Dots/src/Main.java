import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * Created by alexander on 29/01/2015.
 */
@SuppressWarnings("ALL")
class Main {
    private static final int PORT = 25566;
    private static ServerSocket socket;

    public static void main(String[] args) {
        try {
            //opent een lokale poort, als poort bezet is geeft hij een exception
            socket = new ServerSocket(PORT, 10, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
        } catch (UnknownHostException e) {
            // shouldn't happen for localhost
        } catch (IOException e) {
            // port taken, so app is already running
            JOptionPane.showMessageDialog(null, "Applicatie is al opgestart..", "Dots", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Main.class.getResource("/images/meldingen/Icon - waarschuwing.png")));
            System.out.println("Application has already started");
            System.exit(0);
        }
        new SpelController();
    }
    //TODO: scherm voor gameover, tonen van message dialog als exceptions/errors optreden
}