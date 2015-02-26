package be.kdg.dots.model.settings;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Jens on 19-2-2015.
 */
public class SettingsIO {
    Path settingsPath;

    public SettingsIO() {
        try {
            settingsPath = Paths.get(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString(),"settings.dat");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    protected void loadSettings(){

    }
}
