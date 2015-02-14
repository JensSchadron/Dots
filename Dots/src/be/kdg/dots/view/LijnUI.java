package be.kdg.dots.view;

import java.awt.geom.Line2D;

/**
 * Created by Jens on 14-2-2015.
 */
public class LijnUI extends Line2D.Double {
    public LijnUI(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public double getX1() {
        return super.getX1();
    }

    @Override
    public double getX2() {
        return super.getX2();
    }

    @Override
    public double getY1() {
        return super.getY1();
    }

    @Override
    public double getY2() {
        return super.getY2();
    }


}
