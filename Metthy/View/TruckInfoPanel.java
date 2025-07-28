package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.BinContent;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.SpecialCoffeeTruck;
import Metthy.Model.StorageBin;

import javax.swing.*;
import java.awt.*;

public class TruckInfoPanel extends BasePanel{


    private JPanel infoDisplayPanel;

    public TruckInfoPanel(TruckController truckController){

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("regular.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Truck Information");
        backgroundPanel.add(title, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);

        //Form Background Panel
        JPanel formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 20, 200));

        //Main content panel where truck info will be dynamically inserted
        infoDisplayPanel = new JPanel();
        infoDisplayPanel.setLayout(new BorderLayout());
        infoDisplayPanel.setOpaque(false);
        infoDisplayPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton backButton = createButton("Exit to Previous Menu");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            truckController.simulateTruckPanel();
        });

        formBackgroundPanel.add(infoDisplayPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add bottom panel
    }

    public void displayTruckInfo(CoffeeTruck truck){

        infoDisplayPanel.removeAll(); //Reset Panel

        //Set up a center wrapper
        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setOpaque(false);

        //Truck header
        String truckType = (truck instanceof SpecialCoffeeTruck) ? "Special Truck" : "Regular Truck";
        JLabel header = createInfoLabel("[" + truckType + "]");
        header.setAlignmentX(CENTER_ALIGNMENT);

        JLabel nameLoc = createInfoLabel("Name - " + truck.getName() + " | Location: " + truck.getLocation());
        nameLoc.setAlignmentX(CENTER_ALIGNMENT);

        centerWrapper.add(header);
        centerWrapper.add(Box.createVerticalStrut(10));
        centerWrapper.add(nameLoc);
        centerWrapper.add(Box.createVerticalStrut(20));

        // Storage Bins
        infoDisplayPanel.add(createInfoLabel("--- Storage Bins ---"));
        for (StorageBin bin : truck.getBins()) {
            BinContent content = bin.getContent();
            String binInfo = "Bin #" + bin.getBinNumber() + ": ";

            if (content == null)
                binInfo += "[Empty]";

            else
                binInfo += String.format("%s - %.2f / %d", content.getName(), content.getQuantity(), content.getCapacity());

            JLabel binLabel = createInfoLabel(binInfo);
            binLabel.setAlignmentX(CENTER_ALIGNMENT);
            centerWrapper.add(binLabel);
        }

        centerWrapper.add(Box.createVerticalStrut(20));

        // Transactions
        JLabel transactionHeader = createInfoLabel("--- Transactions ---");
        transactionHeader.setAlignmentX(CENTER_ALIGNMENT);
        centerWrapper.add(transactionHeader);

        if (truck.getSalesLog().isEmpty()) {
            JLabel transactionLabel = createInfoLabel("No transactions yet.");
            transactionLabel.setAlignmentX(CENTER_ALIGNMENT);
            centerWrapper.add(transactionLabel);
        }

        else {
            for (String transaction : truck.getSalesLog()) {
                JLabel transactionLabel = createInfoLabel(transaction);
                transactionLabel.setAlignmentX(CENTER_ALIGNMENT);
                centerWrapper.add(transactionLabel); // Customize if needed
            }
        }
        centerWrapper.add(Box.createVerticalStrut(20));

        // Total Sales
        JLabel totalSalesLabel = createInfoLabel("Total Sales: $" + String.format("%.2f", truck.getTotalSales()));
        totalSalesLabel.setAlignmentX(CENTER_ALIGNMENT);
        centerWrapper.add(totalSalesLabel);

        // Refresh UI
        infoDisplayPanel.add(centerWrapper, BorderLayout.CENTER);
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
