package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.BinContent;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.StorageBin;
import Metthy.Model.Syrup;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TruckLoadoutPanel extends BasePanel {

    private final TruckController truckController;

    private JPanel formPanel;
    private JLabel binLabel, capacityLabel, errorLabel;
    private JComboBox<BinContent> ingredientBox;
    private JSpinner quantitySpinner;

    private CoffeeTruck truck;
    private ArrayList<StorageBin> bins;
    private int currentBinIndex = 0;
    private Runnable backAction;
    private boolean fromTruckCreation = false;

    public TruckLoadoutPanel(TruckController truckController){

        this.truckController = truckController;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("special.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Truck Loadout");
        backgroundPanel.add(title, BorderLayout.NORTH);

        // === Form Background Panel ===
        JPanel formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 700, 20, 700));

        // === Form Panel ===
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground((new Color(123, 79, 43)));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 50, 60)); // adjust padding as needed
        formPanel.setOpaque(false);

        //Set up layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        //Row 0: Bin Label
        gbc.gridy = 0;
        binLabel = new JLabel("Setting up Bin #1");
        binLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        binLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(binLabel);
        formPanel.add(Box.createVerticalStrut(20));

        //Row 1: Ingredient Label
        gbc.gridy++;
        JLabel ingredientLabel = new JLabel("Select Ingredient:");
        formPanel.add(ingredientLabel, gbc);
        formPanel.add(Box.createVerticalStrut(20));

        //Row 2: Ingredient Combo Box
        gbc.gridy++;
        ingredientBox = new JComboBox<>();
        ingredientBox.setMaximumSize(new Dimension(200, 30));
        formPanel.add(ingredientBox, gbc);
        formPanel.add(Box.createVerticalStrut(20));

        //Row 3: Quantity Label
        gbc.gridy++;
        JLabel quantityLabel = new JLabel("Enter Quantity:");
        formPanel.add(quantityLabel, gbc);
        formPanel.add(Box.createVerticalStrut(20));

        //Row 4: Quantity Spinner
        gbc.gridy++;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 100.0, 1.0));
        formPanel.add(quantitySpinner, gbc);
        formPanel.add(Box.createVerticalStrut(20));

        //Row 5: Capacity Label
        gbc.gridy++;
        capacityLabel = new JLabel(""); // Will update dynamically
        formPanel.add(capacityLabel, gbc);
        formPanel.add(Box.createVerticalStrut(20));

        //Row 6: Button Panel
        gbc.gridy++;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton confirmButton = new JButton("Confirm");
        JButton skipButton = new JButton("Skip Bin");

        buttonPanel.add(confirmButton);
        buttonPanel.add(skipButton);
        formPanel.add(buttonPanel, gbc);

        confirmButton.addActionListener(e -> handleConfirm());
        skipButton.addActionListener(e -> handleSkip());

        //Row 7: Error label
        gbc.gridy++;
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(errorLabel, gbc);

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton backButton = createButton("Exit to Previous Menu");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            backAction.run();
        });

        //Add formPanel into the background container
        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add south panel

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    public void startLoadout(CoffeeTruck truck, Runnable backAction, boolean fromTruckCreation){

        this.truck = truck;
        this.bins = truck.getBins();
        this.currentBinIndex = 0;
        this.backAction = backAction;
        this.fromTruckCreation = fromTruckCreation;

        updateUIForCurrentBin();
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

    private void modifySyrupBin(int binIndex) {

        int binNumber = binIndex + 1;

        StorageBin bin = truckController.getBinByNumber(truck, binNumber);

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
        truck.assignItemToBin(bin, content, quantity);
        JOptionPane.showMessageDialog(null,
                String.format("Bin #%d loaded with %.2f oz of %s syrup.", binNumber, quantity, content.getName()),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateUIForCurrentBin(){

        if(currentBinIndex >= bins.size()){

            if(fromTruckCreation){

                truckController.mainMenuPanel();

                //Delay repeat option until after returning to truck creation menu
                SwingUtilities.invokeLater(() -> {
                    int repeat = repeat();

                    if (repeat == JOptionPane.NO_OPTION)
                        truckController.mainMenuPanel();
                });
            }

            else
                backAction.run(); //Return to previous menu

            return;
        }

        StorageBin bin = bins.get(currentBinIndex);
        ArrayList<BinContent> ingredients = truckController.getIngredients();

        binLabel.setText("Setting up Bin #" + bin.getBinNumber());

        ingredientBox.removeAllItems();

        if(currentBinIndex == 8 || currentBinIndex == 9)
            modifySyrupBin(currentBinIndex);

        else{

            for(int i = 0; i < ingredients.size(); i++){

                BinContent ingredient = ingredients.get(i);
                ingredientBox.addItem(ingredient);
            }

            ingredientBox.addActionListener(e -> {

                BinContent selected = (BinContent) ingredientBox.getSelectedItem();

                if (selected != null) {
                    int capacity = selected.getCapacity();
                    double currentValue = (double) quantitySpinner.getValue();
                    double newValue = Math.min(currentValue, capacity);
                    quantitySpinner.setModel(new SpinnerNumberModel(newValue, 0.0, capacity, 1.0));
                    capacityLabel.setText("Max Capacity: " + capacity);
                }
            });
        }

        formPanel.revalidate();
        formPanel.repaint();
    }

    private void handleConfirm(){

        playSound("select_sound_effect.wav");

        BinContent selected = (BinContent) ingredientBox.getSelectedItem();
        double quantity = (double) quantitySpinner.getValue();

        if (quantity > selected.getCapacity() || quantity <= 0) {
            errorLabel.setText("Invalid quantity. Must be between 1 and " + selected.getCapacity());
            return;
        }

        StorageBin bin = bins.get(currentBinIndex);
        truckController.assignItemToBin(truck, bin, selected.clone(), quantity);

        JOptionPane.showMessageDialog(this, "Bin #" + bin.getBinNumber() + " loaded with " + quantity + " of " + selected.getName());

        currentBinIndex++;
        updateUIForCurrentBin();
    }

    private void handleSkip(){

        playSound("select_sound_effect.wav");

        StorageBin bin = bins.get(currentBinIndex);
        JOptionPane.showMessageDialog(this, "Skipped Bin #" + bin.getBinNumber());

        currentBinIndex++;
        updateUIForCurrentBin();
    }

    public int repeat(){

        int choice = JOptionPane.showOptionDialog(
                this,
                "Truck created successfully!\nCreate another one?",
                "Success",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] { "Yes, create another", "Back to Menu" },
                "Yes, create another"
        );

        return choice;
    }
}
