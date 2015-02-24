package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

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
    private JPanel panelTime, panelInifinty;
    private JTabbedPane tabbedPane;
    private JTextArea txtTime, txtInfinity;
    private SpelController controller;

    public GUIHighScore(Container contentPane, SpelController controller) {
        super();
        this.contentPane = contentPane;
        setLayout(new GridLayout(1,1));
        this.controller = controller;
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnClose = new JButton("Close");
        btnClose.setSize(50,50);
        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);

        panelTime = new JPanel(new FlowLayout());
        panelInifinty =new JPanel(new FlowLayout());
        panelTime.setOpaque(false);
        panelInifinty.setOpaque(false);
        tabbedPane.addTab("Time", panelTime);
        tabbedPane.addTab("Infinity", panelInifinty);

        txtTime = new JTextArea(controller.getHighscore().getTimeHighScore());
        txtTime.setOpaque(false);
        panelTime.add(txtTime);
        txtInfinity = new JTextArea(controller.getHighscore().getInfinityHighScore());
        txtInfinity.setOpaque(false);
        panelInifinty.add(txtInfinity);
    }

    private void MakeLayout() {
        btnClose.setForeground(Color.BLUE);
        //add(btnClose);
        add(tabbedPane);
        btnClose.setBounds(getWidth(), 0, 350, 30);
        tabbedPane.setBounds(0,0, this.getWidth(), this.getHeight());
        tabbedPane.setVisible(true);
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI(){
            protected void paintContentBorder(Graphics g,int tabPlacement,int selectedIndex){}
        });
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        //tabbedPane.paintComponents(gr);
        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        contentPane.paint(gr);
        //tabbedPane.paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        //g.drawString("Highscore", 50, 100);
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
