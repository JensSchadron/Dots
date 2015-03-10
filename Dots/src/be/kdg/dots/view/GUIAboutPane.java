package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by jens & alexander on 17/02/2015.
 */
public class GUIAboutPane extends GUIGlassPane {
    private JButton btnClose;
    private JTextArea txtAreaInfo;

    public GUIAboutPane(Container contentPane) {
        super(contentPane);
        setLayout(new BorderLayout());
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        String info = "";
        ArrayList<String> infoArray = new ArrayList<>();
        try {
            infoArray = new ArrayList<>(Files.readAllLines(Paths.get(getClass().getResource("/text/about.txt").toURI())));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        for (String anInfoArray : infoArray) {
            info += anInfoArray;
        }
        info = info.replaceAll("\\\\n","\n");
        txtAreaInfo = new JTextArea(info);
        txtAreaInfo.setLineWrap(true);
        txtAreaInfo.setWrapStyleWord(true);
        txtAreaInfo.setOpaque(false);
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setHighlighter(null);
        txtAreaInfo.setMargin(new Insets(10, 10, 10, 10));
        btnClose = new JButton("Sluiten");
    }

    private void MakeLayout() {
        add(txtAreaInfo, BorderLayout.CENTER);
        btnClose.setForeground(Color.BLUE);
        add(btnClose, BorderLayout.SOUTH);
    }

    /*@Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        super.getContentPane().paint(gr);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }*/

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
