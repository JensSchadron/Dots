package be.kdg.dots.view;

import be.kdg.dots.model.DotKleur;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexander on 6/02/2015.
 */

public class DotUI extends JPanel {
    private static final int MAX_SIZE = 60;
    private static final int MIN_SIZE = 50;

    private int x;
    private int y;
    private int diameter;
    private DotKleur kleur;
    private static final Dimension dimension = new Dimension(MAX_SIZE, MAX_SIZE);

    public DotUI(DotKleur kleur) {
        super.setOpaque(false);
        super.setPreferredSize(dimension);
        this.x = 5;
        this.y = 5;
        this.diameter = MIN_SIZE;
        this.kleur = kleur;
    }

    private void toggleX() {
        this.x = (this.diameter == MAX_SIZE) ? 0 : 5;
    }

    private void toggleY() {
        this.y = (this.diameter == MAX_SIZE) ? 0 : 5;
    }

    public void toggleDiameter() {
        this.diameter = (this.diameter == MAX_SIZE) ? MIN_SIZE : MAX_SIZE;
        toggleX();
        toggleY();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw()));
        g2d.fillOval(this.x, this.y, diameter, diameter);
    }
}
