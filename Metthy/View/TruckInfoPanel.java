package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.BinContent;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.SpecialCoffeeTruck;
import Metthy.Model.StorageBin;

import javax.swing.*;
import java.awt.*;

public class TruckInfoPanel extends BasePanel{

    private TruckController truckController;
    private MenuView menuView;
    private BackgroundPanel backgroundPanel;
    private JPanel titleWrapper, infoDisplayPanel, formBackgroundPanel;
    private JLabel titleLabel, errorLabel, nameLabel, locationLabel, truckTypeLabel;

    public TruckInfoPanel(TruckController truckController, MenuView menuView){

        this.truckController = truckController;
        this.menuView = menuView;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("regular.png"));
        Image image = backgroundImage.getImage();
        backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.X_AXIS));
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0)); // Adds spacing from top

        //Stylized title
        titleLabel = new JLabel("Truck Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(false);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setMaximumSize(titleLabel.getPreferredSize());

        //Add label centered in wrapper
        titleWrapper.add(Box.createHorizontalGlue());
        titleWrapper.add(titleLabel);
        titleWrapper.add(Box.createHorizontalGlue());

        backgroundPanel.add(titleWrapper, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);

        //Form Background Panel
        formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 20, 200));

        //Main content panel where truck info will be dynamically inserted
        infoDisplayPanel = new JPanel();
        infoDisplayPanel.setLayout(new BoxLayout(infoDisplayPanel, BoxLayout.Y_AXIS));
        infoDisplayPanel.setOpaque(false);

        formBackgroundPanel.add(infoDisplayPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
    }

    public void displayTruckInfo(CoffeeTruck truck){

        infoDisplayPanel.removeAll(); //Reset Panel

        //Truck header
        String truckType = (truck instanceof SpecialCoffeeTruck) ? "Special Truck" : "Regular Truck";
        JLabel header = createInfoLabel("[" + truckType + "]");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel nameLoc = createInfoLabel("Name - " + truck.getName() + " | Location: " + truck.getLocation());
        nameLoc.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoDisplayPanel.add(header);
        infoDisplayPanel.add(Box.createVerticalStrut(10));
        infoDisplayPanel.add(nameLoc);
        infoDisplayPanel.add(Box.createVerticalStrut(20));

        // Storage Bins
        infoDisplayPanel.add(createInfoLabel("--- Storage Bins ---"));
        for (StorageBin bin : truck.getBins()) {
            BinContent content = bin.getContent();
            String binInfo = "Bin #" + bin.getBinNumber() + ": ";
            if (content == null) {
                binInfo += "[Empty]";
            } else {
                binInfo += String.format("%s - %.2f / %d", content.getName(), content.getQuantity(), content.getCapacity());
            }
            infoDisplayPanel.add(createInfoLabel(binInfo));
        }

        infoDisplayPanel.add(Box.createVerticalStrut(20));

        // Transactions
        infoDisplayPanel.add(createInfoLabel("--- Transactions ---"));
        if (truck.getSalesLog().isEmpty()) {
            infoDisplayPanel.add(createInfoLabel("No transactions yet."));
        }

        else {
            for (String transaction : truck.getSalesLog()) {
                infoDisplayPanel.add(createInfoLabel(transaction)); // Customize if needed
            }
        }
        infoDisplayPanel.add(Box.createVerticalStrut(20));

        // Total Sales
        infoDisplayPanel.add(createInfoLabel("Total Sales: $" + String.format("%.2f", truck.getTotalSales())));

        // Refresh UI
        infoDisplayPanel.revalidate();
        infoDisplayPanel.repaint();
    }

    private JLabel createInfoLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
}
