package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class GUIHelpPane extends GUIGlassPane {
    private JPanel centerPanel;
    private JButton btnClose;
    private GUIHoofdMenu guiHoofdMenu;

    public GUIHelpPane(Container contentPane, GUIHoofdMenu guiHoofdMenu) {
        super(contentPane);
        this.guiHoofdMenu = guiHoofdMenu;
        setLayout(new BorderLayout());
        makeComponents();
        makeLayout();
        addEventHandlers();
    }

    void makeComponents() {
        String help = "";
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/text/help.txt")));
            String tmp;
            while ((tmp = input.readLine()) != null) {
                help += tmp + "\n";
            }
        } catch (IOException e) {
            guiHoofdMenu.getGuiFrame().toonFoutBoodschap("Er is een fout opgetreden bij het inlezen van het help bestand.", false);
            help = "Er is een fout opgetreden bij het inlezen van het help bestand.\n" +
                    "Gelieve ons te contacteren als deze fout zich blijft voordoen.";
        }

        JTextArea helpInfo = new JTextArea(help);
        helpInfo.setLineWrap(true);
        helpInfo.setWrapStyleWord(true);
        helpInfo.setOpaque(false);
        helpInfo.setEditable(false);
        helpInfo.setHighlighter(null);
        helpInfo.setMargin(new Insets(10, 10, 10, 10));

        JLabel helpAnimation = new JLabel(new ImageIcon(getClass().getResource("/images/spelHelp/spelHelp.gif")));
        helpAnimation.setHorizontalAlignment(SwingConstants.CENTER);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(helpInfo, BorderLayout.NORTH);
        centerPanel.add(helpAnimation, BorderLayout.SOUTH);

        btnClose = new JButton("Sluiten");
    }

    void makeLayout() {
        add(centerPanel, BorderLayout.CENTER);
        add(btnClose, BorderLayout.SOUTH);
    }

    void addEventHandlers() {
        this.addMouseListener(new MouseAdapter() {
        });
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
