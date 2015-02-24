package be.kdg.dots.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * Created by Jens on 24-2-2015.
 */
public class GUIPauzePane extends JPanel {
    private Container contentPane;
    private GUIFrame guiFrame;
    private JButton btnContinue, btnGoHome;

    public GUIPauzePane(Container contentPane, GUIFrame guiFrame){
        this.contentPane = contentPane;
        this.guiFrame = guiFrame;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        makeComponents();
        makeLayout();
        MakeEventListener();
    }

    private void makeComponents(){
        btnContinue = new JButton("Continue");
        btnGoHome = new JButton("Go home");
    }

    public void makeLayout(){

    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        //create transparency
        AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , .9f);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(transparent);

        contentPane.paint(gr);
        g.setColor(Color.white);
        g.fillRect(getWidth()/2-100, getHeight()/2-50, getWidth()/2+100, getHeight()/2+50);
    }

    private void MakeEventListener() {
        addMouseListener(new MouseAdapter() {
        });
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnGoHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
