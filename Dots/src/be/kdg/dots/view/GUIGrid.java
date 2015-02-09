package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.DotKleur;
import be.kdg.dots.model.Veld;
//import be.kdg.dots.model.Dot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.geom.Ellipse2D;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIGrid extends JPanel {

//Todo: De bedoeling om de grid waar de dots op komen in deze klasse op een panel te zetten.

    private int row, colum;
    private JLabel lbldot[];
    private final int aantal;


    public GUIGrid(SpelController controller) throws HeadlessException {
        super(new GridLayout(controller.getRow(), controller.getColum()));
        this.row = controller.getRow();
        this.colum = controller.getColum();
        aantal = this.row * this.colum;

        MakeComponents();
        MakeLayout(controller.getVeld());
        MakeEventListener();
    }

    private void MakeComponents() {
        lbldot = new JLabel[this.row * this.colum];

        for (int i = 0; i < lbldot.length; i++) {
            lbldot[i] = new JLabel(Integer.toString(i));
            lbldot[i].setHorizontalAlignment(SwingConstants.CENTER);
            lbldot[i].setOpaque(true);
        }
    }

    private void MakeLayout(Veld veld) {
        //TODO: Maak een dot a.d.h van de array uit de klasse veld d.m.v drawOval

        for (int i = 0; i < row; i++) {
                final DotKleur dotKleur = veld.getVeld().get(i).getDotKleur();
                final DotUI dc = new DotUI();

                JPanel testPanel = new JPanel() {
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        dc.draw(g2d, 50, 50,dotKleur);
                    }
                };
                super.add(testPanel);

        }

    }

/*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        dc.draw(g2d, getWidth(), getHeight());

    }*/
    /*private Graphics MakeDot(Graphics g){
        return g.drawOval(0,0,10,10);
    }*/

    private void MakeEventListener() {
        for (int i = 0; i < aantal; i++) {
            //final int i2 =i;
            lbldot[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
            });
        }
    }
}
