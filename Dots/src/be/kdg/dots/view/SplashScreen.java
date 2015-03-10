package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SplashScreen extends JWindow {
    public SplashScreen(final GUIHoofdMenu guiHoofdMenu) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JLabel imageLabel = new JLabel(new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/hoofdmenu/logo-dots.png")).getImage(), 400, 150)));
        setSize(screenSize);
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());
        setBackground(new Color(0,0,0,0));
        add(imageLabel);
        setVisible(true);

        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiHoofdMenu.getController().isLadenCompleet()) {
                    SplashScreen.this.dispose();
                    guiHoofdMenu.getGuiFrame().showFrame();
                    ((Timer) e.getSource()).stop();
                } else {
                    ((Timer) e.getSource()).setDelay(500);
                }
            }
        });
        timer.start();
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}
