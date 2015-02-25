package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Jens on 24-2-2015.
 */
public class GUIPauzePane extends JPanel {
    private Container contentPane;
    //private GUIFrame guiFrame;
    private JButton btnContinue, btnGoHome;
    private ImageIcon iconPauze, iconHome;
    private JLabel lblPauze, lblHome;
    private String modus;
    private SpelController controller;

    public GUIPauzePane(Container contentPane, SpelController controller) {
        this.contentPane = contentPane;
        this.controller = controller;
        //this.guiFrame = guiFrame;
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        this.modus = "";
        makeComponents();
        makeLayout();
        MakeEventListener();
    }

    private void makeComponents() {
        iconPauze = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnPauze.png")), 50, 50));
        lblPauze = new JLabel("");
        lblPauze.setName("pauze");
        lblPauze.setIcon(iconPauze);

        iconHome = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnHome.png")), 250, 50));
        lblHome = new JLabel("");
        lblHome.setIcon(iconHome);
    }

    public static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return image;
    }


    public void makeLayout() {
        add(lblPauze);
        add(lblHome);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        contentPane.paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void MakeEventListener() {
        addMouseListener(new MouseAdapter() {
        });
        lblPauze.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                setVisible(false);
            }
        });
        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Mouse release detected on Home button");
                eindigSpel();
                setVisible(false);
            }
        });
    }

    public void eindigSpel() {
        controller.getHighscore().addHighScore(modus);
        controller.getGuiFrame().updateFrame("hoofdMenu");
    }
}
