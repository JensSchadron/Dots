package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jens on 9-3-2015.
 */
public class GUIGlassPane extends JPanel {
    private Container contentPane;

    public GUIGlassPane(Container contentPane){
        this.contentPane = contentPane;
    }

    public Container getContentPane() {
        return contentPane;
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        contentPane.paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
    }
}