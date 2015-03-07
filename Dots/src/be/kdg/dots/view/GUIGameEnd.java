package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * Created by alexander on 7/03/2015.
 */
public class GUIGameEnd extends JPanel {

    private Container contentPane;
    private ImageIcon imageWinner;
    private JLabel lblWinner, lblScore, lblLevel;
    private GUIHoofdMenu guiHoofdMenu;
    private JPanel panel, panel2,panel3, panelLabels, panelButton;
    private JButton btnClose;

    public GUIGameEnd(Container contentPane, GUIHoofdMenu guiHoofdMenu) {
        this.contentPane = contentPane;
        this.guiHoofdMenu = guiHoofdMenu;
        setLayout(new BorderLayout());
        makeComponents();
        makeLayout();
        MakeEventListener();
    }

    private void makeComponents() {
        imageWinner = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/Winner.png")), 300, 150));
        lblWinner = new JLabel("");
        lblWinner.setIcon(imageWinner);

        lblScore = new JLabel("Proficiat, u hebt een score van " + Integer.toString(guiHoofdMenu.getController().getSpeler().getScore().getScore()));
        lblLevel = new JLabel( "en level " + Integer.toString(guiHoofdMenu.getController().getSpeler().getLevel().getLevel()) + " behaald");
        lblScore.setFont(new Font("Serif", Font.PLAIN, 25));
        lblLevel.setFont(new Font("Serif", Font.PLAIN, 25));

        btnClose = new JButton("Close");
    }

    public static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return image;
    }


    public void makeLayout() {
        panel = new JPanel(new FlowLayout());
        panel2 = new JPanel(new FlowLayout());
        panel3 = new JPanel(new FlowLayout());
        panelLabels = new JPanel(new GridLayout(3,1));
        panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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

        AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        /*String txtWinner = "OVERWINNING";
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        Font font = new Font("TimesRoman", Font.PLAIN, 80);
        g.setFont(font);
        int textwidth = (int) (font.getStringBounds(txtWinner, frc).getWidth());
        g.drawString(txtWinner, getWidth() / 2 - textwidth / 2, getHeight() / 2);*/
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

    public void eindigSpel() {
        //guiHoofdMenu.getController().getHighscore().addHighScore(modus);
        //guiHoofdMenu.getController().getGuiFrame().updateFrame("hoofdMenu");
    }
}
