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
    private JPanel main, gameMode, setting;
    private JButton btnSettings, btnAbout;
    JLabel lblTimeMode, lblEndlessMode, lblMoveMode;
    private JLabel lblBanner, lblHighscore;

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

        ///be/kdg/dots/resources/
        ImageIcon iconTimed = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnTimed.png"));
        iconTimed = new ImageIcon(resize(iconTimed, 120,120));
        lblTimeMode = new JLabel("", JLabel.CENTER);
        lblTimeMode.setIcon(iconTimed);

        ///be/kdg/dots/resources/
        ImageIcon iconEndless = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnInfinity.png"));
        iconEndless = new ImageIcon(resize(iconEndless, 120,120));
        lblEndlessMode = new JLabel("", JLabel.CENTER);
        lblEndlessMode.setIcon(iconEndless);

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
        gameMode = new JPanel(new GridLayout(1, 2));
        setting = new JPanel(new BorderLayout());
        gameMode.setSize(50, 50);
        main.setBackground(Color.white);
        gameMode.setBackground(Color.white);
        gameMode.add(lblTimeMode);
        gameMode.add(lblEndlessMode);
        setting.add(btnSettings);
        main.add(lblBanner, BorderLayout.NORTH);
        main.add(gameMode, BorderLayout.CENTER);
        main.add(setting, BorderLayout.SOUTH);
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
                //new Test();
            }
        });

        lblEndlessMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Endless mode selected");
                setVisible(false);
                controller.startSpel("Endless");
            }
        });

        btnSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Settings selected");
                controller.setclassPane();
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
