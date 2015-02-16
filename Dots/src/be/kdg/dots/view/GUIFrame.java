package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame{
    private SpelController controller;

    public GUIFrame(SpelController controller) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.controller = controller;
        super.setSize(500, 500);
        super.setVisible(true);
    }

    public void updateFrame(JPanel panel){
        super.removeAll();
        super.add(panel, BorderLayout.CENTER);
        super.validate();
    }
}
