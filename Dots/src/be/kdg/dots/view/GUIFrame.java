package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by jens & alexander on 16/02/2015.
 */
public class GUIFrame extends JFrame{
    private CardLayout cl;
    private SpelController controller;


    public GUIFrame(SpelController controller) throws HeadlessException {
        super("Dots");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cl = new CardLayout();
        setLayout(cl);
        this.controller = controller;
        //MaakLayout();
        super.setSize(500, 500);
        super.setVisible(true);

    }

    /*public void updateFrame(JPanel panel){
        add(panel);
    }*/

    public void updateFrame(String optie) {
        switch (optie) {
            case "hoofdMenu":
                cl.show(this.getContentPane(), "hoofdMenu");
                break;
            case "startSpel":
                cl.show(this.getContentPane(), "startSpel");
                break;
            case "glassPane":
                setGlassPane(new GUIGlassPane(getContentPane()));
                getGlassPane().setVisible(true);
        }
    }

}
