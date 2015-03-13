package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIAchievement extends JWindow {
    private final Rectangle maxBounds;
    private static final int DISTANCE_FROM_BORDER = 10;
    private static final int ACHIEVEMENT_WINDOW_WIDTH = 300;
    private static final int ACHIEVEMENT_WINDOW_HEIGHT = 100;

    public GUIAchievement(String achievement) {
        maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setSize(ACHIEVEMENT_WINDOW_WIDTH, 0);
        setLayout(new BorderLayout());
        setBackground(new Color(0.5f, 0.5f, 0.5f, 0.7f));
        setAlwaysOnTop(true);

        JLabel tekst = new JLabel();
        tekst.setText("<html><font size =+1><b>Achievement get!</b></font><br><font>" + achievement + "</font></html>");

        tekst.setIcon(new ImageIcon(getClass().getResource("/images/Achievement_icon.png")));
        tekst.setHorizontalTextPosition(SwingConstants.RIGHT);
        tekst.setOpaque(false);

        this.add(tekst);
        scrollIn();
    }

    private void scrollIn() {
        Timer scrollTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSize().getHeight() < ACHIEVEMENT_WINDOW_HEIGHT) {
                    setSize((int) getSize().getWidth(), (int) getSize().getHeight() + 10);
                    setLocation((int) (maxBounds.getWidth() - getSize().getWidth() - DISTANCE_FROM_BORDER), ((int) (maxBounds.getHeight() - getSize().getHeight())));
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        Timer scrollOutDelayTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollOut();
            }
        });
        scrollOutDelayTimer.setRepeats(false);
        scrollTimer.start();
        scrollOutDelayTimer.start();
        setVisible(true);
    }

    private void scrollOut() {
        Timer scrollTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSize().getHeight() > 0) {
                    setSize((int) getSize().getWidth(), (int) getSize().getHeight() - 10);
                    setLocation((int) (maxBounds.getWidth() - getSize().getWidth() - DISTANCE_FROM_BORDER), ((int) (maxBounds.getHeight() - getSize().getHeight())));
                } else {
                    dispose();
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        scrollTimer.start();
    }
}
