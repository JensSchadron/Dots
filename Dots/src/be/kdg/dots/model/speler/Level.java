package be.kdg.dots.model.speler;

public class Level {
    private Speler speler;
    private int level=1;

    public Level(Speler speler) {
        this.speler = speler;
        level = 1;
    }

    public void incrementLevel() {
        this.level++;
        speler.getController().getGuiHoofdMenu().getGuiSpel().updateLevel(level);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void resetLevel (){
        level = 1;
        speler.getController().getGuiHoofdMenu().getGuiSpel().updateLevel(level);
    }
}


