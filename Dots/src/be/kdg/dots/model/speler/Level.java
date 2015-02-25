package be.kdg.dots.model.speler;

/**
 * Created by alexander on 25/02/2015.
 */
public class Level {
    private Speler speler;
    private int level;


    public Level(Speler speler) {
        this.speler = speler;
        level = 1;

    }

    public void setLevel(int level){
        this.level = level;
    }


    public int getLevel() {
        return level;
    }
}
