package Metthy.View;


import javax.swing.*;
import java.awt.*;


/**
 * BackgroundPanel is a custom JPanel that displays a background image scaled to fit the panel.
 * This is useful for setting themed GUI backgrounds in the coffee truck application.
 */
public class BackgroundPanel extends JPanel {


    /** The image to be drawn as the panel's background. */
    private final Image backgroundImage;


    /**
     * Constructs a BackgroundPanel with the specified image as the background.
     *
     * @param backgroundImage the Image to be used as the background
     */
    public BackgroundPanel(Image backgroundImage) {
     
        this.backgroundImage = backgroundImage;
        setLayout(new BorderLayout());
    }


    /**
     * Overrides the paintComponent method to draw the background image scaled to panel dimensions.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }}
