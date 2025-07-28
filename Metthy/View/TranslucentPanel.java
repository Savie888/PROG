package Metthy.View;

import javax.swing.*;
import java.awt.*;

public class TranslucentPanel extends JPanel {

    public TranslucentPanel(){

        setOpaque(false); //Set opaque false to blend with background
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2.setColor(new Color(246, 226, 206)); // chocolate brown-ish
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();

        super.paintComponent(g);
    }
}
