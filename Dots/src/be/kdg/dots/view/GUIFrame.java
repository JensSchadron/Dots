package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
                    System.exit(0);
                }
            }
        });
    }

    public void showFrame() {
        super.setVisible(true);
        super.requestFocus();
    }

    public void toonUpdateDialog() {
        if (JOptionPane.showConfirmDialog(this, "Er is mogelijk een nieuwe versie beschikbaar. Wilt u deze downloaden?", "Nieuwe versie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/images/meldingen/Icon - info.png"))) == JOptionPane.YES_OPTION) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/JensSchadron/Dots/blob/master/out/artifacts/Dots_jar/Dots.jar?raw=true"));
            } catch (IOException | URISyntaxException e) {
                toonFoutBoodschap("De nieuwe versie kan momenteel niet worden gedownload, probeer dit later nog eens.", false);
            }
        }
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
                Timer timer = new Timer(500, new ActionListener() {
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
        String boodschap = foutmelding + ((severe) ? "\nOm verdere fouten ten gevolge van deze fout in het spel te voorkomen wordt Dots afgesloten." : "") + "\nAls dit probleem zich blijft voordoen, gelieve ons dan te contacteren.";
        JOptionPane.showMessageDialog(this, boodschap, "Oeps...", JOptionPane.ERROR_MESSAGE, new ImageIcon(GUIFrame.class.getResource("/images/meldingen/Icon - error.png")));
        if (severe) {
            System.exit(1);
        }
    }
}
