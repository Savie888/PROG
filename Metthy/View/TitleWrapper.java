package Metthy.View;

import javax.swing.*;
import java.awt.*;

//JPanel for title
public class TitleWrapper extends JPanel {

    public TitleWrapper(String title){

        super();

        //Setup Title
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0)); // Adds spacing from top

        //Stylized title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(false);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setMaximumSize(titleLabel.getPreferredSize());

        //Center Title Label
        add(Box.createHorizontalGlue());
        add(titleLabel);
        add(Box.createHorizontalGlue());
    }
}
