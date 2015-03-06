package be.kdg.dots.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Hashtable;

/**
 * Created by jens & alexander on 17/02/2015.
 */
public class GUISettingsPane extends JPanel {
    private Container contentPane;
    private JButton btnSave, btnResetHighscore, btnResetUsername;
    private JTextArea txtName, txtTestColor;
    private JPanel centerPanel, gridPanel, panelSouth, panelButton;
    private GUIHoofdMenu guiHoofdMenu;
    private JSlider sliderColor, sliderVeld;
    private SliderUI sliderUI;
    private JOptionPane optionPane;
    private JCheckBox checkBox;

    public GUISettingsPane(Container contentPane, GUIHoofdMenu guiHoofdMenu) {
        this.contentPane = contentPane;
        this.guiHoofdMenu = guiHoofdMenu;
        setLayout(new BorderLayout());
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnSave = new JButton("Opslaan settings");
        btnResetHighscore = new JButton("Reset highscores");
        btnResetUsername = new JButton("Nieuwe username");
        checkBox = new JCheckBox("Hints tonen");
        sliderVeld = new JSlider(2, 8, guiHoofdMenu.getController().getVeld().getRow());
        sliderVeld.setMinorTickSpacing(1);
        sliderVeld.setPaintLabels(true);
        sliderVeld.setPaintTicks(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(2), new JLabel("2X2"));
        labelTable.put(new Integer(4), new JLabel("4X4"));
        labelTable.put(new Integer(6), new JLabel("6X6"));
        labelTable.put(new Integer(8), new JLabel("8X8"));
        sliderVeld.setLabelTable(labelTable);
        sliderVeld.setOpaque(false);
        sliderColor = new JSlider();
        sliderColor.setUI(sliderUI = new SliderUI(sliderColor));
        sliderColor.setOpaque(false);
        txtTestColor = new JTextArea("");

        updateUserInfo();
        centerPanel = new JPanel(new GridLayout(4, 1));
        gridPanel = new JPanel(new GridLayout(1, 3));
        panelSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSouth.setOpaque(false);
        txtName.setLineWrap(true);
        txtName.setWrapStyleWord(true);
        txtName.setOpaque(false);
        txtName.setEditable(false);
        txtName.setHighlighter(null);
        txtName.setMargin(new Insets(10, 10, 10, 10));
    }

    private void updateUserInfo(){
        if (guiHoofdMenu.getController().getSpeler().getUsername() == null) {
            txtName = new JTextArea("Information:\n\nU bent niet ingelogd.");
        } else {
            txtName = new JTextArea("Information:\n\nU bent ingelogd als: " + guiHoofdMenu.getController().getSpeler().getUsername());
        }
    }

    private void MakeLayout() {
        btnSave.setForeground(Color.BLUE);
        panelSouth.add(txtName);
        panelSouth.add(btnResetUsername);
        panelSouth.add(btnResetHighscore);
        panelSouth.add(checkBox);
        gridPanel.add(txtTestColor);
        centerPanel.add(panelSouth);
        centerPanel.add(sliderColor);
        centerPanel.add(gridPanel);
        centerPanel.add(sliderVeld);
        centerPanel.setOpaque(false);
        txtName.setOpaque(false);
        txtName.setMargin(new Insets(20, 20, 20, 20));
        super.add(centerPanel, BorderLayout.CENTER);
        super.add(btnSave, BorderLayout.SOUTH);
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
    }

    private void MakeEventListener() {
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
                guiHoofdMenu.getController().getSettings().setColumn(sliderVeld.getValue());
                guiHoofdMenu.getController().getSettings().setRow(sliderVeld.getValue());
                guiHoofdMenu.getController().setNewVeld();
                //guiHoofdMenu.setBackground(sliderUI.getColors()[sliderColor.getValue() / 4]);
                guiHoofdMenu.getController().getSettings().setBackgroundColor(sliderUI.getColors()[sliderColor.getValue() / 4]);
                guiHoofdMenu.getController().saveSettings();
            }
        });
        btnResetHighscore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiHoofdMenu.getController().getHighscore().resetHighScores();
            }
        });
        sliderColor.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                txtTestColor.setBackground(sliderUI.getColors()[sliderColor.getValue() / 4]);
            }
        });
        btnResetUsername.addActionListener(new ActionListener() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   /*optionPane = new JOptionPane();
                                                   optionPane.setInputValue("");*/
                                                   String text;
                                                   do {
                                                       text = JOptionPane.showInputDialog(null, "Gelieve een username op te geven langer dan 2 karakters", "InfoBox: " + "Username", JOptionPane.INFORMATION_MESSAGE);
                                                       if(text == null){
                                                           return;
                                                       }
                                                   } while (text.length() < 2 || text.length() > 20);
                                                   guiHoofdMenu.getController().getSpeler().setUsername(text);
                                                   guiHoofdMenu.getController().getSettings().saveSettings();
                                                   updateUserInfo();
                                                   repaint();
                                               }
                                           }

        );

    }

    private static class SliderUI extends BasicSliderUI {

        private float[] fracs;
        private LinearGradientPaint p;

        public SliderUI(JSlider slider) {
            super(slider);
        }

        public final Color[] getColors() {
            return p.getColors();
        }

        @Override
        public void paintTrack(Graphics g) {
            fracs = new float[25];
            int teller = 0;
            for (double i = 0; i < 25; i++) {
                fracs[teller++] = (float) ((i) / 25);
            }

            Graphics2D g2d = (Graphics2D) g;
            Rectangle t = trackRect;
            Point2D start = new Point2D.Float(t.x, t.y);
            Point2D end = new Point2D.Float(t.width, t.height);
            //Color[] colors = {Color.magenta, Color.blue, Color.cyan, Color.green, Color.yellow, Color.red};
            Color[] colors2 = {Color.white, new Color(120, 0, 228), new Color(103, 0, 227), new Color(80, 0, 226), new Color(60, 0, 225), new Color(40, 0, 224), new Color(0, 75, 221), new Color(0, 106, 219), new Color(0, 137, 218), new Color(0, 168, 217), new Color(0, 213, 169), new Color(0, 212, 137), new Color(0, 210, 90), new Color(0, 207, 0), new Color(59, 204, 0), new Color(88, 203, 0), new Color(131, 201, 0), new Color(173, 200, 0), new Color(198, 196, 0), new Color(197, 166, 0), new Color(195, 122, 0), new Color(195, 107, 0), new Color(194, 79, 0), new Color(192, 50, 0), Color.black};
            p = new LinearGradientPaint(start, end, fracs, colors2);
            g2d.setPaint(p);
            g2d.fillRect(t.x, t.y, t.width, t.height);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle t = thumbRect;
            g2d.setColor(Color.black);
            int tw2 = t.width / 2;
            g2d.drawLine(t.x, t.y, t.x + t.width - 1, t.y);
            g2d.drawLine(t.x, t.y, t.x + tw2, t.y + t.height);
            g2d.drawLine(t.x + t.width - 1, t.y, t.x + tw2, t.y + t.height);
        }

    }
}
