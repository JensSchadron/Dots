package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame {
    private CardLayout cl;
    private SpelController controller;

    public GUIFrame(SpelController controller) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cl = new CardLayout();
        setLayout(cl);
        this.controller = controller;
        super.setSize(500, 500);
        super.setVisible(true);
    }

    public void updateFrame(String optie) {
        switch (optie) {
            case "hoofdMenu":
                cl.show(this.getContentPane(), "hoofdMenu");
                super.setSize(500, 500);
                break;
            case "startSpel":
                cl.show(this.getContentPane(), "startSpel");
                super.setSize(500, 650);
                break;
            case "pauzePanel":
                setGlassPane(new GUIPauzePane(getContentPane(), controller));
                getGlassPane().setVisible(true);
                break;
            case "instellingenPanel":
                setGlassPane(new GUISettingsPane(getContentPane()));
                getGlassPane().setVisible(true);
                break;
            case "aboutPanel":
                setGlassPane(new GUIAboutPane(getContentPane()));
                getGlassPane().setVisible(true);
                break;
            case "highScorePanel":
                setGlassPane(new GUIHighScore(getContentPane(), controller));
                getGlassPane().setVisible(true);
                break;
            case "login":
                setGlassPane(new GUILogin(getContentPane(), controller));
                getGlassPane().setVisible(true);
                break;
        }
    }

    public SpelController getController() {
        return controller;
    }
}
