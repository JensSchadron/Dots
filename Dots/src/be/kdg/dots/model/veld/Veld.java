package be.kdg.dots.model.veld;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.view.DotUI;

import java.util.*;

public class Veld {
    private final ArrayList<Dot> rooster;
    private final ArrayList<Integer> connectedDots;
    private final int[] dotIndexCheck = new int[8];
    private final int row;
    private final int column;
    private final SpelController controller;

    //Field voor het berekenen van de beste zet
    private ArrayList<Integer> besteMove;
    private final ArrayList<Integer> currentMove;
    private Thread threadBestMove;
    private boolean interruptFlag;
    private final ArrayList<KleurDotIndexPair> indexMap;

    public Veld(int row, int column, SpelController controller) {
        this.controller = controller;
        this.row = row;
        this.column = column;
        this.rooster = new ArrayList<>(this.row * this.column);
        this.connectedDots = new ArrayList<>();
        this.besteMove = new ArrayList<>(this.row * this.column);
        this.currentMove = new ArrayList<>(this.row * this.column);
        this.indexMap = new ArrayList<>(DotKleur.values().length);
        this.threadBestMove = new Thread(new BestMove());
        this.interruptFlag = false;
        vuldotIndexCheck();
        vulVeld();
    }

    void vuldotIndexCheck() {
        dotIndexCheck[0] = -this.column - 1;
        dotIndexCheck[1] = -this.column;
        dotIndexCheck[2] = -this.column + 1;
        dotIndexCheck[3] = -1;
        dotIndexCheck[4] = 1;
        dotIndexCheck[5] = this.column - 1;
        dotIndexCheck[6] = this.column;
        dotIndexCheck[7] = this.column + 1;
    }

    void vulVeld() {
        for (int i = 0; i < this.row * this.column; i++) {
            rooster.add(new Dot());
        }
        startBerekenen();
        if (gameOver()) {
            vulVeld();
        }
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

    public void voegConnectedDotToe(int index) {
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
            controller.getSpel().decrementMoves();
            for (Integer connectedDot : connectedDots) {
                rooster.set(connectedDot.intValue(), null);
                controller.getGuiHoofdMenu().getGuiSpel().getGUIGrid().getDotUI().set(connectedDot.intValue(), null);
            }

            controller.getSpeler().getScore().berekenScore(connectedDots);
            for (int i = this.row * this.column - 1; i > -1; i--) {
                Dot dotOrNull = rooster.get(i);
                DotUI dotUI = null;
                if (dotOrNull == null) {

                    for (int j = i; j >= 0 && dotOrNull == null; j -= this.row) {
                        if (rooster.get(j) != null) {
                            dotOrNull = rooster.get(j);
                            controller.getGuiHoofdMenu().getGuiSpel().getGUIGrid().getDotUI().get(j).setHoeveelDotsZakken((i - j) / this.row);
                            dotUI = controller.getGuiHoofdMenu().getGuiSpel().getGUIGrid().getDotUI().get(j);
                            controller.getGuiHoofdMenu().getGuiSpel().getGUIGrid().getDotUI().set(j, null);
                            rooster.set(j, null);
                        }
                    }
                    if (dotOrNull == null) {
                        dotOrNull = new Dot();
                    }
                    rooster.set(i, dotOrNull);
                    controller.getGuiHoofdMenu().getGuiSpel().getGUIGrid().getDotUI().set(i, dotUI);
                    if (controller.getGuiHoofdMenu().getGuiSpel().getGUIGrid().getDotUI().get(i) != null) {
                        controller.getGuiHoofdMenu().getGuiSpel().getGUIGrid().getDotUI().get(i).setVallen();
                    }
                }
            }
            controller.getGuiHoofdMenu().getGuiSpel().updateScore(controller.getSpeler().getScore().getScore(), controller.getSpeler().getScore().getScoreDoel());
            startBerekenen();
            startCheckingAchievements();
            if (gameOver()) {
                controller.getGuiHoofdMenu().getGuiSpel().eindigSpel(true);
            }
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
        return true;
    }

    void startBerekenen() {
        this.threadBestMove = new Thread(new BestMove());
        this.threadBestMove.start();
    }

    public void stopBerekenen() {
        if (this.threadBestMove.isAlive()) {
            interruptFlag = true;
        }
    }

    void startCheckingAchievements() {
        Thread checkAchievements = new Thread(new CheckAchievements());
        checkAchievements.start();
    }

    private class CheckAchievements implements Runnable {
        @Override
        public void run() {
            TreeSet<DotKleur> dominerendeKleurHashSet = new TreeSet<>();
            ArrayList<DotKleur> uitgeroeideKleurArrayList = new ArrayList<>(Arrays.asList(DotKleur.values()));
            for (int i = 0; i < rooster.size(); i++) {
                dominerendeKleurHashSet.add(rooster.get(i).getDotKleur());
                uitgeroeideKleurArrayList.remove(rooster.get(i).getDotKleur());
            }
            //Achievements kleur uitgeroeid
            for (DotKleur uitgeroeideKleur : uitgeroeideKleurArrayList) {
                controller.getSettings().addAchievements(uitgeroeideKleur + " is uitgeroeid!");
            }

            //Achievements kleur domineert
            if (dominerendeKleurHashSet.size() == 1) {
                controller.getSettings().addAchievements(dominerendeKleurHashSet.first() + " domineert het spel!");
            }
        }
    }

    //------------------------------------Algoritme om beste lijn te berekenen----------------------------------------//

    private class BestMove implements Runnable {
        @Override
        public void run() {
            if (controller.getSettings().isHintsEnabled()) {
                calculateBestMove();
            }
        }
    }

    private class KleurDotIndexPair implements Comparable<KleurDotIndexPair> {
        private final DotKleur kleur;
        private ArrayList<Integer> dotIndexes;
        private ArrayList<Integer> dotsMet1Combinatie;

        public KleurDotIndexPair(DotKleur kleur, ArrayList<Integer> dotIndexes) {
            this.kleur = kleur;
            this.dotIndexes = dotIndexes;
            this.dotsMet1Combinatie = new ArrayList<>();
        }

        public ArrayList<Integer> getDotIndexes() {
            return dotIndexes;
        }

        public void setDotIndexes(ArrayList<Integer> dotIndexes) {
            this.dotIndexes = dotIndexes;
        }

        public ArrayList<Integer> getDotsMet1Combinatie() {
            return dotsMet1Combinatie;
        }

        public void setDotsMet1Combinatie(ArrayList<Integer> dotsMet1Combinatie) {
            this.dotsMet1Combinatie = dotsMet1Combinatie;
        }

        public DotKleur getKleur() {
            return kleur;
        }

        @Override
        public int compareTo(KleurDotIndexPair o) {
            return ((Integer) o.getDotIndexes().size()).compareTo(this.getDotIndexes().size());
        }
    }

    void calculateBestMove() {
        indexMap.clear();
        besteMove.clear();
        currentMove.clear();

        //Analyseren van rooster op dots die minstens één mogelijke zet hebben.
        KleurDotIndexPair tmpPair;
        ArrayList<Integer> tmpArrayList;
        for (int i = 0; i < rooster.size(); i++) {
            int aantalMogelijkeCombinaties = 0;
            int indexBackup = -1;
            int[] tmpIndexArray;
            if (i < this.column && i % this.column == 0) {
                tmpIndexArray = new int[]{4, 6, 7};

            } else if (i < this.column && i % this.column == this.column - 1) {
                tmpIndexArray = new int[]{3, 5, 6};

            } else if (i >= rooster.size() - this.column && i % this.column == this.column - 1) {
                tmpIndexArray = new int[]{0, 1, 3};

            } else if (i >= rooster.size() - this.column && i % this.column == 0) {
                tmpIndexArray = new int[]{1, 2, 4};

            } else if (i < this.column) {
                tmpIndexArray = new int[]{3, 4, 5, 6, 7};

            } else if (i % this.column == this.column - 1) {
                tmpIndexArray = new int[]{0, 1, 3, 5, 6};

            } else if (i >= rooster.size() - this.column) {
                tmpIndexArray = new int[]{0, 1, 2, 3, 4};

            } else if (i % this.column == 0) {
                tmpIndexArray = new int[]{1, 2, 4, 6, 7};

            } else {
                tmpIndexArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
            }
            for (int j = 0; j < tmpIndexArray.length; j++) {
                DotKleur kleur = rooster.get(i).getDotKleur();
                if (kleur.equals(rooster.get(i + dotIndexCheck[tmpIndexArray[j]]).getDotKleur())) {
                    aantalMogelijkeCombinaties++;
                    int index = -1;
                    for (int k = 0; k < indexMap.size(); k++) {
                        if (indexMap.get(k).getKleur().equals(kleur)) {
                            index = k;
                            indexBackup = k;
                        }
                    }
                    if (aantalMogelijkeCombinaties == 1) {
                        if (index == -1) {
                            tmpArrayList = new ArrayList<>();
                            tmpArrayList.add(i);
                            tmpPair = new KleurDotIndexPair(kleur, tmpArrayList);
                            indexMap.add(tmpPair);
                        } else {
                            tmpPair = indexMap.get(index);
                            tmpArrayList = tmpPair.getDotIndexes();
                            tmpArrayList.add(i);
                            tmpPair.setDotIndexes(tmpArrayList);
                            indexMap.set(index, tmpPair);
                        }
                    }
                }
            }
            if (aantalMogelijkeCombinaties == 1 && indexBackup != -1) {
                tmpPair = indexMap.get(indexBackup);
                tmpArrayList = tmpPair.getDotsMet1Combinatie();
                tmpArrayList.add(i);
                tmpPair.setDotsMet1Combinatie(tmpArrayList);
                indexMap.set(indexBackup, tmpPair);
            }
        }

        Collections.sort(indexMap);

        //Algoritme versnellen door dots met maar één combinatie vooraan te plaatsen.
        for (int i = 0; i < indexMap.size(); i++) {
            tmpPair = indexMap.get(i);
            ArrayList<Integer> dotsMet1Combinatie = tmpPair.dotsMet1Combinatie;
            ArrayList<Integer> dotIndexes = tmpPair.getDotIndexes();
            for (int j = 0; j < dotsMet1Combinatie.size(); j++) {
                dotIndexes.remove(dotsMet1Combinatie.get(j));
                dotIndexes.add(j, dotsMet1Combinatie.get(j));
            }
        }

        outerForLoop:
        for (int i = 0; i < indexMap.size(); i++) {
            ArrayList<Integer> tmp = indexMap.get(i).getDotIndexes();
            for (int j = 0; j < tmp.size(); j++) {
                if (interruptFlag) {
                    break outerForLoop;
                }
                calculateNextMove(tmp.get(j), indexMap.get(i).getDotIndexes());
            }
        }

        interruptFlag = false;
    }

    private void calculateNextMove(int currentIndex, ArrayList<Integer> indexArrayList) {

        currentMove.add(currentIndex);
        DotKleur kleur = rooster.get(currentIndex).getDotKleur();

        int[] tmpIndexArray;
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

        } else {
            tmpIndexArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        }

        for (int i = 0; i < tmpIndexArray.length; i++) {
            if (interruptFlag || Thread.interrupted()) {
                interruptFlag = true;
                return;
            }
            if (kleur.equals(rooster.get(currentIndex + dotIndexCheck[tmpIndexArray[i]]).getDotKleur()) && !currentMove.contains(currentIndex + dotIndexCheck[tmpIndexArray[i]])) {
                calculateNextMove(currentIndex + dotIndexCheck[tmpIndexArray[i]], indexArrayList);
            }
            if (besteMove.size() == indexArrayList.size()) {
                interruptFlag = true;
            }
        }

        if (Thread.interrupted() || interruptFlag) {
            interruptFlag = true;
            return;
        }

        if (currentMove.size() > besteMove.size()) {
            besteMove = new ArrayList<>(currentMove);
        }

        currentMove.remove(currentMove.size() - 1);
    }
}