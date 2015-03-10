package be.kdg.dots.model.veld;

import java.util.Random;

public class Dot {
    private DotKleur dotKleur;

    public Dot() {
        int pick = new Random().nextInt(DotKleur.values().length);
        this.dotKleur = DotKleur.values()[pick];
    }

    public DotKleur getDotKleur() {
        return dotKleur;
    }
}
