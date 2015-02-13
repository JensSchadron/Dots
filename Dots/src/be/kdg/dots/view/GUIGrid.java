package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.DotKleur;
import be.kdg.dots.model.Veld;
//import be.kdg.dots.model.Dot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIGrid extends JPanel {

    //Todo: De bedoeling om de grid waar de dots op komen in deze klasse op een panel te zetten.
    private int dotIndex;
    private int dotIndexAmount;

    private ArrayList<DotUI> dotUI;
    private SpelController controller;
    //private GridBagConstraints gbc;


    public GUIGrid(SpelController controller) throws HeadlessException {
        /*super.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;*/

        this.controller = controller;
        this.dotIndex = -1;
        this.dotIndexAmount = 0;

        makeComponents(controller.getVeld());
        makeLayout();
        makeEventListener();
    }

    protected void makeComponents(Veld veld) {
        dotUI = new ArrayList<>(controller.getColum() * controller.getRow());
        double width = -((this.getWidth()-DotUI.getMaxDiameter()*controller.getColum()))/2;
        double height = -((this.getHeight()-DotUI.getMaxDiameter()*controller.getRow()))/2;

        for (int i = 0; i < veld.getVeld().size(); i++) {
            dotUI.add(new DotUI(width + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i % GUIGrid.this.controller.getColum()), height + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i / GUIGrid.this.controller.getRow()))); //Bevat een array van dotUI objecten met kleur en grootte
        }
        repaint();
    }

    private void makeLayout() {
        //TODO: Maak een dot a.d.h van de array uit de klasse veld d.m.v drawOval

        /*for (int i = 0; i < dotUI.size(); i++) {
            gbc.gridx = i%6;
            gbc.gridy = i/6;
            //System.out.println("Debug info - Cell x: " + gbc.gridx + " Cell y: " + gbc.gridy);
            super.add(dotUI.get(i),gbc);
        }*/

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < dotUI.size(); i++) {
            DotUI dot = dotUI.get(i);
            DotKleur dotKleur = controller.getVeld().getVeld().get(i).getDotKleur();
            g2d.setColor(new Color(dotKleur.getRood(), dotKleur.getGroen(), dotKleur.getBlauw()));
            g2d.fill(dot);
        }
    }
    /*private Graphics MakeDot(Graphics g){
        return g.drawOval(0,0,10,10);
    }*/

    private void makeEventListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        System.out.println("Debug info - Mouse release detected on dot " + i);
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //Opslaan in welke dot we gaan. Check inbouwen die controleert of we al in die dot zaten.
                //Value op null zetten als we terug uit de dot gaan

                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        dotIndex = i;
                        dotIndexAmount++;
                        break;
                    }
                    if (!dotUI.get(i).contains(e.getX(), e.getY()) && i == dotUI.size()-1) {
                        if(dotIndex!=-1) {
                            System.out.println("Debug info - Mouse exit detected on dot " + dotIndex);
                            dotUI.get(dotIndex).toggleDiameter();
                        }
                        dotIndex = -1;
                        dotIndexAmount = 0;
                        GUIGrid.this.repaint();
                    }
                }
                if (dotIndex != -1 && dotIndexAmount == 1) {
                    System.out.println("Debug info - Mouse enter detected on dot " + dotIndex);
                    dotUI.get(dotIndex).toggleDiameter();
                    GUIGrid.this.repaint();
                    repaint();
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                double width = (GUIGrid.this.getWidth()-(DotUI.getMaxDiameter()*GUIGrid.this.controller.getColum()))/2;
                double height = (GUIGrid.this.getHeight()-(DotUI.getMaxDiameter()*GUIGrid.this.controller.getRow()))/2;
                System.out.printf("Panel width: %f - Panel height: %f\n",width,height);

                for (int i = 0; i < dotUI.size(); i++) {
                    //System.out.println(dotUI.get(i).getX() + ", " + dotUI.get(i).getY());
                    dotUI.get(i).updateXY(width + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i % GUIGrid.this.controller.getColum()), height + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i / GUIGrid.this.controller.getRow()));
                    //System.out.println(dotUI.get(i).getX() + ", " + dotUI.get(i).getY());
                }
                //GUIGrid.this.repaint();
                repaint();

                //dotUI.clear();
                //makeComponents(controller.getVeld());
            }
        });

            /*this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouse(MouseEvent e) {
                    if (dotUI.get(i2).contains(e.getX(), e.getY())){
                        System.out.println("Debug info - Mouse enter detected on dot " + i2);
                        dotUI.get(i2).toggleDiameter();
                        repaint();
                    }
                }
            });*/

            /*
            dotUI.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Debug: Geklikt op dot " + i2);
                    dotUI[i2].removeMouseListener(this);
                    dotUI[i2] = null;
                    controller.getVeld().getVeld().remove(i2);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    dotUI.get(i2).toggleDiameter();
                    dotUI.get(i2).repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    dotUI.get(i2).toggleDiameter();
                    dotUI.get(i2).repaint();
                }
            });*/
    }

}
