package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.DotKleur;
import be.kdg.dots.model.Veld;
//import be.kdg.dots.model.Dot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.geom.Ellipse2D;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIGrid extends JPanel {

//Todo: De bedoeling om de grid waar de dots op komen in deze klasse op een panel te zetten.

    private int row, colum;
    private DotUI[] dotUI;
    private final int aantal;
    private SpelController controller;
    private GridLayout grid;
    private GridBagConstraints gbc;


    public GUIGrid(SpelController controller) throws HeadlessException {
        //super.setLayout(new GridLayout(controller.getRow(), controller.getColum()));
        super.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        /*gbc.gridheight = 360;
        gbc.gridwidth = 360;*/


        this.controller = controller;
        this.row = controller.getRow();
        this.colum = controller.getColum();
        aantal = this.row * this.colum;

        MakeComponents(controller.getVeld());
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents(Veld veld) {
        dotUI = new DotUI[this.row * this.colum];
        /*lbldot = new JLabel[this.row * this.colum];

        for (int i = 0; i < lbldot.length; i++) {
            lbldot[i] = new JLabel(Integer.toString(i));
            lbldot[i].setHorizontalAlignment(SwingConstants.CENTER);
            lbldot[i].setOpaque(true);
        }*/
        for (int i = 0; i < veld.getVeld().size(); i++) {
            DotKleur dotKleur = veld.getVeld().get(i).getDotKleur(); //Kleur dot opvragen
            dotUI[i] = new DotUI(dotKleur); //Bevat een array van dotUI objecten met kleur en grootte
        }
    }

    private void MakeLayout() {
        //TODO: Maak een dot a.d.h van de array uit de klasse veld d.m.v drawOval

        for (int i = 0; i < dotUI.length; i++) {
            gbc.gridx = i%6;
            gbc.gridy = i/6;
            System.out.println("Cell x: " + gbc.gridx + " Cell y: " + gbc.gridy);
            super.add(dotUI[i],gbc);
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
        for (int i = 0; i < dotUI.length; i++) {
            final int i2 = i;
            dotUI[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Debug: Geklikt op dot " + i2);
                    /*dotUI[i2].removeMouseListener(this);
                    dotUI[i2] = null;
                    controller.getVeld().getVeld().remove(i2);*/
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    dotUI[i2].toggleDiameter();
                    dotUI[i2].repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    dotUI[i2].toggleDiameter();
                    dotUI[i2].repaint();
                }
            });
        }
    }
}
