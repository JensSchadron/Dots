package be.kdg.dots.view;

import be.kdg.dots.model.DotKleur;

import java.awt.*;

/**
 * Created by alexander on 6/02/2015.
 */
//TODO: test
public class DrawingClass {
    public void draw(Graphics2D g2d, int w, int h, DotKleur dotKleur) {
        g2d.setColor(new Color(dotKleur.getRood(), dotKleur.getGroen(), dotKleur.getBlauw()));
        g2d.fillOval(5, 5, w / 2, h / 2);
    }
}
