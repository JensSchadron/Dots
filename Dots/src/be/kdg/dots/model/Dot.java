package be.kdg.dots.model;

import java.util.Random;

/**
 * Created by alexander on 4/02/2015.
 */
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
