package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class GUIAboutPane extends GUIGlassPane {
    private JButton btnClose;
    private JTextArea txtAreaInfo;
    private GUIHoofdMenu guiHoofdMenu;

    public GUIAboutPane(Container contentPane, GUIHoofdMenu guiHoofdMenu) {
        super(contentPane);
        this.guiHoofdMenu = guiHoofdMenu;
        setLayout(new BorderLayout());
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        String info = "";
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/text/about.txt")));
            String tmp;
            while ((tmp = input.readLine()) != null) {
                info += tmp + "\n";
            }
        } catch (IOException e) {
            guiHoofdMenu.getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het lezen van het about bestand.", false);
            info = "Er is een fout opgetreden bij het inlezen van het about bestand.\n" +
                    "Gelieve ons te contacteren als deze fout zich blijft voordoen.";
        }
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
