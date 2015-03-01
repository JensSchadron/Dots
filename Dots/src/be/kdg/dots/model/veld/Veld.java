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
    private Thread threadBestMove;
    private boolean interruptFlag;

    public Veld(int row, int column, SpelController controller) {
        this.controller = controller;
        this.row = row;
        this.column = column;
        this.rooster = new ArrayList<>(this.row * this.column);
        this.connectedDots = new ArrayList<>();
        this.besteMove = new ArrayList<>(this.row * this.column);
        this.currentMove = new ArrayList<>(this.row * this.column);
        this.threadBestMove = new Thread(new BestMove());
        this.interruptFlag = false;
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
        //bestMove.start();
        this.threadBestMove = new Thread(new BestMove());
        this.threadBestMove.start();
        //calculateBestMove();
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

    public ArrayList<Integer> getBesteMove() {
        return besteMove;
    }

    public void voegConnectedDotToe(int index) { //TODO: Methode aanpassen vanwege bugs in code (zie gameover-methode)
        if (!connectedDots.contains(index)) {
            if (connectedDots.size() > 0) {
                int lastDotIndex = connectedDots.get(connectedDots.size() - 1);
                int[] tmpIndexArray;
                if (lastDotIndex < this.column && lastDotIndex % this.column == 0) {
                    tmpIndexArray = new int[]{4, 6, 7};

                } else if (lastDotIndex < this.column && lastDotIndex % this.column == this.column - 1) {
                    tmpIndexArray = new int[]{3, 5, 6};

                } else if (lastDotIndex >= rooster.size() - this.column && lastDotIndex % this.column == this.column - 1) {
                    tmpIndexArray = new int[]{0, 1, 3};

                } else if (lastDotIndex >= rooster.size() - this.column && lastDotIndex % this.column == 0) {
                    tmpIndexArray = new int[]{1, 2, 4};

                } else if (lastDotIndex < this.column) {
                    tmpIndexArray = new int[]{3, 4, 5, 6, 7};

                } else if (lastDotIndex % this.column == this.column - 1) {
                    tmpIndexArray = new int[]{0, 1, 3, 5, 6};

                } else if (lastDotIndex >= rooster.size() - this.column) {
                    tmpIndexArray = new int[]{0, 1, 2, 3, 4};

                } else if (lastDotIndex % this.column == 0) {
                    tmpIndexArray = new int[]{1, 2, 4, 6, 7};

                } else {
                    tmpIndexArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
                }

                for (int i = 0; i < tmpIndexArray.length; i++) {
                    if (lastDotIndex + dotIndexCheck[tmpIndexArray[i]] == index) {
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
            threadBestMove.interrupt();
            //killCalculateBestMove();
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
            //bestMove.start();
            while (threadBestMove.isAlive()) {

            }
            this.threadBestMove = new Thread(new BestMove());
            this.threadBestMove.start();

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

    public class BestMove implements Runnable {
        @Override
        public void run() {
            System.out.println("Debug info - Calculating started...");
            calculateBestMove();
            System.out.println("Debug info - Calculating stopped...");
        }
    }

    public void calculateBestMove() {
        besteMove.clear();
        currentMove.clear();
        for (int i = 0; i < rooster.size(); i++) {
            if (Thread.interrupted() || interruptFlag) {
                System.out.println("Busy with stopping calculateBestMove");
                return;
            }
            calculateNextMove(i);
        }
        String result = "";
        for (int i = 0; i < besteMove.size(); i++) {
            result += besteMove.get(i) + ", ";
        }
        System.out.println(result);
        interruptFlag = false;

    }

    private void calculateNextMove(int currentIndex) {
        currentMove.add(currentIndex);
        DotKleur kleur = rooster.get(currentIndex).getDotKleur();
        if (!(currentIndex < this.column || currentIndex >= rooster.size() - this.column || currentIndex % this.column == 0 || currentIndex % this.column == this.column - 1)) { //controleren of dot niet aan zijkant ligt van speelveld.
            for (int i = 0; i < dotIndexCheck.length; i++) {
                if (Thread.interrupted() || interruptFlag) {
                    System.out.println("Busy with stopping calculateNextMove");
                    interruptFlag = true;
                    return;
                }
                if (kleur.equals(rooster.get(currentIndex + dotIndexCheck[i]).getDotKleur()) && !currentMove.contains(currentIndex + dotIndexCheck[i])) {
                    calculateNextMove(currentIndex + dotIndexCheck[i]);
                }
            }
        } else {
            int[] tmpIndexArray = new int[0];
            if (currentIndex < this.column && currentIndex % this.column == 0) {
                tmpIndexArray = new int[]{4, 6, 7};

            } else if (currentIndex < this.column && currentIndex % this.column == this.column - 1) {
                tmpIndexArray = new int[]{3, 5, 6};

            } else if (currentIndex >= rooster.size() - this.column && currentIndex % this.column == this.column - 1) {
                tmpIndexArray = new int[]{0, 1, 3};

            } else if (currentIndex >= rooster.size() - this.column && currentIndex % this.column == 0) {
                tmpIndexArray = new int[]{1, 2, 4};

            } else if (currentIndex < this.column) {
                tmpIndexArray = new int[]{3, 4, 5, 6, 7};

            } else if (currentIndex % this.column == this.column - 1) {
                tmpIndexArray = new int[]{0, 1, 3, 5, 6};

            } else if (currentIndex >= rooster.size() - this.column) {
                tmpIndexArray = new int[]{0, 1, 2, 3, 4};

            } else if (currentIndex % this.column == 0) {
                tmpIndexArray = new int[]{1, 2, 4, 6, 7};

            }
            for (int i = 0; i < tmpIndexArray.length; i++) {
                if (Thread.interrupted() || interruptFlag) {
                    System.out.println("Busy with stopping calculateNextMove");
                    interruptFlag = true;
                    return;
                }
                if (kleur.equals(rooster.get(currentIndex + dotIndexCheck[tmpIndexArray[i]]).getDotKleur()) && !currentMove.contains(currentIndex + dotIndexCheck[tmpIndexArray[i]])) {
                    calculateNextMove(currentIndex + dotIndexCheck[tmpIndexArray[i]]);
                }
            }
        }
        if (Thread.interrupted() || interruptFlag) {
            System.out.println("Busy with stopping calculateNextMove");
            interruptFlag = true;
            return;
        }

        if (currentMove.size() > besteMove.size()) {
            besteMove = new ArrayList<>(currentMove);
        }
        currentMove.remove(currentMove.size() - 1);
    }
}
