package Metthy.View;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private final Image backgroundImage;

    public BackgroundPanel(Image backgroundImage){

        this.backgroundImage = backgroundImage;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
