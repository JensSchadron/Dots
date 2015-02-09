package be.kdg.dots.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexander on 4/02/2015.
 */
public class Veld {
    private Dot[][] rooster;
    private int row, colum;

    public Veld(int row, int colum) {
        this.row = row;
        this.colum = colum;
    }

    public Dot[][] ListOfDots() {
        this.rooster = new Dot[row][colum];
        for (int i=0;i<row;i++) {
            for (int j =0;i<colum;i++){
                rooster[i][j] = new Dot();
            }

        }
        return rooster;
    }

    public Dot[][] getRooster() {
        return rooster;
    }
}
