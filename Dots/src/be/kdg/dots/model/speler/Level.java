package be.kdg.dots.model.speler;

/**
 * Created by alexander on 25/02/2015.
 */
public class Level {
    private Speler speler;
    private int level=1;

    public Level(Speler speler) {
        this.speler = speler;
        level = 1;
    }

    public void incrementLevel() {
        this.level++;
        speler.getController().getGuiSpel().updateLevel(level);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void resetLevel (){
        level = 1;
        speler.getController().getGuiSpel().updateLevel(level);
    }
}


