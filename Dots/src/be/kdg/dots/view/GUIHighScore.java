package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by alexander on 23/02/2015.
 */
public class GUIHighScore extends JPanel {
    private Container contentPane;
    private JButton btnClose;
    private JPanel panelTest;
    private JTabbedPane tabbedPane;

    public GUIHighScore(Container contentPane) {
        super();
        this.contentPane = contentPane;
        setLayout(new GridLayout(1,1));
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnClose = new JButton("Close");
        tabbedPane = new JTabbedPane(SwingConstants.TOP);
        panelTest = new JPanel(new FlowLayout());

        panelTest.add(new JButton("test"));
        tabbedPane.addTab("Time", panelTest);
    }

    private void MakeLayout() {
        btnClose.setForeground(Color.BLUE);
        //add(btnClose);
        add(tabbedPane);
        btnClose.setBounds(40, getHeight() / 2, 350, 30);
        tabbedPane.setBounds(0,0, this.getWidth(), this.getHeight());
        tabbedPane.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        contentPane.paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        g.drawString("Highscore", 50, 100);
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
