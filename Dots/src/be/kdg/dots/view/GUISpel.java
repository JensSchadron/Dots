package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUISpel extends JPanel {
    private final GUIFrame guiFrame;
    private GUIGrid gridGame;
    private JLabel lblLevel, lblScore, lblTimeOrMoves, lblPauze, lblHome;
    private final SpelController controller;
    private final String modus;

    public GUISpel(SpelController controller, GUIFrame guiFrame, String modus) throws HeadlessException {
        super.setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.white);
        this.controller = controller;
        this.guiFrame = guiFrame;
        this.modus = modus;
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        gridGame = new GUIGrid(this);

        ImageIcon iconLevel = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelInfo/imgLevel.png")), 20, 20));
        lblLevel = new JLabel("Level:");
        lblLevel.setIcon(iconLevel);
        lblLevel.setForeground(new Color(0, 150, 75));

        ImageIcon iconScore = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelInfo/imgScore.png")), 20, 20));
        lblScore = new JLabel("Score:");
        lblScore.setIcon(iconScore);
        lblScore.setForeground(Color.blue);

        ImageIcon iconTimeOrMoves;
        if (!modus.equals("Move")) {
            iconTimeOrMoves = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelInfo/imgTime.png")), 20, 20));
            lblTimeOrMoves = new JLabel("Time: ");
            lblTimeOrMoves.setIcon(iconTimeOrMoves);
            lblTimeOrMoves.setForeground(Color.red);
        } else {
            iconTimeOrMoves = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelInfo/imgHashtag.png")), 20, 20));
            lblTimeOrMoves = new JLabel("Move: ");
            lblTimeOrMoves.setIcon(iconTimeOrMoves);
            lblTimeOrMoves.setForeground(Color.red);
        }

        ImageIcon iconPauze = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelMenu/btnPauze.png")), 50, 50));
        lblPauze = new JLabel("");
        lblPauze.setName("pauze");
        lblPauze.setIcon(iconPauze);

        ImageIcon iconHome = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelMenu/btnHome.png")), 250, 50));
        lblHome = new JLabel("");
        lblHome.setIcon(iconHome);
    }

    private static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return image;
    }

    private void MakeLayout() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        JPanel flowLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        gamePanel.setBackground(Color.white);
        gridGame.setBackground(Color.white);
        panelNorth.setBackground(Color.white);
        flowLayout.add(lblLevel);
        flowLayout.add(lblScore);
        flowLayout.add(lblTimeOrMoves);
        panelNorth.add(lblPauze);
        panelNorth.add(lblHome);
        gamePanel.add(panelNorth, BorderLayout.NORTH);
        gamePanel.add(gridGame, BorderLayout.CENTER);
        gamePanel.add(flowLayout, BorderLayout.SOUTH);
        super.add(gamePanel);
    }

    private void MakeEventListener() {
        lblPauze.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!modus.equals("Move")){
                    controller.getSpel().stopTimer();
                }
                guiFrame.updateFrame("pauzePanel");
            }
        });
        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                eindigSpel();
            }
        });
    }

    public void eindigSpel() {
        controller.getVeld().stopBerekenen();
        if (!modus.equals("Move")) {
            controller.getSpel().stopTimer();
        }
        controller.getHighscore().addHighScore(modus);
        guiFrame.updateFrame("gameEndPanel");
    }

    public void updateTimerOrMoves(int aantalSecondenOfMoves) {
        switch (modus) {
            case "Move":
                lblTimeOrMoves.setText("Move: " + aantalSecondenOfMoves);
                break;
            default:
                lblTimeOrMoves.setText("Time: " + aantalSecondenOfMoves);
                break;
        }
    }

    public void updateScore(int score, int doel) {
        lblScore.setText("Score: " + score + " / " + doel);
    }

    public void updateLevel(int level) {
        lblLevel.setText("Level: " + level);
    }

    public SpelController getController() {
        return controller;
    }

    public String getModus() {
        return modus;
    }

    public GUIGrid getGUIGrid(){
        return gridGame;
    }
}
