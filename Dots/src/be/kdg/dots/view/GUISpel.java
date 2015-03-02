package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUISpel extends JPanel {
    private GUIFrame guiFrame;
    private JPanel gamePanel, panelNorth, flowLayout;
    private GUIGrid gridGame;
    private JLabel lblLevel, lblScore, lblTimeOrMoves, lblPauze, lblHome;
    private SpelController controller;
    private ImageIcon iconLevel, iconScore, iconTimeOrMoves, iconPauze, iconPlay, iconHome;
    private String modus;

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

        iconLevel = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblLevel.png")), 20, 20));
        lblLevel = new JLabel("Level:");
        lblLevel.setIcon(iconLevel);
        lblLevel.setForeground(new Color(0, 150, 75));

        iconScore = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblScore.png")), 20, 20));
        lblScore = new JLabel("Score:");
        lblScore.setIcon(iconScore);
        lblScore.setForeground(Color.blue);

        if (!modus.equals("Move")) {
            iconTimeOrMoves = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblTime.png")), 20, 20));
            lblTimeOrMoves = new JLabel("Time: ");
            lblTimeOrMoves.setIcon(iconTimeOrMoves);
            lblTimeOrMoves.setForeground(Color.red);
        } else {
            iconTimeOrMoves = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/hashtag.png")), 20, 20));
            lblTimeOrMoves = new JLabel("Move: ");
            lblTimeOrMoves.setIcon(iconTimeOrMoves);
            lblTimeOrMoves.setForeground(Color.red);
        }

        iconPauze = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnPauze.png")), 50, 50));
        lblPauze = new JLabel("");
        lblPauze.setName("pauze");
        lblPauze.setIcon(iconPauze);

        iconHome = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnHome.png")), 250, 50));
        lblHome = new JLabel("");
        lblHome.setIcon(iconHome);
    }

    public static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return image;
    }

    private void MakeLayout() {
        gamePanel = new JPanel(new BorderLayout());
        flowLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
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
                controller.getSpel().stopTimer();
                guiFrame.updateFrame("pauzePanel");
            }
        });
        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Mouse release detected on Home button");
                eindigSpel();
            }
        });
    }

    public void eindigSpel() {
        if (!modus.equals("Move")) {
            controller.getSpel().stopTimer();
        }
        controller.getHighscore().addHighScore(modus);
        guiFrame.updateFrame("hoofdMenu");
    }

    /*public void setMove() {
        if (--move == 0) {
            controller.getGuiSpel().eindigSpel();
        } else {
            lblTimeOrMoves.setText("Move: " + move);
        }
    }*/

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
}
