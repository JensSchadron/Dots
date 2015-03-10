package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame {
    private static final int MIN_GAME_WIDTH = 450;
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
        setMinimumSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(500, 500));
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

    public void showFrame() {
        super.setVisible(true);
        super.requestFocus();
    }

    public void updateFrame(String optie) {
        Dimension minSize = new Dimension(getMinimumSize());
        Dimension prefSize = new Dimension(getPreferredSize());
        switch (optie) {
            case "hoofdMenu":
                cl.show(this.getContentPane(), "hoofdMenu");
                minSize = new Dimension(500, 500);
                prefSize = new Dimension(500, 500);
                break;
            case "startSpel":
                cl.show(this.getContentPane(), "startSpel");
                int width = (80 * guiHoofdMenu.getController().getSettings().getColumn()<MIN_GAME_WIDTH)?MIN_GAME_WIDTH:80 * guiHoofdMenu.getController().getSettings().getColumn();
                minSize = new Dimension(width, 150 + 70 * guiHoofdMenu.getController().getSettings().getRow());
                prefSize = new Dimension(width, 150 + 70 * guiHoofdMenu.getController().getSettings().getRow());
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
            case "helpPanel":
                setGlassPane(new GUIHelpPane(getContentPane()));
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
        setLocationRelativeTo(null);
        setMinimumSize(minSize);
        setPreferredSize(prefSize);
    }
}
