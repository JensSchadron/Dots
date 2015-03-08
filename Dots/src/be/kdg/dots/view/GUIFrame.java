package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame {
    private CardLayout cl;
    private GUIHoofdMenu guiHoofdMenu;
    private JPanel panelClosing;

    public GUIFrame(final GUIHoofdMenu guiHoofdMenu) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Image iconLoading = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/dots-logo.png")).getImage();
        super.setIconImage(iconLoading);
        cl = new CardLayout();
        setLayout(cl);
        this.guiHoofdMenu = guiHoofdMenu;
        //this.splashScreen = splashScreen;
        super.setSize(550, 500);
        setLocationRelativeTo(null);

        panelClosing = new JPanel();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                guiHoofdMenu.getController().getSettings().saveSettings();
                if (JOptionPane.showConfirmDialog(panelClosing, "Bent u zeker dat u wilt afsluiten?", "Zeker sluiten?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.err.println("Exiting application");
                    System.exit(0);
                }
            }
        });
    }

    public void showFrame(){
        super.setVisible(true);
        super.requestFocus();
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
            case "gameEndPanel":
                setGlassPane(new GUIGameEnd(getContentPane(), guiHoofdMenu));
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
