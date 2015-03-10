package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by jens & alexander on 4/02/2015.
 */
public class GUIHoofdMenu extends JPanel {
    private final SplashScreen splashScreen;
    private SpelController controller;
    private GUIFrame guiFrame;
    private GUISpel guiSpel;
    private JPanel main, gameMode, southPanel, loginPanel;
    private JLabel lblTimeMode, lblEndlessMode, lblMoveMode, lblBanner, lblHighscore;
    private ImageIcon iconTimed, iconEndless, iconMove, iconHighscore, iconBanner;
    private JButton btnSettings, btnAbout, btnHelp;

    public GUIFrame getGuiFrame() {
        return guiFrame;
    }

    public GUISpel getGuiSpel() {
        return guiSpel;
    }

    public SpelController getController() {
        return controller;
    }

    public GUIHoofdMenu(SpelController controller) throws HeadlessException {
        setOpaque(true);
        setBackground(Color.white);
        this.controller = controller;

        guiFrame = new GUIFrame(this);
        splashScreen = new SplashScreen(this);
        guiFrame.getContentPane().add("hoofdMenu", this);

        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnSettings = new JButton("Settings");
        btnAbout = new JButton("About");
        btnHelp = new JButton("Help");

        iconTimed = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/images/hoofdmenu/knop-groen.png")).getImage(), 120, 120));
        lblTimeMode = new JLabel("", JLabel.RIGHT);
        lblTimeMode.setIcon(iconTimed);

        iconEndless = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/images/hoofdmenu/knop-paars.png")).getImage(), 120, 120));
        lblEndlessMode = new JLabel("", JLabel.RIGHT);
        lblEndlessMode.setIcon(iconEndless);

        iconMove = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/images/hoofdmenu/knop-roos.png")).getImage(), 120, 120));
        lblMoveMode = new JLabel("", JLabel.LEFT);
        lblMoveMode.setIcon(iconMove);

        iconHighscore = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/images/hoofdmenu/knop-blauw.png")).getImage(), 120, 120));
        lblHighscore = new JLabel("", JLabel.LEFT);
        lblHighscore.setIcon(iconHighscore);

        iconBanner = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/images/hoofdmenu/logo-dots.png")).getImage(), 400, 150));
        lblBanner = new JLabel("", JLabel.CENTER);
        lblBanner.setIcon(iconBanner);

        lblTimeMode.setOpaque(false);
        lblEndlessMode.setOpaque(false);
        lblMoveMode.setOpaque(false);

    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    private void MakeLayout() {
        main = new JPanel(new BorderLayout());
        gameMode = new JPanel(new GridLayout(2, 2, 10, 10));
        southPanel = new JPanel(new GridLayout(1, 4));
        loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        gameMode.setSize(50, 50);
        main.setOpaque(false);
        gameMode.setOpaque(false);
        loginPanel.setOpaque(false);
        gameMode.add(lblTimeMode);
        gameMode.add(lblMoveMode);
        gameMode.add(lblEndlessMode);
        gameMode.add(lblHighscore);
        southPanel.add(btnAbout);
        southPanel.add(btnSettings);
        southPanel.add(btnHelp);
        main.add(lblBanner, BorderLayout.NORTH);
        main.add(gameMode, BorderLayout.CENTER);
        main.add(southPanel, BorderLayout.SOUTH);
        super.add(main);
        super.revalidate();
    }

    public void setBackgroundColor(Color color) {
        this.setBackground(color);
    }

    private void startSpel(String modus) {
        guiSpel = new GUISpel(controller, guiFrame, modus);
        guiFrame.getContentPane().add("startSpel", guiSpel);
    }

    private void MakeEventListener() {
        lblTimeMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
                checkSpeler();
                startSpel("Time");
                controller.startSpel("Time");
            }
        });

        lblEndlessMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
                checkSpeler();
                startSpel("Infinity");
                controller.startSpel("Infinity");
            }
        });

        lblMoveMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
                checkSpeler();
                startSpel("Move");
                controller.startSpel("Move");
            }
        });

        btnSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guiFrame.updateFrame("instellingenPanel");
            }
        });
        btnAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guiFrame.updateFrame("aboutPanel");
            }
        });
        btnHelp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guiFrame.updateFrame("helpPanel");
                controller.getSettings().addAchievements("Missing common sense...");
            }
        });
        lblHighscore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guiFrame.updateFrame("highScorePanel");
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
    }

    public void checkSpeler() {
        if (controller.getSpeler().getUsername() == null) {
            String userName;
            do {
                userName = JOptionPane.showInputDialog(this, "Gelieve een username op te geven tussen de 2 tot 20 karakters", "InfoBox: " + "Username", JOptionPane.INFORMATION_MESSAGE);
                if (userName == null) {
                    return;
                }
            } while (userName.length() < 2 || userName.length() > 20);
            controller.getSpeler().setUsername(userName);
        }
    }


}
