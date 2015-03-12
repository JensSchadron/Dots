package be.kdg.dots.view;

import java.awt.geom.Ellipse2D;

public class DotUI extends Ellipse2D.Double {
    private static final double MIN_DIAMETER = 50;
    private static final double MAX_DIAMETER = 60;
    private static final double AFSTAND_TUSSEN_DOTS = 10;

    private double x;
    private double y;
    private double diameter;
    private int yVallen;
    private int hoeveelDotsZakken;
    private boolean moetVallen;

    public DotUI(double x, double y) {
        super(x, y, MIN_DIAMETER, MIN_DIAMETER);
        this.x = x;
        this.y = y;
        this.diameter = MIN_DIAMETER;
        this.yVallen = 0;
        this.hoeveelDotsZakken = 0;
        this.moetVallen = false;
    }

    public void toggleDiameter() {
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

    public void isMaximized(){
        if (diameter == MAX_DIAMETER) {
            toggleDiameter();
        }
    }

    public void vallen(){
        isMaximized();
        updateXY(getX(), getY() + 10);
        yVallen += 10;


        if (yVallen >= hoeveelDotsZakken * (MAX_DIAMETER + AFSTAND_TUSSEN_DOTS)) {
            moetVallen = false;
            yVallen = 0;
            hoeveelDotsZakken = 0;
        }
    }

    public void setHoeveelDotsZakken(int hoeveelDotsZakken) {
        this.hoeveelDotsZakken = hoeveelDotsZakken;
    }

    public int getHoeveelDotsZakken() {
        return hoeveelDotsZakken;
    }

    public void setVallen() {
        this.moetVallen = true;
    }

    public boolean moetVallen(){
        return moetVallen;
    }
}
