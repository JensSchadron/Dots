package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame {
    private CardLayout cl;
    //private SpelController controller;
    private GUIHoofdMenu guiHoofdMenu;
    private Image iconLoading;
    private JPanel loadingPanel;
    private JLabel loading;

    public GUIFrame(SpelController controller, GUIHoofdMenu guiHoofdMenu) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        iconLoading = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/dots-logo.png")).getImage();
        super.setIconImage(iconLoading);
        cl = new CardLayout();
        setLayout(cl);
        //guiHoofdMenu = new GUIHoofdMenu(controller);
        this.guiHoofdMenu = guiHoofdMenu;
        //this.controller = controller;
        super.setSize(500, 500);
        super.setVisible(true);
        /*loadingPanel = new JPanel(new BorderLayout());
        iconLoading = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/infinity.png"));
        loading = new JLabel("", JLabel.CENTER);
        loading.setIcon(iconLoading);
        loadingPanel.add(loading);
        cl.show(this.getContentPane(), "test");*/
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
                setGlassPane(new GUIPauzePane(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;
            case "instellingenPanel":
                setGlassPane(new GUISettingsPane(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;
            case "aboutPanel":
                setGlassPane(new GUIAboutPane(getContentPane()));
                getGlassPane().setVisible(true);
                break;
            case "highScorePanel":
                setGlassPane(new GUIHighScore(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;
            case "login":
                setGlassPane(new GUILogin(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;
        }
    }

}
