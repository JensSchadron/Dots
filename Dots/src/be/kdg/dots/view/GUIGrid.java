package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;
import be.kdg.dots.model.veld.DotKleur;
import be.kdg.dots.model.veld.Veld;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by alexander on 4/02/2015.
 */

public class GUIGrid extends JPanel {
    //Todo: De bedoeling om de grid waar de dots op komen in deze klasse op een panel te zetten.
    //Variabelen nodig om dots te resizen als erover gehoverd wordt
    private int dotIndex;
    private int dotIndexAmount;
    private MouseMotionListener[] listeners;

    //Variabelen nodig om een lijn te tekenen tussen dots
    private ArrayList<LijnUI> lijnUI;
    private Color dotKleur;

    private ArrayList<DotUI> dotUI;
    private SpelController controller;

    public GUIGrid(SpelController controller) throws HeadlessException {
        setOpaque(false);
        setBackground(Color.BLUE);


        lijnUI = new ArrayList<>();

        this.controller = controller;
        this.dotIndex = -1;
        this.dotIndexAmount = 0;

        makeComponents(controller.getVeld());
        makeEventListener();
    }

    protected void makeComponents(Veld veld) {
        dotUI = new ArrayList<>(controller.getColum() * controller.getRow());

        double width = (this.getWidth() - (DotUI.getAfstandTussenDots() * (controller.getColum() - 1) + DotUI.getMaxDiameter() * controller.getColum())) / 2;
        double height = (this.getHeight() - (DotUI.getAfstandTussenDots() * (controller.getColum() - 1) + DotUI.getMaxDiameter() * controller.getRow())) / 2;

        for (int i = 0; i < veld.getVeld().size(); i++) {
            dotUI.add(new DotUI(width + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i % GUIGrid.this.controller.getColum()), height + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i / GUIGrid.this.controller.getRow()))); //Bevat een array van dotUI objecten met kleur en grootte
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Teken dot(s)
        for (int i = 0; i < dotUI.size(); i++) {
            DotUI dot = dotUI.get(i);
            DotKleur dotKleur = controller.getVeld().getVeld().get(i).getDotKleur();
            g2d.setColor(new Color(dotKleur.getRood(), dotKleur.getGroen(), dotKleur.getBlauw()));
            g2d.fill(dot);
        }

        //Teken lijn(en)
        g2d.setColor(dotKleur);
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        for (LijnUI lijn : lijnUI) {
            g2d.draw(lijn);
        }

    }

    private void makeEventListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        System.out.println("Debug info - Mouse press detected on dot " + i);
                        controller.getVeld().voegConnectedDotToe(i);
                        DotKleur kleur = controller.getVeld().getVeld().get(i).getDotKleur();
                        GUIGrid.this.dotKleur = new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        System.out.println("Debug info - Mouse release detected on dot " + i);
                    }
                }
                GUIGrid.this.lijnUI.clear();
                controller.getVeld().clearConnectedDots();
                repaint();
                System.out.println("Mouse release detected");
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //Opslaan in welke dot we gaan. Check inbouwen die controleert of we al in die dot zaten.
                //Value op null zetten als we terug uit de dot gaan

                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        if (dotIndex != i) {
                            if (dotIndex != -1) {
                                System.out.println("Debug info - Mouse exit detected on dot " + dotIndex);
                                dotUI.get(dotIndex).toggleDiameter();
                                dotIndexAmount = 0;
                            }
                        }
                        dotIndex = i;
                        dotIndexAmount++;
                        break;
                    }
                    if (!dotUI.get(i).contains(e.getX(), e.getY()) && i == dotUI.size() - 1) {
                        if (dotIndex != -1) {
                            System.out.println("Debug info - Mouse exit detected on dot " + dotIndex);
                            dotUI.get(dotIndex).toggleDiameter();
                        }
                        dotIndex = -1;
                        dotIndexAmount = 0;
                        repaint();
                    }
                }
                if (dotIndex != -1 && dotIndexAmount == 1) {
                    System.out.println("Debug info - Mouse enter detected on dot " + dotIndex);
                    dotUI.get(dotIndex).toggleDiameter();
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        System.out.println("Debug info - Mouse drag detected to dot " + i);
                        DotKleur kleur = controller.getVeld().getVeld().get(i).getDotKleur();
                        if (dotKleur.equals(new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw()))) {
                            controller.getVeld().voegConnectedDotToe(i);
                            ArrayList connectedDots = controller.getVeld().getConnectedDots();
                            if (connectedDots.size() >= 2) {
                                int indexDot1 = (int) connectedDots.get(connectedDots.size() - 2);
                                int indexDot2 = (int) connectedDots.get(connectedDots.size() - 1);
                                lijnUI.add(new LijnUI(dotUI.get(indexDot1).getCenterX(), dotUI.get(indexDot1).getCenterY(), dotUI.get(indexDot2).getCenterX(), dotUI.get(indexDot2).getCenterY()));
                                repaint();
                            }
                        }
                    }
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                double width = (GUIGrid.this.getWidth() - (DotUI.getAfstandTussenDots() * (controller.getColum() - 1) + DotUI.getMaxDiameter() * controller.getColum())) / 2;
                double height = (GUIGrid.this.getHeight() - (DotUI.getAfstandTussenDots() * (controller.getColum() - 1) + DotUI.getMaxDiameter() * controller.getRow())) / 2;

                for (int i = 0; i < dotUI.size(); i++) {
                    dotUI.get(i).updateXY(width + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i % GUIGrid.this.controller.getColum()), height + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i / GUIGrid.this.controller.getRow()));
                }
                repaint();
            }
        });
    }
}
