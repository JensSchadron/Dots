package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUISpel extends JPanel {

    private JPanel gamePanel, gridSouth, flowLayout;
    private GUIGrid gridGame;
    private JLabel lblLevel, lblScore, lblTime;
    private JButton btnMenu;
    private SpelController controller;

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
        btnMenu = new JButton("Menu");
        gridGame = new GUIGrid(controller);

        ImageIcon iconLevel= new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblLevel.png"));
        iconLevel = new ImageIcon(resize(iconLevel, 20,20));
        lblLevel = new JLabel("Level:");
        lblLevel.setIcon(iconLevel);

        ImageIcon iconScore= new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblScore.png"));
        iconScore = new ImageIcon(resize(iconScore, 20,20));
        lblScore = new JLabel("Score:");
        lblScore.setIcon(iconScore);

        ImageIcon iconTime = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/lblTime.png"));
        iconTime = new ImageIcon(resize(iconTime, 20,20));
        lblTime = new JLabel("Time: 45");
        lblTime.setIcon(iconTime);
    }

    public static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return newImg;
    }

    private void MakeLayout() {
        gamePanel = new JPanel(new BorderLayout());
        flowLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 70,10));
        gamePanel.setBackground(Color.white);
        gridGame.setBackground(Color.white);
        flowLayout.add(lblLevel);
        flowLayout.add(lblScore);
        flowLayout.add(lblTime);
        gamePanel.add(flowLayout, BorderLayout.SOUTH);
        gamePanel.add(gridGame, BorderLayout.CENTER);
        super.add(gamePanel);

    }

    private void MakeEventListener() {

    }

    public void updateTimer(int aantalSeconden) {
        lblTime.setText("Time: " + aantalSeconden);
    }
}
