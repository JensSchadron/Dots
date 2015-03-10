package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GUIHelpPane extends GUIGlassPane {
    private JPanel centerPanel;
    private JTextArea helpInfo;
    private JLabel helpAnimation;
    private JButton btnClose;

    public GUIHelpPane(Container contentPane) {
        super(contentPane);
        setLayout(new BorderLayout());
        makeComponents();
        makeLayout();
        addEventHandlers();
    }

    public void makeComponents() {
        String help = "";
        ArrayList<String> helpArray = new ArrayList<>();
        try {
            helpArray = new ArrayList<>(Files.readAllLines(Paths.get(getClass().getResource("/text/help.txt").toURI())));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        for (String anInfoArray : helpArray) {
            help += anInfoArray;
        }
        help = help.replaceAll("\\\\n", "\n");
        helpInfo = new JTextArea(help);
        helpInfo.setLineWrap(true);
        helpInfo.setWrapStyleWord(true);
        helpInfo.setOpaque(false);
        helpInfo.setEditable(false);
        helpInfo.setHighlighter(null);
        helpInfo.setMargin(new Insets(10, 10, 10, 10));

        helpAnimation = new JLabel(new ImageIcon(getClass().getResource("/images/spelHelp/spelHelp.gif")));
        helpAnimation.setHorizontalAlignment(SwingConstants.CENTER);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(helpInfo, BorderLayout.NORTH);
        centerPanel.add(helpAnimation, BorderLayout.SOUTH);

        btnClose = new JButton("Sluiten");
    }

    public void makeLayout() {
        add(centerPanel, BorderLayout.CENTER);
        add(btnClose, BorderLayout.SOUTH);
    }

    public void addEventHandlers() {
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
