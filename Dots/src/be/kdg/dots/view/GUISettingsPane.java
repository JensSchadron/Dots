package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by jens & alexander on 17/02/2015.
 */
public class GUISettingsPane extends JPanel {
    private Container contentPane;
    private JButton btnSave, btnResetHighscore;
    private JTextArea txtName;
    private JPanel centerPanel;
    private SpelController controller;
    private JSlider slider;

    public GUISettingsPane(Container contentPane, SpelController controller) {
        this.contentPane = contentPane;
        this.controller = controller;
        setLayout(new BorderLayout());
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnSave = new JButton("Save settings");
        btnResetHighscore = new JButton("Reset highscores");
        slider = new JSlider();
        slider.setUI(new SliderUI(slider));
        System.out.println(slider.getValue());


        if (controller.getSpeler().getUsername()==null){
            txtName = new JTextArea("Information:\n\nU bent ingelogd als: Nobody");
        }else{
            txtName = new JTextArea("Information:\n\nU bent ingelogd als: " + controller.getSpeler().getUsername());
        }
        centerPanel = new JPanel(new GridLayout(4,1));
    }

    private void MakeLayout() {
        btnSave.setForeground(Color.BLUE);
        super.add(btnSave, BorderLayout.SOUTH);
        centerPanel.add(txtName);
        centerPanel.add(slider);
        centerPanel.add(btnResetHighscore);
        centerPanel.setOpaque(false);
        txtName.setOpaque(false);
        txtName.setMargin(new Insets(20, 20, 20, 20));
        super.add(centerPanel, BorderLayout.CENTER);
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

        /*AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g.setComposite(solid);
        g.setColor(Color.black);*/
    }

    private void MakeEventListener() {
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
            }
        });
        btnResetHighscore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getHighscore().resetHighScores();
            }
        });
    }

    private static class SliderUI extends BasicSliderUI {

        private static float[] fracs = {0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f};
        private LinearGradientPaint p;

        public SliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle t = trackRect;
            Point2D start = new Point2D.Float(t.x, t.y);
            Point2D end = new Point2D.Float(t.width, t.height);
            Color[] colors = {Color.magenta, Color.blue, Color.cyan,
                    Color.green, Color.yellow, Color.red};
            p = new LinearGradientPaint(start, end, fracs, colors);
            g2d.setPaint(p);
            g2d.fillRect(t.x, t.y, t.width, t.height);

            if (!this.slider.isEnabled()){
                for (int i=0 ; i<colors.length ; i++) {
                    float[] hsv = Color.RGBtoHSB(colors[i].getRed(),colors[i].getGreen(),colors[i].getBlue(),null);
                    System.out.println(colors[i] = new Color(Color.HSBtoRGB(hsv[0], 0.25f, hsv[2])));
                }
            }

        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle t = thumbRect;
            g2d.setColor(Color.black);
            int tw2 = t.width / 2;
            g2d.drawLine(t.x, t.y, t.x + t.width - 1, t.y);
            g2d.drawLine(t.x, t.y, t.x + tw2, t.y + t.height);
            g2d.drawLine(t.x + t.width - 1, t.y, t.x + tw2, t.y + t.height);
        }

    }
}
