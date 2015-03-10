package be.kdg.dots.view;

import be.kdg.dots.model.veld.DotKleur;
import be.kdg.dots.model.veld.Veld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by jens & alexander on 4/02/2015.
 */

public class GUIGrid extends JPanel {
    //Variabelen nodig om dots te resizen als erover gehoverd wordt
    private int dotIndex;
    private int dotIndexAmount;

    //Variabelen nodig om een lijn te tekenen tussen dots
    private ArrayList<LijnUI> lijnUI;
    private Color dotKleur;

    private ArrayList<LijnUI> hintUI;

    private ArrayList<DotUI> dotUI;
    private GUISpel guiSpel;

    private Timer hintTimer;
    private boolean toonHints = false;

    public GUIGrid(GUISpel guiSpel) throws HeadlessException {
        setOpaque(false);
        setBackground(Color.BLUE);

        lijnUI = new ArrayList<>();
        hintUI = new ArrayList<>();

        this.guiSpel = guiSpel;
        this.dotIndex = -1;
        this.dotIndexAmount = 0;
        if (guiSpel.getController().getSettings().getHintVertraging() != 0) {
            hintTimer = new Timer(guiSpel.getController().getSettings().getHintVertraging(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toonHints = true;
                    repaint();
                }
            });
            hintTimer.setRepeats(false);
            hintTimer.start();
        } else {
            toonHints = true;
        }

        makeComponents(guiSpel.getController().getVeld());
        makeEventListener();
    }

    protected void makeComponents(Veld veld) {
        dotUI = new ArrayList<>(guiSpel.getController().getVeld().getColumn() * guiSpel.getController().getVeld().getRow());

        double width = (this.getWidth() - (DotUI.getAfstandTussenDots() * (guiSpel.getController().getVeld().getColumn() - 1) + DotUI.getMaxDiameter() * guiSpel.getController().getVeld().getColumn())) / 2;
        double height = (this.getHeight() - (DotUI.getAfstandTussenDots() * (guiSpel.getController().getVeld().getColumn() - 1) + DotUI.getMaxDiameter() * guiSpel.getController().getVeld().getRow())) / 2;

        for (int i = 0; i < veld.getVeld().size(); i++) {
            /*double width2 = width + DotUI.getAfstandTussenDots() * (i % GUIGrid.this.guiSpel.getController().getVeld().getColumn() - 1) + DotUI.getMaxDiameter() * (i % GUIGrid.this.guiSpel.getController().getVeld().getColumn());
            double height2 = height + DotUI.getAfstandTussenDots() * ((i>veld.getVeld().size()-veld.getColumn())? i : veld.getRow()-1)+ DotUI.getMaxDiameter() * (i / GUIGrid.this.guiSpel.getController().getVeld().getRow());
            dotUI.add(new DotUI(width2,height2));*/
            dotUI.add(new DotUI(width + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i % GUIGrid.this.guiSpel.getController().getVeld().getColumn()), height + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i / GUIGrid.this.guiSpel.getController().getVeld().getRow()))); //Bevat een array van dotUI objecten met hun grootte
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Dikte van lijnen instellen
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

        //Teken hints
        if (toonHints) {
            reloadHintUI();
            g2d.setColor(Color.LIGHT_GRAY);
            for (LijnUI hint : hintUI) {
                g2d.draw(hint);
            }
        }

        //Teken dot(s)
        for (int i = 0; i < dotUI.size(); i++) {
            DotUI dot = dotUI.get(i);
            DotKleur dotKleur = guiSpel.getController().getVeld().getVeld().get(i).getDotKleur();
            g2d.setColor(new Color(dotKleur.getRood(), dotKleur.getGroen(), dotKleur.getBlauw()));
            g2d.fill(dot);
        }

        //Teken lijn(en)
        g2d.setColor(dotKleur);
        for (LijnUI lijn : lijnUI) {
            g2d.draw(lijn);
        }
    }

    public void reloadHintUI() {
        hintUI.clear();
        ArrayList<Integer> dotIndexHints = guiSpel.getController().getVeld().getBesteMove();
        for (int i = 0; i < dotIndexHints.size() - 1; i++) {
            if (dotIndexHints.size() >= 2) {
                int indexDot1 = dotIndexHints.get(i);
                int indexDot2 = dotIndexHints.get(i + 1);
                hintUI.add(new LijnUI(dotUI.get(indexDot1).getCenterX(), dotUI.get(indexDot1).getCenterY(), dotUI.get(indexDot2).getCenterX(), dotUI.get(indexDot2).getCenterY()));
            }
        }


    }

    private void makeEventListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        //System.out.println("Debug info - Mouse press detected on dot " + i);
                        guiSpel.getController().getVeld().voegConnectedDotToe(i);
                        DotKleur kleur = guiSpel.getController().getVeld().getVeld().get(i).getDotKleur();
                        GUIGrid.this.dotKleur = new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                /*for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        System.out.println("Debug info - Mouse release detected on dot " + i);
                    }
                }*/
                if (GUIGrid.this.lijnUI.size() >= 1 && guiSpel.getController().getSettings().getHintVertraging() != 0) {
                    hintTimer.start();
                    toonHints = false;
                }
                dotKleur = null;
                GUIGrid.this.lijnUI.clear();
                guiSpel.getController().getVeld().clearConnectedDots();
                repaint();

                //guiSpel.setMove();
                //guiSpel.getController().checkScore();
                //System.out.println("Mouse release detected");
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
                                //System.out.println("Debug info - Mouse exit detected on dot " + dotIndex);
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
                            //System.out.println("Debug info - Mouse exit detected on dot " + dotIndex);
                            dotUI.get(dotIndex).toggleDiameter();
                        }
                        dotIndex = -1;
                        dotIndexAmount = 0;
                        repaint();
                    }
                }
                if (dotIndex != -1 && dotIndexAmount == 1) {
                    //System.out.println("Debug info - Mouse enter detected on dot " + dotIndex);
                    dotUI.get(dotIndex).toggleDiameter();
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
                        //System.out.println("Debug info - Mouse drag detected to dot " + i);
                        DotKleur kleur = guiSpel.getController().getVeld().getVeld().get(i).getDotKleur();
                        if (dotKleur == null) {
                            dotKleur = new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw());
                        }
                        if (dotKleur.equals(new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw()))) {
                            guiSpel.getController().getVeld().voegConnectedDotToe(i);
                            ArrayList connectedDots = guiSpel.getController().getVeld().getConnectedDots();
                            if (connectedDots.size() >= 2) {
                                if (guiSpel.getController().getSettings().getHintVertraging() != 0) {
                                    hintTimer.stop();
                                }
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
                double width = (GUIGrid.this.getWidth() - (DotUI.getAfstandTussenDots() * (guiSpel.getController().getVeld().getColumn() - 1) + DotUI.getMaxDiameter() * guiSpel.getController().getVeld().getColumn())) / 2;
                double height = (GUIGrid.this.getHeight() - (DotUI.getAfstandTussenDots() * (guiSpel.getController().getVeld().getColumn() - 1) + DotUI.getMaxDiameter() * guiSpel.getController().getVeld().getRow())) / 2;
                for (int i = 0; i < dotUI.size(); i++) {
                    dotUI.get(i).updateXY(width + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i % GUIGrid.this.guiSpel.getController().getVeld().getColumn()), height + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i / GUIGrid.this.guiSpel.getController().getVeld().getRow()));
                }
                repaint();
            }
        });
    }
}
