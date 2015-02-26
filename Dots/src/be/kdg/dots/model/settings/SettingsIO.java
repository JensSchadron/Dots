package be.kdg.dots.model.settings;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 19-2-2015.
 */
public class SettingsIO {
    private Settings settings;
    private Path settingsPath;

    public SettingsIO(Settings settings) {
        this.settings = settings;
        try {
            settingsPath = Paths.get(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString(),"settings.dat");
        } catch (URISyntaxException e) { //Deze error zou nooit mogen optreden...
            e.printStackTrace();
        }
    }

    protected List<String> loadSettings() {
        List<String> strInstellingen =  new ArrayList<>();
        if(Files.exists(settingsPath)){
            try {
                strInstellingen = Files.readAllLines(settingsPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }

        return strInstellingen;
    }
}
