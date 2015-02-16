package be.kdg.dots.model.veld;

import java.util.ArrayList;

/**
 * Created by alexander on 4/02/2015.
 */
public class Veld {
    private ArrayList<Dot> rooster;
    private ArrayList<Integer> connectedDots;
    private final int[] dotIndexCheck = new int[8];
    private final int row;
    private final int colum;

    public Veld(int row, int colum) {
        this.row = row;
        this.colum = colum;
        this.rooster = new ArrayList<>(this.row * this.colum);
        this.connectedDots = new ArrayList<>();
        vuldotIndexCheck();
        vulVeld();
    }

    public void vuldotIndexCheck() {
        dotIndexCheck[0] = -this.colum - 1;
        dotIndexCheck[1] = -this.colum;
        dotIndexCheck[2] = -this.colum + 1;
        dotIndexCheck[3] = -1;
        dotIndexCheck[4] = 1;
        dotIndexCheck[5] = this.colum - 1;
        dotIndexCheck[6] = this.colum;
        dotIndexCheck[7] = this.colum + 1;
        //dotIndexCheck = {-this.colum-1, -this.colum, -this.colum, -1, 1, this.colum + 5, this.colum + 6, this.colum + 7};
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
        if (!connectedDots.contains(index)) {
            if (connectedDots.size() > 0) {
                int lastDotIndex = connectedDots.get(connectedDots.size() - 1);
                for (int dotIndex : dotIndexCheck) {
                    if (lastDotIndex + dotIndex == index) {
                        connectedDots.add(index);
                        break;
                    }
                }
            } else {
                connectedDots.add(index);
            }
        }
    }

    public ArrayList<Integer> getConnectedDots() {
        return connectedDots;
    }

    public void clearConnectedDots() {
        if (connectedDots.size() >= 2) {
            for (Integer connectedDot : connectedDots) {
                rooster.set(connectedDot.intValue(), null);
            }
        }

        for (int i = this.row * this.colum - 1; i > -1; i--) {
            Dot dotOrNull = rooster.get(i);
            if (dotOrNull == null) {
                for (int j = i; j >= 0 && dotOrNull == null; j -= this.colum) {
                    if (rooster.get(j) != null) {
                        dotOrNull = rooster.get(j);
                        rooster.set(j, null);
                    }
                }
                if (dotOrNull == null) {
                    dotOrNull = new Dot();
                }
                rooster.set(i, dotOrNull);
            }
        }
        connectedDots.clear();
    }


}
