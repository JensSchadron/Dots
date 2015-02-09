package be.kdg.dots.model;

import java.util.Random;

/**
 * Created by alexander on 4/02/2015.
 */
public enum DotKleur {
    BLUE(127,26,243),RED(245,0,82),GREEN(0,245,122),YELLOW(255,255,0),PURPLE(255,0,255);

    private final int rood;
    private final int groen;
    private final int blauw;

    DotKleur(int rood, int groen, int blauw) {
        this.rood = rood;
        this.groen = groen;
        this.blauw = blauw;
    }

    public int getRood() {
        return rood;
    }

    public int getGroen() {
        return groen;
    }

    public int getBlauw() {
        return blauw;
    }
}


