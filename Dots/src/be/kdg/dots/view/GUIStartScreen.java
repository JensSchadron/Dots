package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIStartScreen extends JFrame {
    private SpelController controller;
    private JPanel main, gameMode;
    private JButton moveMode, settings, about;
    JLabel timeMode, endlessMode;
    private JLabel banner, highscore;

    public GUIStartScreen(SpelController controller) throws HeadlessException {
        super("Dots Game");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        this.controller = controller;
        MakeComponents();
        MakeLayout();
        MakeEventListener();
        super.setSize(400, 300);
        super.setVisible(true);
    }

    private void MakeComponents() {
        //timeMode = new JButton("Timed");
        //endlessMode = new JButton("Endless");

        ImageIcon iconTimed = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/btnTimed.png"));
        timeMode = new JLabel("Timed mode",JLabel.CENTER);
        timeMode.setIcon(iconTimed);

        ImageIcon iconEndless = new ImageIcon(getClass().getResource("/be/kdg/dots/resources/btnInfinity.png"));
        endlessMode = new JLabel("Endless mode", JLabel.CENTER);
        endlessMode.setIcon(iconEndless);

        moveMode = new JButton("Moves");

        banner = new JLabel("Dots");
        banner.setForeground(new Color(83,93,245));
        banner.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        /*moveMode.setBorder(new RoundedBorder(20));
        moveMode.setBackground(new Color(45, 24, 185));
        moveMode.setForeground(Color.white);
        moveMode.setFocusPainted(false);*/

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("../fonts/dotness.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(120f);
            banner.setFont(font);
        } catch (IOException|FontFormatException e) {
            e.printStackTrace();
        }

    }

    private void MakeLayout() {
        main = new JPanel(new BorderLayout());
        gameMode = new JPanel(new GridLayout(1,2));
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
                controller.startSpel();
            }
        });

        endlessMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Endless mode selected");
                setVisible(false);
                controller.startSpel();
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
