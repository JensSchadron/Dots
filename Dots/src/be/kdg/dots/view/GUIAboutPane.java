package be.kdg.dots.view;

import be.kdg.dots.model.exception.DotsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        ArrayList<String> infoArray;
        try {
            infoArray = new ArrayList<>(Files.readAllLines(Paths.get(getClass().getResource("/text/about.txt").toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new DotsException("Er is een fout opgetreden bij het lezen van het about bestand.");
        }
        for (String anInfoArray : infoArray) {
            info += anInfoArray;
        }
        info = info.replaceAll("\\\\n", "\n");
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
