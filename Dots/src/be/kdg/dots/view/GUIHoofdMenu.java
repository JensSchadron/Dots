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
    private JPanel main, gameMode;
    private JButton btnAbout;
    JLabel lblTimeMode, lblEndlessMode, lblMoveMode, lblSettings;
    private JLabel lblBanner, lblHighscore;
    private ImageIcon iconTimed, iconEndless, iconSettings;

    public GUIHoofdMenu(SpelController controller) throws HeadlessException {
        setOpaque(true);
        setBackground(Color.white);
        this.controller = controller;
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        ///be/kdg/dots/resources/
        iconTimed = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnTimed.png")), 120,120));
        lblTimeMode = new JLabel("", JLabel.CENTER);
        lblTimeMode.setIcon(iconTimed);

        ///be/kdg/dots/resources/
        iconEndless = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnInfinity.png")), 120,120));
        lblEndlessMode = new JLabel("", JLabel.CENTER);
        lblEndlessMode.setIcon(iconEndless);

        iconSettings = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnSettings.png")), 120,120));
        lblSettings = new JLabel("", JLabel.CENTER);
        lblSettings.setIcon(iconSettings);

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
        gameMode.setSize(50, 50);
        main.setBackground(Color.white);
        gameMode.setBackground(Color.white);
        gameMode.add(lblTimeMode);
        gameMode.add(lblEndlessMode);
        gameMode.add(lblSettings);
        main.add(lblBanner, BorderLayout.NORTH);
        main.add(gameMode, BorderLayout.CENTER);
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

        lblSettings.addMouseListener(new MouseAdapter() {
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
