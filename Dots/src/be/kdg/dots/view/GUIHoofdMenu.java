package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jens & alexander on 4/02/2015.
 */
public class GUIHoofdMenu extends JPanel {
    private SpelController controller;
    private JPanel main, gameMode;
    private JButton settings, about;
    JLabel timeMode, endlessMode, moveMode;
    private JLabel banner, highscore;

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
        ImageIcon iconTimed = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnTimed.png"));
        iconTimed = new ImageIcon(resize(iconTimed, 120,120));
        timeMode = new JLabel("", JLabel.CENTER);
        timeMode.setIcon(iconTimed);

        ///be/kdg/dots/resources/
        ImageIcon iconEndless = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnInfinity.png"));
        iconEndless = new ImageIcon(resize(iconEndless, 120,120));
        endlessMode = new JLabel("", JLabel.CENTER);
        endlessMode.setIcon(iconEndless);

        banner = new JLabel("Dots");
        banner.setForeground(new Color(83, 93, 245));
        banner.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        /*moveMode.setBorder(new RoundedBorder(20));
        moveMode.setBackground(new Color(45, 24, 185));
        moveMode.setForeground(Color.white);
        moveMode.setFocusPainted(false);*/

        try {
            //../fonts/
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/be/kdg/dots/resources/fonts/dotness.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(120f);
            banner.setFont(font);
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
        gameMode.setSize(50, 50);
        main.setBackground(Color.white);
        gameMode.setBackground(Color.white);
        gameMode.add(timeMode);
        //gameMode.add(moveMode);
        gameMode.add(endlessMode);
        main.add(banner, BorderLayout.NORTH);
        main.add(gameMode, BorderLayout.CENTER);
        super.add(main);
        super.revalidate();
    }

    private void MakeEventListener() {
        timeMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Time mode selected");
                setVisible(false);
                controller.startSpel("Time");
            }
        });

        endlessMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Endless mode selected");
                setVisible(false);
                controller.startSpel("Endless");
            }
        });
        /*
        timeMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controller.startSpel();
            }
        });
        moveMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controller.startSpel();
            }
        });*/
    }


}
