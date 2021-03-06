package be.kdg.dots.view;

import be.kdg.dots.model.veld.DotKleur;
import be.kdg.dots.model.veld.Veld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUIGrid extends JPanel {
    //Variabelen nodig om dots te resizen als erover gehoverd wordt
    private int dotIndex;
    private int dotIndexAmount;

    //Variabelen nodig om een lijn te tekenen tussen dots
    private final ArrayList<LijnUI> lijnUI;
    private Color dotKleur;

    private final ArrayList<LijnUI> hintUI;

    private ArrayList<DotUI> dotUI;
    private double widthForDots;
    private double heightForDots;
    private final GUISpel guiSpel;

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

        new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i) != null && dotUI.get(i).moetVallen()) {
                        dotUI.get(i).vallen(berekenY(i));
                    }
                }
                repaint();
            }
        }).start();
    }

    void makeComponents(Veld veld) {
        dotUI = new ArrayList<>(guiSpel.getController().getVeld().getColumn() * guiSpel.getController().getVeld().getRow());

        berekenBeginXY();

        for (int i = 0; i < veld.getVeld().size(); i++) {
            dotUI.add(new DotUI(berekenX(i), berekenY(i))); //Bevat een array van dotUI objecten met hun grootte
        }
        repaint();
    }

    private void berekenBeginXY() {
        widthForDots = (this.getWidth() - (DotUI.getAfstandTussenDots() * (guiSpel.getController().getVeld().getColumn() - 1) + DotUI.getMaxDiameter() * guiSpel.getController().getVeld().getColumn())) / 2;
        heightForDots = (this.getHeight() - (DotUI.getAfstandTussenDots() * (guiSpel.getController().getVeld().getColumn() - 1) + DotUI.getMaxDiameter() * guiSpel.getController().getVeld().getRow())) / 2;
    }

    private double berekenX(int index) {
        return widthForDots + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (index % GUIGrid.this.guiSpel.getController().getVeld().getColumn());
    }

    private double berekenY(int index) {
        return heightForDots + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (index / GUIGrid.this.guiSpel.getController().getVeld().getRow());
    }

    private void veldAanvullen() {
        for (int i = dotUI.size() - 1; i >= 0; i--) {
            if (dotUI.get(i) == null) {
                DotUI tmpDotUI;
                if (i + guiSpel.getController().getVeld().getColumn() >= guiSpel.getController().getVeld().getVeld().size()) {
                    tmpDotUI = new DotUI(widthForDots + (DotUI.getAfstandTussenDots() + DotUI.getMaxDiameter()) * (i % GUIGrid.this.guiSpel.getController().getVeld().getColumn()), heightForDots);
                } else {
                    tmpDotUI = dotUI.get(i + guiSpel.getController().getVeld().getColumn());
                }
                tmpDotUI.isMaximized();

                double y = tmpDotUI.getY() - 2 * (DotUI.getMaxDiameter() + DotUI.getAfstandTussenDots());
                dotUI.set(i, new DotUI(tmpDotUI.getX(), y));
                dotUI.get(i).setVallen();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Dikte van lijnen instellen
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

        //Eventueel nieuwe dotUI object genereren alvorens de hints te tekenen
        veldAanvullen();

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

    void reloadHintUI() {
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
                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (int i = 0; i < dotUI.size(); i++) {
                        if (dotUI.get(i).contains(e.getX(), e.getY())) {
                            guiSpel.getController().getVeld().voegConnectedDotToe(i);
                            DotKleur kleur = guiSpel.getController().getVeld().getVeld().get(i).getDotKleur();
                            GUIGrid.this.dotKleur = new Color(kleur.getRood(), kleur.getGroen(), kleur.getBlauw());
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (GUIGrid.this.lijnUI.size() >= 1 && guiSpel.getController().getSettings().getHintVertraging() != 0) {
                        hintTimer.start();
                        toonHints = false;
                    }
                    dotKleur = null;
                    GUIGrid.this.lijnUI.clear();


                    guiSpel.getController().getVeld().clearConnectedDots();
                    repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //Opslaan in welke dot we gaan. Check inbouwen die controleert of we al in die dot zaten.
                //Value op null zetten als we terug uit de dot gaan

                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i) != null && dotUI.get(i).contains(e.getX(), e.getY()) && !dotUI.get(i).moetVallen()) {
                        if (dotIndex != i) {
                            if (dotIndex != -1) {
                                dotUI.get(dotIndex).toggleDiameter();
                                dotIndexAmount = 0;
                            }
                        }
                        dotIndex = i;
                        dotIndexAmount++;
                        break;
                    }
                    if (dotUI.get(i) != null && !dotUI.get(i).contains(e.getX(), e.getY()) && i == dotUI.size() - 1) {
                        if (dotIndex != -1) {
                            for (DotUI aDotUI : dotUI) {
                                aDotUI.isMaximized();
                            }
                        }
                        dotIndex = -1;
                        dotIndexAmount = 0;
                        repaint();
                    }
                }
                if (dotIndex != -1 && dotIndexAmount == 1) {
                    dotUI.get(dotIndex).toggleDiameter();
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
                for (int i = 0; i < dotUI.size(); i++) {
                    if (dotUI.get(i).contains(e.getX(), e.getY())) {
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
                berekenBeginXY();
                for (int i = 0; i < dotUI.size(); i++) {
                    dotUI.get(i).isMaximized();
                    dotUI.get(i).updateXY(berekenX(i), berekenY(i));
                }
                repaint();
            }
        });
    }

    public ArrayList<DotUI> getDotUI() {
        return dotUI;
    }
}
