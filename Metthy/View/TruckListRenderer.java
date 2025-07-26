package Metthy.View;

import Metthy.Model.CoffeeTruck;
import Metthy.Model.SpecialCoffeeTruck;

import javax.swing.*;
import java.awt.*;

public class TruckListRenderer extends JPanel implements ListCellRenderer<CoffeeTruck>{

    private JLabel typeLabel = new JLabel();
    private JLabel infoLabel = new JLabel();

    public TruckListRenderer(){

        setLayout(new BorderLayout(5, 5));
        setOpaque(true);

        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setForeground(Color.DARK_GRAY);

        add(typeLabel, BorderLayout.NORTH);
        add(infoLabel, BorderLayout.NORTH);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends CoffeeTruck> list, CoffeeTruck truck,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        if (truck == null)
            return this;

        typeLabel.setText(truck instanceof SpecialCoffeeTruck ? "Special Truck" : "Regular Truck");
        infoLabel.setText("Name: " + truck.getName() + "% | Location: " + truck.getLocation());

        if (isSelected) {
            setBackground(new Color(255, 255, 255, 80)); // translucent white
            setOpaque(true);
        } else {
            setOpaque(false);
        }

        return this;
    }
}
