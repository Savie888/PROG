package Metthy.View;

import Metthy.Controller.MainController;
import Metthy.Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * DashboardPanel represents the main dashboard screen showing trucks and controls.
 * Currently displays a background image and a title label.
 * Future improvements can include real-time data on coffee trucks and actions.
 */

public class DashboardPanel extends BasePanel{

    private final MainController mainController;

    private JPanel formPanel, centerWrapper;
    private ArrayList<CoffeeTruck> trucks;

    public DashboardPanel(MainController mainController){

        this.mainController = mainController;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("regular.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Dashboard");
        backgroundPanel.add(title, BorderLayout.NORTH);

        //Form Background Panel
        JPanel formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 20, 200));

        //Form Panel
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));
        
        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton backButton = createButton("Exit to Previous Menu");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            mainController.mainMenuPanel();
        });

        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add bottom panel

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    public void displayDashboard(){

        this.trucks = mainController.getTrucks();

        formPanel.removeAll(); // Clear previous content

        centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setOpaque(false);

        //Header
        JLabel header = createInfoLabel("[Truck Deployment]");
        header.setAlignmentX(CENTER_ALIGNMENT);
        centerWrapper.add(header);
        centerWrapper.add(Box.createVerticalStrut(15));

        displayTruckDeployment();

        displayTotalInventory();

        displaySyrupInventory();

        displayTruckSalesInfo();

        formPanel.add(centerWrapper);
        formPanel.revalidate();
        formPanel.repaint();
    }

    private void displayTruckDeployment(){

        int regularCount = mainController.getRegularTruckCount();
        int specialCount = mainController.getSpecialTruckCount();
        int total = regularCount + specialCount;

        // Deployment details
        JLabel regularLabel = createInfoLabel("Regular Trucks   : " + regularCount);
        JLabel specialLabel = createInfoLabel("Special Trucks   : " + specialCount);
        JLabel dividerLabel = createInfoLabel("------------------------");
        JLabel totalLabel = createInfoLabel("Total Trucks     : " + total);

        regularLabel.setAlignmentX(CENTER_ALIGNMENT);
        specialLabel.setAlignmentX(CENTER_ALIGNMENT);
        dividerLabel.setAlignmentX(CENTER_ALIGNMENT);
        totalLabel.setAlignmentX(CENTER_ALIGNMENT);

        centerWrapper.add(regularLabel);
        centerWrapper.add(specialLabel);
        centerWrapper.add(dividerLabel);
        centerWrapper.add(totalLabel);
    }

    private void displayTotalInventory(){

        int totalSmallCups = 0, totalMediumCups = 0, totalLargeCups = 0;
        double totalCoffeeGrams = 0, totalMilkOz = 0, totalWaterOz = 0;
        ArrayList<CoffeeTruck> trucks = mainController.getTrucks();

        for (CoffeeTruck truck : trucks){

            for (StorageBin bin : truck.getBins()){

                if (bin == null || bin.getContent() == null)
                    continue;

                BinContent content = bin.getContent();
                double quantity = bin.getItemQuantity();

                switch (content.getName()) {
                    case "Coffee Bean" -> totalCoffeeGrams += quantity;
                    case "Milk"        -> totalMilkOz += quantity;
                    case "Water"       -> totalWaterOz += quantity;
                    case "Small Cup"   -> totalSmallCups += (int) quantity;
                    case "Medium Cup"  -> totalMediumCups += (int) quantity;
                    case "Large Cup"   -> totalLargeCups += (int) quantity;
                }
            }
        }

        // Header
        JLabel header = createInfoLabel("[Aggregate Inventory]");
        header.setAlignmentX(CENTER_ALIGNMENT);
        centerWrapper.add(header);
        centerWrapper.add(Box.createVerticalStrut(15));

        // Inventory details
        centerWrapper.add(centeredInfo("Small Cups      : " + totalSmallCups));
        centerWrapper.add(centeredInfo("Medium Cups     : " + totalMediumCups));
        centerWrapper.add(centeredInfo("Large Cups      : " + totalLargeCups));
        centerWrapper.add(centeredInfo("Coffee Beans    : " + String.format("%.2f g", totalCoffeeGrams)));
        centerWrapper.add(centeredInfo("Milk            : " + String.format("%.2f oz", totalMilkOz)));
        centerWrapper.add(centeredInfo("Water           : " + String.format("%.2f oz", totalWaterOz)));
    }

    private void displaySyrupInventory(){

        centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setOpaque(false);

        JLabel header = createInfoLabel("[Syrup Inventory by Special Truck]");
        header.setAlignmentX(CENTER_ALIGNMENT);
        centerWrapper.add(header);
        centerWrapper.add(Box.createVerticalStrut(15));

        int specialTruckCount = 0;

        for (CoffeeTruck truck : trucks) {
            if (truck instanceof SpecialCoffeeTruck) {
                specialTruckCount++;

                JLabel truckLabel = createInfoLabel("--- Special Truck #" + specialTruckCount + " Syrups ---");
                truckLabel.setAlignmentX(CENTER_ALIGNMENT);
                centerWrapper.add(truckLabel);

                boolean hasSyrups = false;

                for (StorageBin bin : truck.getBins()) {
                    if (bin != null && bin.getContent() instanceof Syrup) {
                        hasSyrups = true;
                        Syrup syrup = (Syrup) bin.getContent();
                        String info = String.format("%-16s : %.2f oz", syrup.getName() + " Syrup", syrup.getQuantity());
                        JLabel syrupLabel = createInfoLabel(info);
                        syrupLabel.setAlignmentX(CENTER_ALIGNMENT);
                        centerWrapper.add(syrupLabel);
                    }
                }

                if (!hasSyrups) {
                    JLabel emptyLabel = createInfoLabel("[No syrup inventory]");
                    emptyLabel.setAlignmentX(CENTER_ALIGNMENT);
                    centerWrapper.add(emptyLabel);
                }

                centerWrapper.add(Box.createVerticalStrut(10));
            }
        }

        if (specialTruckCount == 0) {
            JLabel noneLabel = createInfoLabel("No special trucks deployed.");
            noneLabel.setAlignmentX(CENTER_ALIGNMENT);
            centerWrapper.add(noneLabel);
        }
    }

    private void displayTruckSalesInfo() {

        centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setOpaque(false);

        JLabel header = createInfoLabel("[Sales Summary]");
        header.setAlignmentX(CENTER_ALIGNMENT);
        centerWrapper.add(header);
        centerWrapper.add(Box.createVerticalStrut(15));

        double combinedSales = 0;

        for (CoffeeTruck truck : trucks) {
            JLabel truckLabel = createInfoLabel("--- " + truck.getName() + " ---");
            truckLabel.setAlignmentX(CENTER_ALIGNMENT);
            centerWrapper.add(truckLabel);

            ArrayList<String> log = truck.getSalesLog();

            if (log.isEmpty()) {
                JLabel noSales = createInfoLabel("No sales recorded.");
                noSales.setAlignmentX(CENTER_ALIGNMENT);
                centerWrapper.add(noSales);
            } else {
                for (String entry : log) {
                    JLabel entryLabel = createInfoLabel("• " + entry);
                    entryLabel.setAlignmentX(CENTER_ALIGNMENT);
                    centerWrapper.add(entryLabel);
                }
            }

            JLabel totalLabel = createInfoLabel(
                    String.format("Total for [%s] : ₱%.2f", truck.getName(), truck.getTotalSales())
            );
            totalLabel.setAlignmentX(CENTER_ALIGNMENT);
            centerWrapper.add(totalLabel);
            centerWrapper.add(Box.createVerticalStrut(15));

            combinedSales += truck.getTotalSales();
        }

        JLabel grandTotal = createInfoLabel(String.format("Combined Sales : ₱%.2f", combinedSales));
        grandTotal.setAlignmentX(CENTER_ALIGNMENT);
        centerWrapper.add(grandTotal);
    }


    private JLabel createInfoLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    // Utility for aligned info labels
    private JLabel centeredInfo(String text){

        JLabel label = createInfoLabel(text);
        label.setAlignmentX(CENTER_ALIGNMENT);

        return label;
    }
}
