package be.kdg.dots.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by alexander on 4/02/2015.
 */
public class Veld {
    private ArrayList<Dot> rooster;
    private ArrayList<Integer> connectedDots;
    private final int row;
    private final int colum;

    public Veld(int row, int colum) {
        this.row = row;
        this.colum = colum;
        this.rooster = new ArrayList<>(this.row * this.colum);
        this.connectedDots = new ArrayList<>();
        vulVeld();
    }

    public void vulVeld() {
        for (int i = 0; i < this.row * this.colum; i++) {
            rooster.add(new Dot());
        }
    }

    public ArrayList<Dot> getVeld() {
        return rooster;
    }

    public int getColum() {
        return colum;
    }

    public int getRow() {
        return row;
    }

    public void voegConnectedDotToe(int index) {
        if (!connectedDots.contains(index))
            connectedDots.add(index);
    }

    public ArrayList<Integer> getConnectedDots() {
        return connectedDots;
    }

    public void clearConnectedDots() {
        if (connectedDots.size() >= 2) {
            for (int index = 0; index < connectedDots.size(); index++) {
                rooster.set(connectedDots.get(index), null);
            }
        }

        for (int i = this.row * this.colum - 1; i > -1; i--) {
            Object dotOrNull = rooster.get(i);
            if (dotOrNull == null) {
                Dot dot = null;
                for (int j = i; j > -1; j -= 6) {
                    if (rooster.get(j) != null) {
                        dot = rooster.get(j);
                        rooster.set(j, null);
                    }
                }
                if (dot == null) {
                    dot = new Dot();
                }
                rooster.set(i, dot);
            }
        }
        connectedDots.clear();
    }


}
