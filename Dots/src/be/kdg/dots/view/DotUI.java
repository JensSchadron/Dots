package be.kdg.dots.view;

import be.kdg.dots.model.DotKleur;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by alexander on 6/02/2015.
 */

public class DotUI extends Ellipse2D.Double {
    private static final double MIN_DIAMETER = 50;
    private static final double MAX_DIAMETER = 60;
    private static final double AFSTAND_TUSSEN_DOTS = 10;

    private double x;
    private double y;
    private double diameter = MIN_DIAMETER;

    public DotUI(double x, double y) {
        super(x, y, MIN_DIAMETER, MIN_DIAMETER);
        this.x = x;
        this.y = y;
        this.diameter = MIN_DIAMETER;
    }

    public void toggleDiameter() {
        System.out.println("Trying to toggle diameter");
        this.diameter = (this.diameter == MAX_DIAMETER) ? MIN_DIAMETER : MAX_DIAMETER;
        this.x = (this.diameter == MAX_DIAMETER) ? this.x - 5 : this.x + 5;
        this.y = (this.diameter == MAX_DIAMETER) ? this.y - 5 : this.y + 5;
        super.setFrame(this.x, this.y, this.diameter, this.diameter);
    }

    public void updateXY(double panelWidth, double panelHeight) {
        this.x = panelWidth;
        this.y = panelHeight;
    }

    @Override
    public boolean contains(double x, double y) {
        return super.contains(x, y);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public double getHeight() {
        return diameter;
    }

    @Override
    public double getWidth() {
        return diameter;
    }

    public static double getMaxDiameter() {
        return MAX_DIAMETER;
    }

    public static double getAfstandTussenDots() {
        return AFSTAND_TUSSEN_DOTS;
    }

    /*  private DotKleur kleur;
    private static final Dimension dimension = new Dimension(MAX_SIZE, MAX_SIZE);

    public DotUI(DotKleur kleur) {
        this.x = 5;
        this.y = 5;
        this.diameter = MIN_SIZE;
        this.kleur = kleur;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw()));
        g2d.fillOval(this.x, this.y, diameter, diameter);
    }*/
}
