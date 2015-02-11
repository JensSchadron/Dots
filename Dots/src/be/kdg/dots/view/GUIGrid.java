package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.DotKleur;
import be.kdg.dots.model.Veld;
//import be.kdg.dots.model.Dot;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.*;
//import java.awt.geom.Ellipse2D;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIGrid extends JPanel {

//Todo: De bedoeling om de grid waar de dots op komen in deze klasse op een panel te zetten.

    private int row, colum;
    private JLabel[] lbldot;
    private JPanel[] jPanels;
    private final int aantal;


    public GUIGrid(SpelController controller) throws HeadlessException {
        super(new GridLayout(controller.getRow(), controller.getColum()));
        this.row = controller.getRow();
        this.colum = controller.getColum();
        aantal = this.row * this.colum;

        MakeComponents(controller.getVeld());
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents(Veld veld) {
        jPanels = new JPanel[this.row * this.colum];
        /*lbldot = new JLabel[this.row * this.colum];

        for (int i = 0; i < lbldot.length; i++) {
            lbldot[i] = new JLabel(Integer.toString(i));
            lbldot[i].setHorizontalAlignment(SwingConstants.CENTER);
            lbldot[i].setOpaque(true);
        }*/
        for (int i = 0; i < veld.getVeld().size(); i++) {
            final DotKleur dotKleur = veld.getVeld().get(i).getDotKleur(); //Kleur dot opvragen
            final DotUI dc = new DotUI(); //DotUI klasse aanmaken (bevat logica om dots te tekenen

            JPanel testPanel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    super.setBackground(Color.white);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    dc.draw(g2d, 50, 50, dotKleur);
                }
            };
            jPanels[i] = testPanel;
        }
    }

    private void MakeLayout() {
        //TODO: Maak een dot a.d.h van de array uit de klasse veld d.m.v drawOval

        super.setLayout(new GridLayout(6, 6));
        for (JPanel jPanel : jPanels) {
            super.add(jPanel);
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
        for (int i = 0; i < jPanels.length; i++) {
            final int i2 = i;
            jPanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Debug: Geklikt op dot " + i2);
                }
            });
        }
    }
}
