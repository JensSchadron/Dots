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
    private JLabel lblInfo;

    public GUIAboutPane(Container contentPane) {
        super();
        this.contentPane = contentPane;
        setLayout(new BorderLayout());
        //setLayout(null);
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        lblInfo = new JLabel("<html>Hey daar.<p>" +
                "<p>" +
                "Welkom bij de info van ons spel.<p>" +
                "Moest je het nog niet door hebben wat precies de bedoeling is van het spel, dan kan je hier alvast een " +
                "deeltje van de uitleg vinden.<p>" +
                "Dots is een spel waarbij gekleurde bolletjes, de zogenaamde dots, een zo lang mogelijke ketting moeten " +
                "vormen. Er zijn wel twéé voorwaarden: De dots moeten aangrenzend zijn en ze moeten dezelfde kleur hebben.<p>" +
                "Als je nog steeds niet goed door hebt hoe je het spel precies moet spelen, dan kan je nog steeds terecht bij " +
                "de help die je bereikt door op de help-knop in het hoofdmenu te drukken.<p>" +
                "<p>" +
                "En dan nu een beetje info over onszelf.<p>" +
                "Had je nu echt gedacht dat je nu al info over ons hier zou vinden? Het kan best zijn dat deze er nog komt op een later tijdstip ;)" +
                "</html>");
        btnClose = new JButton("Close");
    }

    private void MakeLayout() {
        add(lblInfo, BorderLayout.CENTER);
        btnClose.setForeground(Color.BLUE);
        add(btnClose, BorderLayout.SOUTH);
        //btnClose.setBounds(40, getHeight() / 2, 350, 30);
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

        /*AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        g.drawString("Hier komt de about info (Info over onszelf en het spel)", 50, 100);
        g.drawString("test!", 50, 120);*/
    }

    private void MakeEventListener() {
        this.addMouseListener(new MouseAdapter() {
        });
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
            }
        });

    }
}
