package be.kdg.dots.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by alexander on 5/03/2015.
 */
public class ProgressBar extends JPanel{

        private final int minValue, maxValue;
        private boolean indeterminate = false;
        private int currentValue;
        private ArrayList<Rectangle> rects = new ArrayList<>();
        private Color PROGRESS_COLOR = Color.blue;
        private int removeValue = 0;
        private Timer indeterminateTimer;
        private int x = 0, y = 0, ballSize = 25;
        private boolean changeDirection = false;

        public ProgressBar(int min, int max) {
            indeterminateTimer = new Timer(50, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    repaint();
                }
            });
            maxValue = max;
            minValue = min;
            currentValue = maxValue;
            setBorder(new LineBorder(Color.BLACK));
        }

        @Override
        protected void paintComponent(Graphics grphcs) {
            super.paintComponent(grphcs);
            Graphics2D g2d = (Graphics2D) grphcs;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (!indeterminate) {
                rects.clear();
                int rectWidths = getWidth() / maxValue;
                int startingX = 0;

                if (currentValue < maxValue) {
                    for (int i = minValue; i < currentValue; i++) {
                        rects.add(new Rectangle(startingX, 0, rectWidths, getHeight()));
                        startingX += rectWidths;
                    }
                } else {
                    for (int i = minValue; i < (maxValue - removeValue); i++) {
                        rects.add(new Rectangle(startingX, 0, rectWidths, getHeight()));
                        startingX += rectWidths;
                    }
                }

                for (Rectangle r : rects) {
                    g2d.setColor(PROGRESS_COLOR);
                    g2d.fillRect(r.x, r.y, r.width, r.height);
                }
            } else {
                if (!indeterminateTimer.isRunning()) {
                    indeterminateTimer.start();
                }
                g2d.setColor(PROGRESS_COLOR);
                if (!changeDirection) {
                    if (x + 10 < getWidth() - (ballSize / 2)) {
                        x += 10;
                    } else {
                        changeDirection = true;
                    }
                } else if (changeDirection) {
                    if (x + 10 > 0) {
                        x -= 10;
                    } else {
                        changeDirection = false;
                    }
                }
                g2d.fillOval(x, y, ballSize, getHeight());
            }
        }
}
