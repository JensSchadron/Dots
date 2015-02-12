package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIView extends JFrame{

    private JPanel gamePanel, gridSouth;
    private GUIGrid gridGame;
    private JLabel lblLevel, lblScore, lblTime;
    private JButton btnMenu;
    private SpelController controller;

    public GUIView(SpelController controller) throws HeadlessException {
        super("Dots");
        this.controller = controller;
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MakeComponents();
        MakeLayout();
        MakeEventListener();
        super.setSize(380, 420);
        super.setVisible(true);
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

    public void updateTimer(int aantalSeconden){
        lblTime.setText("Time: " + aantalSeconden);
    }
}
