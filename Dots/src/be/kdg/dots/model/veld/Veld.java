package be.kdg.dots.model.veld;

import be.kdg.dots.controller.SpelController;

import java.util.ArrayList;

/**
 * Created by alexander on 4/02/2015.
 */
public class Veld {
    private ArrayList<Dot> rooster;
    private ArrayList<Integer> connectedDots;
    private ArrayList<Integer> besteMove;
    private ArrayList<Integer> currentMove;
    private final int[] dotIndexCheck = new int[8];
    private final int row;
    private final int column;
    private SpelController controller;

    public Veld(int row, int column, SpelController controller) {
        this.controller = controller;
        this.row = row;
        this.column = column;
        this.rooster = new ArrayList<>(this.row * this.column);
        this.connectedDots = new ArrayList<>();
        this.besteMove = new ArrayList<>(this.row * this.column);
        this.currentMove = new ArrayList<>(this.row * this.column);
        vuldotIndexCheck();
        vulVeld();
    }

    public void vuldotIndexCheck() {
        dotIndexCheck[0] = -this.column - 1;
        dotIndexCheck[1] = -this.column;
        dotIndexCheck[2] = -this.column + 1;
        dotIndexCheck[3] = -1;
        dotIndexCheck[4] = 1;
        dotIndexCheck[5] = this.column - 1;
        dotIndexCheck[6] = this.column;
        dotIndexCheck[7] = this.column + 1;
        //dotIndexCheck = {-this.column-1, -this.column, -this.column, -1, 1, this.column + 5, this.column + 6, this.column + 7};
    }

    public void vulVeld() {
        for (int i = 0; i < this.row * this.column; i++) {
            rooster.add(new Dot());
        }
        gameOver();
    }

    public ArrayList<Dot> getVeld() {
        return rooster;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void voegConnectedDotToe(int index) { //TODO: Methode aanpassen vanwege bugs in code (zie gameover-methode)
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
            controller.getSpeler().getScore().berekenScore(connectedDots);


            for (int i = this.row * this.column - 1; i > -1; i--) {
                Dot dotOrNull = rooster.get(i);
                if (dotOrNull == null) {
                    for (int j = i; j >= 0 && dotOrNull == null; j -= this.column) {
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
            controller.getGuiSpel().updateScore(controller.getSpeler().getScore().getScore(), controller.getSpeler().getScore().getScoreDoel());
            gameOver(); //TODO: extra code schrijven om spel te beëindigen
        }
        connectedDots.clear();
    }

    private boolean gameOver() {
        int[] check = {1, this.column + 1, this.column, this.column - 1};
        for (int i = 0; i < rooster.size() - 1; i++) { //laatste dot moet niet gecontroleerd worden!!!
            DotKleur kleur = rooster.get(i).getDotKleur();

            if (i < rooster.size() - this.column) {

                if (!((i % this.column) == 0 || (i % this.column) == (this.column - 1))) {
                    for (int j = 0; j < check.length; j++) {
                        if (kleur.equals(rooster.get(i + check[j]).getDotKleur())) {
                            return false;
                        }
                    }
                } else if (i % this.column == this.column - 1) {
                    for (int j = 2; j < check.length; j++) {
                        if (kleur.equals(rooster.get(i + check[j]).getDotKleur())) {
                            return false;
                        }
                    }
                } else if (i % this.column == 0) {
                    for (int j = 0; j < check.length - 1; j++) {
                        if (kleur.equals(rooster.get(i + check[j]).getDotKleur())) {
                            return false;
                        }
                    }
                }

            } else if (i >= rooster.size() - this.row) {
                if (kleur.equals(rooster.get(i + check[0]).getDotKleur())) {
                    return false;
                }
            }
        }

        System.out.println("Tja, game over hé");
        return true;
    }

    public void calculateBestMove() {
        for (int i = 0; i < rooster.size(); i++) {
            calculateNextMove(i);
        }
    }

    private void calculateNextMove(int currentIndex) {
        currentMove.add(currentIndex);
        for (int i = 0; i < dotIndexCheck.length; i++) {
            if (!(currentIndex < this.column || currentIndex >= rooster.size() - this.column || currentIndex % this.column == 0 || currentIndex % this.column == this.column - 1)) { //controleren of dot niet aan zijkant ligt van speelveld.
                if (rooster.get(currentIndex).getDotKleur().equals(rooster.get(currentIndex + dotIndexCheck[i]).getDotKleur())) {
                    calculateNextMove(currentIndex + dotIndexCheck[i]);
                }
            } else {
                if (currentIndex < this.column && currentIndex % this.column == 0) {

                } else if (currentIndex < this.column) {

                } else if (currentIndex < this.column && currentIndex % this.column == this.column - 1) {

                } else if (currentIndex % this.column == this.column - 1) {

                } else if (currentIndex >= rooster.size() - this.column && currentIndex % this.column == this.column - 1) {

                } else if (currentIndex >= rooster.size() - this.column) {

                } else if (currentIndex >= rooster.size() - this.column && currentIndex % this.column == 0) {

                } else if (currentIndex % this.column == 0) {

                }
            }
        }
    }
}
