package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TruckMaintenancePanel extends BasePanel {

    private final TruckController truckController;
    private CoffeeTruck selectedTruck;

    public TruckMaintenancePanel(TruckController truckController){

        this.truckController = truckController;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("special.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Truck Maintenance");
        backgroundPanel.add(title, BorderLayout.NORTH);

        //Form Background Panel
        JPanel formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 20, 200));

        //Error label
        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setText(" ");

        //Center wrapper for the button panel
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton restockButton = createButton("Restock Bins");
        JButton modifyButton = createButton("Modify Bins");
        JButton emptyButton = createButton("Empty Bins");
        JButton editNameButton = createButton("Edit Truck Name");
        JButton editLocationButton = createButton("Edit Truck Location");
        JButton editPricesButton = createButton("Edit Prices");
        JButton exitButton = createButton("Exit to Simulation Menu");

        buttonPanel.add(restockButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(modifyButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(emptyButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(editNameButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(editLocationButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(editPricesButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(errorLabel);

        restockButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            int option = selectBinOption();

            if(option == 0) //Restock all bins
                restockAll();

            else if(option == 1){
                int binNumber = selectBin(selectedTruck); //Get a bin number
                restock(binNumber);
            }
        });

        modifyButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            int option = selectBinOption();

            if(option == 0)
                truckController.truckLoadoutPanel(selectedTruck, () -> truckController.truckMaintenancePanel(selectedTruck), false);

            else if(option == 1)
                modify();
        });

        emptyButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            int option = selectBinOption();

            if(option == 0) //Empty all bins
                truckController.emptyAllBins(selectedTruck);

            else if(option == 1)
                empty(); //Call empty method
        });

        editNameButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            String currentName = selectedTruck.getName();
            String newName = JOptionPane.showInputDialog(null,
                    "Current Name: " + currentName + "\nEnter new name:",
                    "Edit Truck Name",
                    JOptionPane.PLAIN_MESSAGE);

            if(newName.isEmpty())
                errorLabel.setText("Name can't be empty.");

            else if(!truckController.isTruckNameUnique(newName))
                errorLabel.setText("Name already used.");

            else{
                errorLabel.setText("");
                selectedTruck.setName(newName);
                JOptionPane.showMessageDialog(null, "Truck name updated to: " + newName);
            }
        });

        editLocationButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            String currentLocation = selectedTruck.getLocation(); // Adjust as needed
            String newLocation = JOptionPane.showInputDialog(null,
                    "Current Location: " + currentLocation + "\nEnter new location:",
                    "Edit Truck Location",
                    JOptionPane.PLAIN_MESSAGE);

            if(newLocation.isEmpty())
                errorLabel.setText("Location can't be empty.");

            else if(!truckController.isTruckLocationUnique(newLocation))
                errorLabel.setText("Location already used.");

            else{
                errorLabel.setText(" ");
                selectedTruck.setLocation(newLocation);
                JOptionPane.showMessageDialog(null, "Truck location updated to: " + newLocation);
            }
        });

        editPricesButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            setIngredientPrices();
        });

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            truckController.simulateTruckPanel(); //Return to simulate truck panel
        });

        //Panel for error display
        JPanel errorWrapper = new JPanel();
        errorWrapper.setOpaque(false);
        errorWrapper.setPreferredSize(new Dimension(300, 20)); // Fixed height
        errorWrapper.add(errorLabel);

        buttonPanel.add(errorWrapper);
        centerWrapper.add(buttonPanel);
        formBackgroundPanel.add(centerWrapper, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    public void startMaintenance(CoffeeTruck truck){

        this.selectedTruck = truck;
    }

    private int selectBinOption(){

        String[] options = {"Select all bins", "Select one bin"};

        int result = JOptionPane.showOptionDialog(
                this,
                "Select Action",
                "Bin Maintenance",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        return result;
    }

    private int selectBin(CoffeeTruck truck){

        int max = 8;

        if(truck instanceof SpecialCoffeeTruck)
            max = 10;

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, max, 1);
        JSpinner spinner = new JSpinner(spinnerModel);

        int result = JOptionPane.showOptionDialog(
                this,
                spinner,
                "Select a Bin",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        if(result == JOptionPane.OK_OPTION)
            return (int) spinner.getValue();

        else
            return -1; //Cancelled
    }

    private int selectRestockMode(){

        String[] options = {"Fully Restock", "Add Quantities"};

        int option = JOptionPane.showOptionDialog(
                this,
                "Choose restock mode:",
                "Restock Bins",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        return option;
    }

    private double getRestockQuantity(int capacity, double currentQuantity){

        double max = capacity - currentQuantity;

        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, max, 1);
        JSpinner spinner = new JSpinner(model);

        JLabel infoLabel = new JLabel(
                String.format("Current: %.0f / %d  |  Enter quantity to add (0 = full restock)", currentQuantity, capacity)
        );

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(10)); // spacing
        panel.add(spinner);

        double result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Restock Bin",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION)
            return (double) spinner.getValue();
        else
            return -1; //User cancelled
    }

    private void restock(int binNumber){

        double quantity;

        if(binNumber == -1) //User cancelled
            return;

        if(!truckController.validBinNumber(binNumber)){
            JOptionPane.showMessageDialog(null, "Invalid bin number");
            return;
        }

        StorageBin bin = truckController.getBinByNumber(selectedTruck, binNumber);

        int mode = selectRestockMode();

        if(mode == 0) //Full Restock
            truckController.fullRestockBin(selectedTruck, bin);

        else{
            int capacity = bin.getContent().getCapacity();
            double currentQuantity = bin.getContent().getQuantity();

            quantity = getRestockQuantity(capacity, currentQuantity);
            truckController.restockBin(selectedTruck, bin, quantity);
        }
    }

    private void restockAll(){

        int mode = selectRestockMode();

        if(mode == -1)
            return;

        if(mode == 0)
            truckController.fullRestockAllBins(selectedTruck);

        else{
            for(int i = 0; i < selectedTruck.getBins().size(); i++){

                restock(i + 1);
            }
        }
    }

    private double getIngredientQuantity(int capacity){

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, capacity, 1);
        JSpinner spinner = new JSpinner(model);

        JLabel infoLabel = new JLabel(
                String.format("Bin capacity: %d units", capacity)
        );

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(10)); // spacing
        panel.add(spinner);

        double result = JOptionPane.showConfirmDialog(
                this,
                spinner,
                "Enter quantity",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION)
            return (double) spinner.getValue();

        else
            return -1; //User cancelled
    }

    private BinContent selectIngredient(){

        JComboBox<BinContent> ingredientBox = new JComboBox<>();
        ingredientBox.setMaximumSize(new Dimension(200, 30));

        ArrayList<BinContent> ingredients = truckController.getIngredients();

        for(int i = 0; i < ingredients.size(); i++){

            BinContent ingredient = ingredients.get(i);
            ingredientBox.addItem(ingredient);
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                ingredientBox,
                "Select Ingredient",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION)
            return (BinContent) ingredientBox.getSelectedItem();

        else
            return null; //User cancelled
    }

    private void modify(){

        double quantity;

        int binNumber = selectBin(selectedTruck); //Get a bin number;

        if(binNumber == -1) //User cancelled
            return;

        if(!truckController.validBinNumber(binNumber)){
            JOptionPane.showMessageDialog(null, "Invalid bin number");
            return;
        }

        if(binNumber == 9 || binNumber == 10)
            modifySyrupBin(binNumber);

        else{
            StorageBin bin = truckController.getBinByNumber(selectedTruck, binNumber);

            BinContent ingredient = selectIngredient();

            if(ingredient == null)
                return;

            quantity = getIngredientQuantity(ingredient.getCapacity());

            truckController.modifyBin(selectedTruck, bin, ingredient, quantity);
        }

    }

    private BinContent createSyrup() {

        String name = JOptionPane.showInputDialog(
                null,
                "Enter syrup name (e.g., Vanilla, Grape):",
                "Create Syrup",
                JOptionPane.PLAIN_MESSAGE
        );

        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No name entered. Syrup creation cancelled.", "Cancelled", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new Syrup(name.trim());
    }

    private void modifySyrupBin(int binNumber) {

        StorageBin bin = truckController.getBinByNumber(selectedTruck, binNumber);

        JOptionPane.showMessageDialog(null, "Setup for Syrup Bin #" + binNumber, "Bin Setup", JOptionPane.INFORMATION_MESSAGE);

        //Let user create a syrup
        BinContent content = createSyrup();

        if (content == null)
            return; // User cancelled

        int max = content.getCapacity();
        double quantity = -1;

        //Ask user to enter a quantity
        boolean valid = false;

        while (!valid) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter quantity to store (Max: " + max + ")",
                    "Quantity Entry",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (input == null) return; // cancelled

            try {
                quantity = Double.parseDouble(input);
                if (quantity < 0 || quantity > max) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity. Try again.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }

        // Step 3: Assign to bin
        selectedTruck.assignItemToBin(bin, content, quantity);
        JOptionPane.showMessageDialog(null,
                String.format("Bin #%d loaded with %.2f oz of %s syrup.", binNumber, quantity, content.getName()),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void empty(){

        int binNumber = selectBin(selectedTruck); //Get a bin number;

        if(binNumber == -1) //User cancelled
            return;

        if(!truckController.validBinNumber(binNumber)){
            JOptionPane.showMessageDialog(null, "Invalid bin number");
            return;
        }

        StorageBin bin = truckController.getBinByNumber(selectedTruck, binNumber);
        truckController.emptyBin(selectedTruck, bin); //Empty one bin
    }

    /**
     * Prompts the user to input the prices for coffee ingredients (cups, coffee beans, milk, and water).
     */
    private void setIngredientPrices(){

        double cupPrice = enterPrice("Enter price of a cup");
        truckController.setCupPrice(cupPrice);

        double coffeeGramPrice = enterPrice("Enter price of coffee per gram:");
        truckController.setCoffeeGramPrice(coffeeGramPrice);

        double milkOzPrice = enterPrice("Enter price of milk per oz:");
        truckController.setMilkOzPrice(milkOzPrice);

        double waterOzPrice = enterPrice("Enter price of water per oz:");
        truckController.setWaterOzPrice(waterOzPrice);

        double syrupOzPrice = enterPrice("Enter price of syrup per oz:");
        truckController.setSyrupOzPrice(syrupOzPrice);

        double extraShotPrice = enterPrice("Enter price for extra espresso shot:");
        truckController.setExtraShotPrice(extraShotPrice);
    }

    private double enterPrice(String message){

        double value = 0;

        while (value <= 0) {
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 100.00, 0.5));
            spinner.setPreferredSize(new Dimension(100, 25));

            int option = JOptionPane.showOptionDialog(
                    this,
                    spinner,
                    message,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null
            );

            value = (double) spinner.getValue();
        }

        return value;
    }
}


