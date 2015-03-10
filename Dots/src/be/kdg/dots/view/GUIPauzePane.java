package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class GUIPauzePane extends GUIGlassPane {
    private ImageIcon iconPauze, iconHome;
    private JLabel lblPauze, lblHome;
    private String modus;
    private GUIHoofdMenu guiHoofdMenu;

    public GUIPauzePane(Container contentPane, GUIHoofdMenu guiHoofdMenu) {
        super(contentPane);
        this.guiHoofdMenu = guiHoofdMenu;
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        this.modus = guiHoofdMenu.getGuiSpel().getModus();
        makeComponents();
        makeLayout();
        MakeEventListener();
    }

    private void makeComponents() {
        iconPauze = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelMenu/btnPlay.png")), 50, 50));
        lblPauze = new JLabel("");
        lblPauze.setName("pauze");
        lblPauze.setIcon(iconPauze);

        iconHome = new ImageIcon(resize(new ImageIcon(getClass().getResource("/images/spelMenu/btnHome.png")), 250, 50));
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

        super.getContentPane().paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        String txtPauze = "PAUZE";
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        Font font = new Font("TimesRoman", Font.PLAIN, 80);
        g.setFont(font);
        int textwidth = (int) (font.getStringBounds(txtPauze, frc).getWidth());
        g.drawString(txtPauze, getWidth() / 2 - textwidth / 2, getHeight() / 2);
    }

    private void MakeEventListener() {
        addMouseListener(new MouseAdapter() {
        });
        lblPauze.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
                if (!modus.equals("Move")){
                    guiHoofdMenu.getController().getSpel().startTimer();
                }
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
        guiHoofdMenu.getController().getHighscore().addHighScore(modus);
        guiHoofdMenu.getController().getGuiHoofdMenu().getGuiFrame().updateFrame("hoofdMenu");
    }
}
