package Metthy.View;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel with a semi-transparent, rounded rectangle background.
 * Used for overlay panels that blend with background images.
 */

public class TranslucentPanel extends JPanel {

    public TranslucentPanel(){

        setOpaque(false); //Set opaque false to blend with background
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        // Enable anti-aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Set the composite for 90% opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        // Set a custom color for the background (light chocolate brown)
        g2.setColor(new Color(246, 226, 206)); 
        // Fill a rounded rectangle over the panel area
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        // Clean up graphics context
        g2.dispose();

        super.paintComponent(g);
    }
}
