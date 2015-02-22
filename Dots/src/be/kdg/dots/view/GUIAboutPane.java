package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jens & alexander on 17/02/2015.
 */
public class GUIAboutPane extends JPanel{
    private Container contentPane;
    private JButton btnClose;

    public GUIAboutPane(Container contentPane) {
        super();
        this.contentPane = contentPane;
        setLayout(null);
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnClose = new JButton("Close");
    }

    private void MakeLayout() {
        btnClose.setForeground(Color.BLUE);
        add(btnClose);
        btnClose.setBounds(40, getHeight() / 2, 350, 30);
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        contentPane.paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        g.drawString("Hier komt de about info (Info over onszelf en het spel)", 50, 100);
        g.drawString("test!", 50, 120);
    }

    private void MakeEventListener() {
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
            }
        });

    }
}
