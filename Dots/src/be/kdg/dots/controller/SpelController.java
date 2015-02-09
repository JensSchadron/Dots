package be.kdg.dots.controller;

import be.kdg.dots.model.*;

/**
 * Created by alexander on 4/02/2015.
 */
public class SpelController {
    private Speler speler;
    private Veld veld;
    private int row=5;
    private int colum=6;

    public SpelController() {
        speler = new Speler();
        veld = new Veld(row, colum);
    }

    public Veld getVeld() {
        return veld;
    }

    public Speler getSpeler() {
        return speler;
    }

    public int getRow() {
        return row;
    }

    public int getColum() {
        return colum;
    }
}
