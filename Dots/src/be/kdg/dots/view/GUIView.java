package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIView extends JFrame {

    private JPanel gamePanel, gridSouth;
    private GUIGrid gridGame;
    private JLabel lblLevel, lblScore, lblTime;
    private JButton btnMenu;
    private SpelController controller;

    public GUIView() throws HeadlessException {
        super("Dots");
        controller = new SpelController();
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MakeComponents();
        MakeLayout();
        MakeEventListener();
        super.setSize(300, 300);
        super.setVisible(true);
    }

    private void MakeComponents() {
        lblLevel = new JLabel("Level:");
        lblScore = new JLabel("Score:");
        lblTime = new JLabel("Time");
        btnMenu = new JButton("Menu");
    }

    private void MakeLayout() {
        gamePanel = new JPanel(new BorderLayout());
        gridSouth = new JPanel(new GridLayout());
        gridGame = new GUIGrid(controller);
        gamePanel.setBackground(Color.white);
        gridSouth.setBackground(Color.white);
        gridGame.setBackground(Color.white);
        gridSouth.add(lblLevel);
        gridSouth.add(lblScore);
        gamePanel.add(gridSouth, BorderLayout.SOUTH);
        super.add(gamePanel);
        gamePanel.add(gridGame, BorderLayout.CENTER);
    }

    private void MakeEventListener() {

    }

}
