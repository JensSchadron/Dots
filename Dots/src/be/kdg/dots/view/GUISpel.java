package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUISpel extends JPanel {

    private JPanel gamePanel, gridSouth;
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
        lblLevel = new JLabel("Level:");
        lblScore = new JLabel("Score:");
        lblTime = new JLabel("Time: 45");
    }

    private void MakeLayout() {
        gamePanel = new JPanel(new BorderLayout());
        gridSouth = new JPanel(new GridLayout());
        gamePanel.setBackground(Color.white);
        gridSouth.setBackground(Color.white);
        gridGame.setBackground(Color.white);
        gridSouth.add(lblLevel);
        gridSouth.add(lblScore);
        gridSouth.add(lblTime);
        gamePanel.add(gridSouth, BorderLayout.SOUTH);
        gamePanel.add(gridGame, BorderLayout.CENTER);
        super.add(gamePanel);

    }

    private void MakeEventListener() {

    }

    public void updateTimer(int aantalSeconden) {
        lblTime.setText("Time: " + aantalSeconden);
    }
}
