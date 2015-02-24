import be.kdg.dots.controller.SpelController;
import be.kdg.dots.view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            System.out.println("Application has already started");
            System.exit(0);
        }
        new SpelController();
    }
}