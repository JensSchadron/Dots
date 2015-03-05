package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame {
    private CardLayout cl;
    private GUIHoofdMenu guiHoofdMenu;
    //private SplashScreen splashScreen;
    private Image iconLoading;
    private JPanel panelClosing;

    public GUIFrame(final GUIHoofdMenu guiHoofdMenu) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        iconLoading = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/dots-logo.png")).getImage();
        super.setIconImage(iconLoading);
        cl = new CardLayout();
        setLayout(cl);
        this.guiHoofdMenu = guiHoofdMenu;
        //this.splashScreen = splashScreen;
        super.setSize(500, 500);
        super.setVisible(true);
        panelClosing = new JPanel();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                guiHoofdMenu.getController().saveSettings();
                if (JOptionPane.showConfirmDialog(panelClosing, "Bent u zeker dat u wilt afsluiten?", "Zeker sluiten?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(1);
                }
            }
        });
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
            /*case "splashScreen":
                cl.show(this.getContentPane(), "splashScreen");
                super.setSize(300, 300);
                break;*/
            /*case "login":
                setGlassPane(new GUILogin(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;*/
        }
    }

}
