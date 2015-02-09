package be.kdg.dots.model;

import java.util.ArrayList;

/**
 * Created by alexander on 4/02/2015.
 */
public class Veld {
    private ArrayList<Dot> rooster;
    private int row, colum;

    public Veld(int row, int colum) {
        this.row = row;
        this.colum = colum;
        this.rooster = new ArrayList<Dot>(this.row*this.colum);
        vulVeld();
    }
    
    public void vulVeld(){
        for (int i = 0; i < this.row*this.colum; i++) {
            rooster.add(new Dot());
        }
    }

    public ArrayList<Dot> getVeld() {
        return rooster;
    }
}
