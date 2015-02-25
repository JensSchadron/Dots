package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * Created by alexander on 24/02/2015.
 */
public class GUILogin extends JPanel {
    private Container contentPane;
    private SpelController controller;
    private JLabel lbllabel;
    private JTextField txtUsername;
    private JButton btnCancel, btnOK;
    private JPanel panel;

    public GUILogin(Container contentPane, SpelController controller) {
        super();
        this.contentPane = contentPane;
        this.controller = controller;
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnCancel = new JButton("Cancel");
        btnOK = new JButton("OK");
        lbllabel = new JLabel("Give a username:");
        txtUsername = new JTextField(controller.getSpeler().getUsername());

    }

    private void MakeLayout() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        btnCancel.setAlignmentX(CENTER_ALIGNMENT);
        btnOK.setAlignmentX(CENTER_ALIGNMENT);
        lbllabel.setAlignmentX(CENTER_ALIGNMENT);
        txtUsername.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(lbllabel);
        panel.add(txtUsername);
        panel.add(btnOK);
        this.add(panel);
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
        g.fillRect(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);

        /*AlphaComposite solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f);
        g.setComposite(solid);
        g.setColor(Color.black);
        g.drawString("Hier komt de about info (Info over onszelf en het spel)", 50, 100);
        g.drawString("test!", 50, 120);*/
    }

    private void MakeEventListener() {
        this.addMouseListener(new MouseAdapter() {
        });
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtUsername.getText().length()>2){
                    controller.setSpeler(txtUsername.getText());
                    setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Gelieve een username op te geven langer dan 2 karakters", "InfoBox: " + "Username", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
