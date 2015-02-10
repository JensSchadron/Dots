package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIStartScreen extends JFrame {
    private JPanel main, gameMode;
    private JButton moveMode, timeMode, endlessMode, settings, about;
    private JLabel banner, highscore;

    public GUIStartScreen() throws HeadlessException {
        super("Dots Game");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MakeComponents();
        MakeLayout();
        MakeEventListener();
        super.setSize(400, 300);
        super.setVisible(true);
    }

    private void MakeComponents() {
        moveMode = new JButton("Moves");
        timeMode = new JButton("Timed");
        endlessMode = new JButton("Endless");
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

    }

    private void MakeLayout() {
        main = new JPanel(new BorderLayout());
        gameMode = new JPanel(new GridLayout(2,2));
        main.setBackground(Color.white);
        gameMode.setBackground(Color.white);
        gameMode.add(timeMode);
        gameMode.add(moveMode);
        gameMode.add(endlessMode);
        main.add(banner, BorderLayout.NORTH);
        main.add(gameMode, BorderLayout.CENTER);
        super.add(main);
    }

    private void MakeEventListener() {
        moveMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new GUIView();
            }
        });
    }


}
