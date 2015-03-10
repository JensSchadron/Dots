package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GUIHighScore extends GUIGlassPane {
    private JButton btnClose;
    private JTabbedPane tabbedPane;
    private JTextArea txtTime, txtInfinity, txtMove;
    private GUIHoofdMenu guiHoofdMenu;

    public GUIHighScore(Container contentPane, GUIHoofdMenu guiHoofdMenu) {
        super(contentPane);
        setLayout(new GridLayout(1, 1));
        this.guiHoofdMenu = guiHoofdMenu;
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnClose = new JButton("Sluiten");
        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);

        JPanel panelTime = new JPanel(new FlowLayout());
        JPanel panelInfinty = new JPanel(new FlowLayout());
        JPanel panelMove = new JPanel(new FlowLayout());

        panelTime.setOpaque(false);
        panelInfinty.setOpaque(false);
        panelMove.setOpaque(false);
        JScrollPane scrollPaneTime = new JScrollPane(panelTime);
        JScrollPane scrollPaneInfinity = new JScrollPane(panelInfinty);
        JScrollPane scrollPaneMove = new JScrollPane(panelMove);
        scrollPaneTime.setOpaque(false);
        scrollPaneTime.getViewport().setOpaque(false);
        scrollPaneTime.setBorder(null);
        scrollPaneInfinity.setOpaque(false);
        scrollPaneInfinity.getViewport().setOpaque(false);
        scrollPaneInfinity.setBorder(null);
        scrollPaneMove.setOpaque(false);
        scrollPaneMove.getViewport().setOpaque(false);
        scrollPaneMove.setBorder(null);
        tabbedPane.addTab("Time", scrollPaneTime);
        tabbedPane.addTab("Move", scrollPaneMove);
        tabbedPane.addTab("Infinity", scrollPaneInfinity);

        txtTime = new JTextArea(guiHoofdMenu.getController().getHighscore().getHighScores("Time"));
        txtTime.setOpaque(false);
        txtTime.setEditable(false);
        txtTime.setHighlighter(null);
        txtTime.setMargin(new Insets(10, 10, 10, 10));
        panelTime.add(txtTime);
        txtInfinity = new JTextArea(guiHoofdMenu.getController().getHighscore().getHighScores("Infinity"));
        txtInfinity.setOpaque(false);
        txtInfinity.setEditable(false);
        txtInfinity.setHighlighter(null);
        txtInfinity.setMargin(new Insets(10, 10, 10, 10));
        panelInfinty.add(txtInfinity);
        txtMove = new JTextArea(guiHoofdMenu.getController().getHighscore().getHighScores("Move"));
        txtMove.setOpaque(false);
        txtMove.setEditable(false);
        txtMove.setHighlighter(null);
        txtMove.setMargin(new Insets(10, 10, 10, 10));
        panelMove.add(txtMove);
    }

    private void MakeLayout() {
        btnClose.setForeground(Color.BLUE);
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        panelButton.setOpaque(false);
        panelButton.add(btnClose);
        mainPanel.add(panelButton, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
        btnClose.setBounds(40, getHeight() / 2, 350, 30);
        tabbedPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        tabbedPane.setVisible(true);
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }
        });

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/fonts/UbuntuMono-R.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(15f);
            tabbedPane.setFont(font);
            txtInfinity.setFont(font);
            txtTime.setFont(font);
            txtMove.setFont(font);
            btnClose.setFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
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
