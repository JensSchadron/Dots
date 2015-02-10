package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by alexander on 4/02/2015.
 */
public class GUIStartScreen extends JFrame {
    private JPanel main;
    private JButton start;
    private JLabel banner;

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
        start = new JButton("Start game");
        banner = new JLabel("Dots...");

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
        main.add(start, BorderLayout.CENTER);
        main.add(banner, BorderLayout.NORTH);
        super.add(main);
    }

    private void MakeEventListener() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new GUIView();
            }
        });
    }


}
