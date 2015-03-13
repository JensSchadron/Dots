package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIFrame extends JFrame {
    private static final int MIN_GAME_WIDTH = 450;
    private final CardLayout cl;
    private final GUIHoofdMenu guiHoofdMenu;
    private final JPanel panelClosing;

    public GUIFrame(final GUIHoofdMenu guiHoofdMenu) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Image iconLoading = new ImageIcon(getClass().getResource("/images/dots-logo.png")).getImage();
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
                if (JOptionPane.showConfirmDialog(panelClosing, "Bent u zeker dat u wilt afsluiten?", "Zeker sluiten?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(getClass().getResource("/images/meldingen/Icon - info.png"))) == JOptionPane.YES_OPTION) {
                    //System.err.println("Exiting application");
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
        switch (optie) {
            case "hoofdMenu":
                cl.show(this.getContentPane(), "hoofdMenu");
                setMinimumSize(new Dimension(500, 500));
                setPreferredSize(new Dimension(500, 500));
                setLocationRelativeTo(null);
                break;
            case "startSpel":
                cl.show(this.getContentPane(), "startSpel");
                int width = (80 * guiHoofdMenu.getController().getSettings().getColumn() < MIN_GAME_WIDTH) ? MIN_GAME_WIDTH : 80 * guiHoofdMenu.getController().getSettings().getColumn();
                setMinimumSize(new Dimension(width, 150 + 70 * guiHoofdMenu.getController().getSettings().getRow()));
                setPreferredSize(new Dimension(width, 150 + 70 * guiHoofdMenu.getController().getSettings().getRow()));
                setLocationRelativeTo(null);
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
                setGlassPane(new GUIAboutPane(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;
            case "helpPanel":
                setGlassPane(new GUIHelpPane(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;
            case "highScorePanel":
                setGlassPane(new GUIHighScore(getContentPane(), guiHoofdMenu));
                getGlassPane().setVisible(true);
                break;
            case "gameEndPanel":
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setGlassPane(new GUIGameEnd(getContentPane(), guiHoofdMenu, true));
                        getGlassPane().setVisible(true);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                break;
        }
    }

    public void toonAchievement(String achievement) {
        new GUIAchievement(achievement);
    }

    public void toonFoutBoodschap(String foutmelding, boolean severe) {
        String boodschap = foutmelding + ((severe) ? " Om verdere fouten ten gevolge van deze fout in het spel te voorkomen wordt Dots afgesloten" : "") + "  Als dit probleem zich blijft voordoen, gelieve ons dan te contacteren.";
        JOptionPane.showMessageDialog(this, boodschap, "Oeps...", JOptionPane.ERROR_MESSAGE, new ImageIcon(GUIFrame.class.getResource("/images/Icon - error.png")));
        if (severe) {
            System.exit(1);
        }
    }
}
