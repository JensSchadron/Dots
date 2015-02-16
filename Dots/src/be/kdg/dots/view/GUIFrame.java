package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame{
    private SpelController controller;
    private JPanel panel;

    public GUIFrame(SpelController controller, JPanel panel) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.controller = controller;
        this.panel = panel;
        MakeComponents();
        super.setSize(500, 500);
        super.setVisible(true);
    }

    private void MakeComponents() {
        super.add(panel);
    }
}
