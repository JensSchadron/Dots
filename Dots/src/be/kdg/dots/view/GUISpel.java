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

    private JPanel gamePanel, panelNorth, flowLayout;
    private GUIGrid gridGame;
    private JLabel lblLevel, lblScore, lblTime, lblPauze, lblHome;
    private SpelController controller;
    private ImageIcon iconLevel,iconScore,iconTime, iconPauze, iconPlay, iconHome;

    public GUISpel(SpelController controller) throws HeadlessException {
        super.setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.white);
        this.controller = controller;
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        gridGame = new GUIGrid(controller);

        iconLevel = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblLevel.png")), 20,20));
        lblLevel = new JLabel("Level:");
        lblLevel.setIcon(iconLevel);
        lblLevel.setForeground(new Color(0,150,75));

        iconScore = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblScore.png")), 20,20));
        lblScore = new JLabel("Score:");
        lblScore.setIcon(iconScore);
        lblScore.setForeground(Color.blue);

        iconTime = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblTime.png")), 20,20));
        lblTime = new JLabel("Time: 45");
        lblTime.setIcon(iconTime);
        lblTime.setForeground(Color.red);

        iconPauze = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnPauze.png")), 50,50));
        lblPauze = new JLabel("");
        lblPauze.setName("pauze");
        lblPauze.setIcon(iconPauze);

        iconHome = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnHome.png")), 250,50));
        lblHome = new JLabel("");
        lblHome.setIcon(iconHome);
    }

    public static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return newImg;
    }

    private void MakeLayout() {
        gamePanel = new JPanel(new BorderLayout());
        flowLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 90, 10));
        panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        gamePanel.setBackground(Color.white);
        gridGame.setBackground(Color.white);
        panelNorth.setBackground(Color.white);
        flowLayout.add(lblLevel);
        flowLayout.add(lblScore);
        flowLayout.add(lblTime);
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
               if (lblPauze.getName() == "pauze"){
                   iconPlay = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnPlay.png")), 50,50));
                   lblPauze.setIcon(iconPlay);
                   lblPauze.setName("play");
                   controller.stopTimer();
               }else {
                   iconPauze = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnPauze.png")), 50,50));
                   lblPauze.setName("pauze");
                   lblPauze.setIcon(iconPauze);
                   controller.startTimer();
               }
            }
        });
        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                controller.setGuiHoofdMenu();
            }
        });
    }

    public void updateTimer(int aantalSeconden) {
        lblTime.setText("Time: " + aantalSeconden);
    }
}
