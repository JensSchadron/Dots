package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIStartScreen extends JFrame {
    private JPanel main;
    private JButton start;

    public GUIStartScreen() throws HeadlessException {
        super("Dots Game");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MakeComponents();
        MakeLayout();
        MakeEventListener();
        super.setSize(300, 300);
        super.setVisible(true);
    }

    private void MakeComponents() {
        start = new JButton("Start game");

    }

    private void MakeLayout() {
        main = new JPanel(new BorderLayout());
        main.add(start, BorderLayout.CENTER);
        super.add(main);
    }

    private void MakeEventListener() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new GUIView();
            }
        });
    }


}
