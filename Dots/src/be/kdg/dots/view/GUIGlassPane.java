package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexander on 17/02/2015.
 */
public class GUIGlassPane extends JPanel implements ActionListener {
    private Container contentPane;
    private boolean inited;

    public GUIGlassPane(Container contentPane) {
        this.contentPane = contentPane;
        setLayout(null);

    }

    private void init() {
        inited = true;
        JLabel label = new JLabel("test2:");
        label.setForeground(Color.blue);
        add(label);
        label.setBounds(40, getHeight()/2, 350, 20);

    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);

        if(!inited) {
            init();
        }

        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        //draw the contents of the JFrame's content pane upon our glass pane.
        contentPane.paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        g.drawString("Hier komen de settings", 50, 100);
        g.drawString("test!", 50, 120);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }
}
