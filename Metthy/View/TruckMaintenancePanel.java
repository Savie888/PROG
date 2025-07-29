package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.BinContent;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.SpecialCoffeeTruck;
import Metthy.Model.StorageBin;

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

            else if(option == 1)
                restock();
        });

        modifyButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            int option = selectBinOption();

            if(option == 0)
                truckController.truckLoadoutPanel(selectedTruck);

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
            //menuView.showPanel("DASHBOARD");
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

    public int getRestockQuantity(){

        SpinnerNumberModel model = new SpinnerNumberModel(0, 1, 1000, 1);
        JSpinner spinner = new JSpinner(model);

        int result = JOptionPane.showConfirmDialog(
                this,
                spinner,
                "Enter quantity to add (0 for full restock)",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION)
            return (int) spinner.getValue();

        else
            return -1; //User cancelled
    }

    public void restock(){

        double quantity;

        int binNumber = selectBin(selectedTruck); //Get a bin number

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
            quantity = getRestockQuantity();
            truckController.restockBin(selectedTruck, bin, quantity);
        }
    }

    public void restockAll(){

        int binNumber, mode;
        double quantity;

        mode = selectRestockMode();

        if(mode == -1)
            return;

        if(mode == 0)
            truckController.fullRestockAllBins(selectedTruck);

        else{
            for(int i = 0; i < selectedTruck.getBins().size(); i++){
                binNumber = i + 1;
                StorageBin bin = truckController.getBinByNumber(selectedTruck, binNumber);
                quantity = getRestockQuantity();

                if(quantity == 0) //Full Restock
                    truckController.fullRestockBin(selectedTruck, bin);

                else
                    truckController.restockBin(selectedTruck, bin, quantity);
            }
        }
    }

    private int getIngredientQuantity(int capacity){

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, capacity, 1);
        JSpinner spinner = new JSpinner(model);

        int result = JOptionPane.showConfirmDialog(
                this,
                spinner,
                "Enter quantity to add",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION)
            return (int) spinner.getValue();

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

    public void modify(){

        double quantity;

        int binNumber = selectBin(selectedTruck); //Get a bin number;

        if(binNumber == -1) //User cancelled
            return;

        if(!truckController.validBinNumber(binNumber)){
            JOptionPane.showMessageDialog(null, "Invalid bin number");
            return;
        }

        StorageBin bin = truckController.getBinByNumber(selectedTruck, binNumber);

        BinContent ingredient = selectIngredient();

        if(ingredient == null)
            return;

        quantity = getIngredientQuantity(ingredient.getCapacity());

        truckController.modifyBin(selectedTruck, bin, ingredient, quantity);
    }

    public void empty(){

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
}


