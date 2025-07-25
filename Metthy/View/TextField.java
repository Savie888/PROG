package Metthy.View;

import javax.swing.*;
import java.awt.*;

public class TextField extends JTextField {

    private final String text;

    public TextField(String hint) {
        this.text = hint;
        setForeground(Color.WHITE); // actual text color
        setBackground(new Color(255, 255, 255, 200)); // dark translucent background
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE); // hint color
            //g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets insets = getInsets();
            g2.drawString(text, insets.left + 5, getHeight() / 2 + getFont().getSize() / 2 - 2);
            g2.dispose();
        }
    }
}
