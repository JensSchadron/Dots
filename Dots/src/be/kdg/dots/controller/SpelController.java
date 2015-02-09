package be.kdg.dots.controller;

import be.kdg.dots.model.*;

/**
 * Created by alexander on 4/02/2015.
 */
public class SpelController {
    private Veld veld;
    //private int row=6;
    //private int colum=6;

    public SpelController() {
        veld = new Veld(6, 6);
    }

    public Veld getVeld() {
        return veld;
    }

    public int getRow() {
        return veld.getRow();
    }

    public int getColum() {
        return veld.getColum();
    }
}
