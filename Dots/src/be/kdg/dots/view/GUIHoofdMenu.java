package be.kdg.dots.view;

import be.kdg.dots.controller.SpelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jens & alexander on 4/02/2015.
 */
public class GUIHoofdMenu extends JPanel {
    private SpelController controller;
    private GUIFrame guiFrame;
    private GUISpel guiSpel;
    private SplashScreen splashScreen;

    private JPanel main, gameMode, southPanel, loginPanel;
    private JLabel lblTimeMode, lblEndlessMode, lblMoveMode, lblBanner, lblHighscore;
    private ImageIcon iconTimed, iconEndless, iconMove, iconHighscore, iconBanner;
    private JButton btnSettings, btnAbout, btnHelp;

    public GUIFrame getGuiFrame() {
        return guiFrame;
    }

    public GUISpel getGuiSpel() {
        return guiSpel;
    }

    public SpelController getController() {
        return controller;
    }

    public GUIHoofdMenu(SpelController controller) throws HeadlessException {
        setOpaque(true);
        setBackground(Color.white);
        this.controller = controller;
        //guiFrame = new GUIFrame(this);
        //guiFrame.getContentPane().add("hoofdMenu", this);

        splashScreen = new SplashScreen();
        guiFrame = new GUIFrame(this);
        guiFrame.getContentPane().add("hoofdMenu", this);

        MakeComponents();
        MakeLayout();
        MakeEventListener();
    }

    private void MakeComponents() {
        btnSettings = new JButton("Settings");
        btnAbout = new JButton("About");
        btnHelp = new JButton("Help");

        //iconTimed = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnTimed.png")), 120, 120));
        iconTimed = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/hoofdmenu/knop-groen.png")).getImage(), 120, 120));
        lblTimeMode = new JLabel("", JLabel.RIGHT);
        lblTimeMode.setIcon(iconTimed);

        //iconEndless = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnInfinity.png")), 120, 120));
        iconEndless = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/hoofdmenu/knop-paars.png")).getImage(), 120, 120));
        lblEndlessMode = new JLabel("", JLabel.RIGHT);
        lblEndlessMode.setIcon(iconEndless);

        //iconMove = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnMove.png")), 120, 120));
        iconMove = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/hoofdmenu/knop-roos.png")).getImage(), 120, 120));
        lblMoveMode = new JLabel("", JLabel.LEFT);
        lblMoveMode.setIcon(iconMove);

        //iconHighscore = new ImageIcon(resize(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/btnHighscore.png")), 120, 120));
        iconHighscore = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/hoofdmenu/knop-blauw.png")).getImage(), 120, 120));
        lblHighscore = new JLabel("", JLabel.LEFT);
        lblHighscore.setIcon(iconHighscore);

        iconBanner = new ImageIcon(getScaledImage(new ImageIcon(getClass().getResource("/be/kdg/dots/resources/images/hoofdmenu/logo-dots.png")).getImage(), 400, 150));
        lblBanner = new JLabel("", JLabel.CENTER);
        lblBanner.setIcon(iconBanner);
        //lblBanner = new JLabel("Dots");
        //lblBanner.setForeground(new Color(83, 93, 245));
        //lblBanner.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        lblTimeMode.setOpaque(false);
        lblEndlessMode.setOpaque(false);
        lblMoveMode.setOpaque(false);

        try {
            //fonts
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/be/kdg/dots/resources/fonts/dotness.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(120f);
            lblBanner.setFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public static Image resize(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void MakeLayout() {
        main = new JPanel(new BorderLayout());
        gameMode = new JPanel(new GridLayout(2, 2, 10, 10));
        southPanel = new JPanel(new GridLayout(1, 4));
        loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        gameMode.setSize(50, 50);
        main.setOpaque(false);
        gameMode.setOpaque(false);
        loginPanel.setOpaque(false);
        gameMode.add(lblTimeMode);
        gameMode.add(lblMoveMode);
        gameMode.add(lblEndlessMode);
        gameMode.add(lblHighscore);
        southPanel.add(btnAbout);
        southPanel.add(btnSettings);
        southPanel.add(btnHelp);

        main.add(lblBanner, BorderLayout.NORTH);
        main.add(gameMode, BorderLayout.CENTER);
        main.add(southPanel, BorderLayout.SOUTH);
        super.add(main);
        super.revalidate();
    }

    public void setBackgroundColor(Color color){
        this.setBackground(color);
    }

    private void startSpel(String modus){
        guiSpel = new GUISpel(controller, guiFrame, modus);
        guiFrame.getContentPane().add("startSpel", guiSpel);
    }

    private void MakeEventListener() {
        lblTimeMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Time mode selected");
                setVisible(false);
                checkSpeler();
                startSpel("Time");
                controller.startSpel("Time");
            }
        });

        lblEndlessMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Infinity mode selected");
                setVisible(false);
                checkSpeler();
                startSpel("Infinity");
                controller.startSpel("Infinity");
            }
        });

        lblMoveMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Move mode selected");
                setVisible(false);
                checkSpeler();
                startSpel("Move");
                controller.startSpel("Move");
            }
        });

        btnSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - Settings selected");
                //controller.getGuiFrame().updateFrame("instellingenPanel");
                guiFrame.updateFrame("instellingenPanel");
            }
        });
        btnAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Debug info - About selected");
                guiFrame.updateFrame("aboutPanel");
                //controller.getGuiFrame().updateFrame("aboutPanel");
            }
        });
        btnHelp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("");
                System.out.println("Achievement get: Missing common sense...");
            }
        });
        lblHighscore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //controller.getGuiFrame().updateFrame("highScorePanel");
                guiFrame.updateFrame("highScorePanel");
            }
        });
    }

    public void checkSpeler(){
        if (controller.getSpeler().getUsername()==null){
            String userName;
            do{
                userName = JOptionPane.showInputDialog(this, "Gelieve een username op te geven met minimum 2 en maximum 20 karakters", "InfoBox: " + "Username", JOptionPane.INFORMATION_MESSAGE);
                if(userName == null){
                    return;
                }
            } while(userName.length()<2||userName.length()>20);
            controller.getSpeler().setUsername(userName);
        }
    }


}
