package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.BinContent;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.StorageBin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TruckLoadoutPanel extends BasePanel {

    private TruckController controller;
    private TruckView truckView;
    private MenuView menuView;

    private JComboBox<BinContent> ingredientBox;
    private JSpinner quantitySpinner;
    private JLabel binLabel, capacityLabel, quantityLabel, ingredientLabel;
    private JButton confirmButton, skipButton;

    private CoffeeTruck truck;
    private ArrayList<StorageBin> bins;
    private ArrayList<BinContent> ingredients;
    private int currentBinIndex = 0;

    private BackgroundPanel backgroundPanel;
    private JPanel titleWrapper, formPanel, formBackgroundPanel;
    private JLabel titleLabel, errorLabel;

    public TruckLoadoutPanel(TruckController controller, MenuView menuView){

        this.controller = controller;
        this.menuView = menuView;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("special.png"));
        Image image = backgroundImage.getImage();
        backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.X_AXIS));
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0)); // Adds spacing from top

        //Stylized title
        titleLabel = new JLabel("Truck Loadout", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(false);

        // Force label to hug its text
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setMaximumSize(titleLabel.getPreferredSize());

        // Add label centered in wrapper
        titleWrapper.add(Box.createHorizontalGlue());
        titleWrapper.add(titleLabel);
        titleWrapper.add(Box.createHorizontalGlue());

        backgroundPanel.add(titleWrapper, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);

        // === Form Background Panel ===
        formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setOpaque(false);
        formBackgroundPanel.setBackground(new Color(78, 53, 36, 200));
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(30, 110, 60, 90));

        // === Form Panel ===
        formPanel = new TranslucentPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 80)); // adjust padding as needed
        formPanel.setOpaque(false);

        binLabel = new JLabel("Setting up Bin #1");
        binLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        binLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(binLabel, CENTER_ALIGNMENT);
        formPanel.add(Box.createVerticalStrut(20));

        ingredientLabel = new JLabel("Select Ingredient:");
        ingredientBox = new JComboBox<>();
        ingredientBox.setMaximumSize(new Dimension(200, 30));
        formPanel.add(ingredientLabel);
        formPanel.add(ingredientBox);

        quantityLabel = new JLabel("Enter Quantity:");
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 100.0, 1.0));
        quantitySpinner.setMaximumSize(new Dimension(200, 30));
        formPanel.add(quantityLabel, CENTER_ALIGNMENT);
        formPanel.add(quantitySpinner);

        capacityLabel = new JLabel(""); // Will update dynamically
        formPanel.add(capacityLabel);

        confirmButton = new JButton("Confirm");
        skipButton = new JButton("Skip Bin");

        confirmButton.addActionListener(e -> handleConfirm());
        skipButton.addActionListener(e -> handleSkip());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(confirmButton);
        buttonPanel.add(skipButton);
        formPanel.add(buttonPanel);

        //Bottom Panel for back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton backButton = createButton("Exit to Previous Menu");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            menuView.showPanel("CREATE_TRUCK");
        });

        //Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(errorLabel);

        //Add formPanel into the background container
        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add south panel
    }

    public void startLoadout(CoffeeTruck truck) {
        this.truck = truck;
        this.bins = truck.getBins();
        this.currentBinIndex = 0;

        updateUIForCurrentBin();
    }

    private void updateUIForCurrentBin() {
        if (currentBinIndex >= bins.size()) {
            //truckView.showLoadoutComplete(truck);
            menuView.showPanel("CREATE_TRUCK");
            return;
        }

        StorageBin bin = bins.get(currentBinIndex);
        ingredients = controller.getIngredients();

        binLabel.setText("Setting up Bin #" + bin.getBinNumber());

        ingredientBox.removeAllItems();

        for (BinContent ingredient : ingredients) {
            ingredientBox.addItem(ingredient);  // directly store the object
        }
        /*
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientBox.addItem((i + 1) + ". " + ingredients.get(i).getName());
        }
*/

        quantitySpinner.setValue(1.0);
        capacityLabel.setText("");

        for (ActionListener al : ingredientBox.getActionListeners()) {
            ingredientBox.removeActionListener(al);
        }

        ingredientBox.addActionListener(e -> {
            BinContent selected = (BinContent) ingredientBox.getSelectedItem();
            int capacity = selected.getCapacity();
            capacityLabel.setText("Max Capacity: " + capacity);
            quantitySpinner.setModel(new SpinnerNumberModel(1.0, 0.0, capacity, 1.0));
        });

        revalidate();
        repaint();
    }

    private void handleConfirm(){

        BinContent selected = (BinContent) ingredientBox.getSelectedItem();
        double quantity = (double) quantitySpinner.getValue();

        if (quantity > selected.getCapacity() || quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Must be between 1 and " + selected.getCapacity());
            return;
        }

        StorageBin bin = bins.get(currentBinIndex);
        controller.assignItemToBin(truck, bin, selected.clone(), quantity);

        JOptionPane.showMessageDialog(this, "Bin #" + bin.getBinNumber() + " loaded with " + quantity + " of " + selected.getName());

        currentBinIndex++;
        updateUIForCurrentBin();
    }

    private void handleSkip() {
        StorageBin bin = bins.get(currentBinIndex);
        JOptionPane.showMessageDialog(this, "Skipped Bin #" + bin.getBinNumber());

        currentBinIndex++;
        updateUIForCurrentBin();
    }
}
