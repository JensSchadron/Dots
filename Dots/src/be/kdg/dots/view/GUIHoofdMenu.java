package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by jens & alexander on 4/02/2015.
 */
public class GUIHoofdMenu extends JPanel {
    private SpelController controller;
    private JPanel main, gameMode, southPanel, loginPanel, rightPanel;
    private JLabel lblTimeMode, lblEndlessMode, lblMoveMode, lblSettings,lblBanner, lblHighscore;
    private ImageIcon iconTimed, iconEndless, iconSettings, iconHighscore;
    private JButton btnSettings, btnAbout, btnHelp, btnInloggen;

    public GUIHoofdMenu(SpelController controller) throws HeadlessException {
        setOpaque(true);
        setBackground(Color.white);
        this.controller = controller;
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnSettings = new JButton("Settings");
        btnAbout = new JButton("About");
        btnHelp = new JButton("Help");
        btnInloggen = new JButton("Inloggen");
        btnInloggen.setSize(200,50);

        iconTimed = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnTimed.png")), 120,120));
        lblTimeMode = new JLabel("", JLabel.CENTER);
        lblTimeMode.setIcon(iconTimed);

        iconEndless = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnInfinity.png")), 120,120));
        lblEndlessMode = new JLabel("", JLabel.CENTER);
        lblEndlessMode.setIcon(iconEndless);

        iconSettings = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnSettings.png")), 120,120));
        lblSettings = new JLabel("", JLabel.CENTER);
        lblSettings.setIcon(iconSettings);

        iconHighscore = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnHighscore.png")), 120,120));
        lblHighscore = new JLabel("", JLabel.CENTER);
        lblHighscore.setIcon(iconHighscore);

        lblBanner = new JLabel("Dots");
        lblBanner.setForeground(new Color(83, 93, 245));
        lblBanner.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        /*lblMoveMode.setBorder(new RoundedBorder(20));
        lblMoveMode.setBackground(new Color(45, 24, 185));
        lblMoveMode.setForeground(Color.white);
        lblMoveMode.setFocusPainted(false);*/

        try {
            //../fonts/
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/be/kdg/dots/resources/fonts/dotness.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(120f);
            lblBanner.setFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return newImg;
    }

    private void MakeLayout() {
        main = new JPanel(new BorderLayout());
        gameMode = new JPanel(new GridLayout(2, 2, 10, 10));
        southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 10));
        loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
        rightPanel = new JPanel(new BorderLayout());
        gameMode.setSize(50, 50);
        main.setBackground(Color.white);
        gameMode.setBackground(Color.white);
        loginPanel.setBackground(Color.white);
        gameMode.add(lblTimeMode);
        gameMode.add(lblEndlessMode);
        gameMode.add(lblSettings);
        gameMode.add(lblHighscore);
        southPanel.add(btnAbout);
        southPanel.add(btnSettings);
        southPanel.add(btnHelp);
        rightPanel.add(lblBanner, BorderLayout.CENTER);
        rightPanel.add(loginPanel, BorderLayout.EAST);

        loginPanel.add(btnInloggen, BorderLayout.NORTH);
        main.add(rightPanel, BorderLayout.NORTH);
        main.add(gameMode, BorderLayout.CENTER);
        main.add(southPanel, BorderLayout.SOUTH);
        //main.add(loginPanel, BorderLayout.EAST);
        super.add(main);
        super.revalidate();
    }

    private void MakeEventListener() {
        lblTimeMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Time mode selected");
                setVisible(false);
                controller.startSpel("Time");
            }
        });

        lblEndlessMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Infinity mode selected");
                setVisible(false);
                controller.startSpel("Infinity");
            }
        });

        btnSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Settings selected");
                controller.getGuiFrame().updateFrame("instellingenPanel", controller);
            }
        });
        btnAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - About selected");
                controller.getGuiFrame().updateFrame("aboutPanel", controller);
            }
        });
        btnInloggen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //String username = JOptionPane.showInputDialog("Give a username",JOptionPane.PLAIN_MESSAGE);
                controller.getGuiFrame().updateFrame("login", controller);
                //controller.setSpeler(username);
            }
        });
        lblHighscore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                controller.getGuiFrame().updateFrame("highScorePanel", controller);
            }
        });
        /*
        lblTimeMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controller.startSpel();
            }
        });
        lblMoveMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controller.startSpel();
            }
        });*/
    }


}
