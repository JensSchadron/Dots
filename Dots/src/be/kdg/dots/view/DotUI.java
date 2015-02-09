package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexander on 5/02/2015.
 */

class DotUI extends JPanel{
    public DotUI(){

    }


    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.red);
        g.fillOval(0, 0, 10, 10);
    }
}


