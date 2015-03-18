package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class GUIGameEnd extends GUIGlassPane {

    private JLabel lblWinner, lblScore, lblLevel;
    private final GUIHoofdMenu guiHoofdMenu;
    private JButton btnClose;
    private Boolean gameOver;

    public GUIGameEnd(Container contentPane, GUIHoofdMenu guiHoofdMenu, Boolean gameOver) {
        super(contentPane);
        this.guiHoofdMenu = guiHoofdMenu;
        this.gameOver = gameOver;
        setLayout(new BorderLayout());
        makeComponents();
        makeLayout();
        MakeEventListener();
    }

    private void makeComponents() {
        ImageIcon imageGameover = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/gameOver.png")), 300, 150));
        lblWinner = new JLabel("");
        lblWinner.setIcon(imageGameover);

        lblScore = new JLabel("Proficiat, u hebt een score van " + Integer.toString(guiHoofdMenu.getController().getSpeler().getScore().getScore()));
        lblLevel = new JLabel("en level " + Integer.toString(guiHoofdMenu.getController().getSpeler().getLevel().getLevel()) + " behaald");
        lblScore.setFont(new Font("Serif", Font.PLAIN, 25));
        lblLevel.setFont(new Font("Serif", Font.PLAIN, 25));

        btnClose = new JButton("Close");
    }

    private static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return image;
    }


    void makeLayout() {
        JPanel panel = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panelLabels = new JPanel(new GridLayout(3, 1));
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panelLabels.setOpaque(false);
        panelButton.setOpaque(false);

        btnClose.setForeground(Color.BLUE);
        btnClose.setBounds(40, getHeight() / 2, 350, 30);
        panelButton.add(btnClose);
        panel.add(lblWinner);
        panel2.add(lblScore);
        panel3.add(lblLevel);

        panelLabels.add(panel);
        panelLabels.add(panel2, BOTTOM_ALIGNMENT);
        panelLabels.add(panel3, TOP_ALIGNMENT);

        add(panelLabels, BorderLayout.CENTER);
        add(panelButton, BorderLayout.NORTH);
    }

    private void MakeEventListener() {
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
                guiHoofdMenu.getGuiFrame().updateFrame("hoofdMenu");
            }
        });
    }
}
