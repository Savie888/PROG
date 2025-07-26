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
        ingredientLabel = new JLabel("Select Ingredient:");
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
        quantityLabel = new JLabel("Enter Quantity:");
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
        confirmButton = new JButton("Confirm");
        skipButton = new JButton("Skip Bin");

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
            menuView.showPanel("CREATE_TRUCK");
        });

        //Add formPanel into the background container
        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add south panel
    }

    public void startLoadout(CoffeeTruck truck){
        this.truck = truck;
        this.bins = truck.getBins();
        this.currentBinIndex = 0;

        updateUIForCurrentBin();
    }

    private void updateUIForCurrentBin(){

        if (currentBinIndex >= bins.size()) {
            //truckView.showLoadoutComplete(truck);
            menuView.showPanel("CREATE_TRUCK");

            SwingUtilities.invokeLater(() -> {
                int repeat = repeat();

                if (repeat == JOptionPane.NO_OPTION)
                    menuView.showPanel("MAIN_MENU");
            });

            return;
        }

        StorageBin bin = bins.get(currentBinIndex);
        ingredients = controller.getIngredients();

        binLabel.setText("Setting up Bin #" + bin.getBinNumber());

        ingredientBox.removeAllItems();

        for(int i = 0; i < ingredients.size(); i++){

            BinContent ingredient = ingredients.get(i);
            ingredientBox.addItem(ingredient);
        }


        quantitySpinner.setValue(1.0);
        capacityLabel.setText("");

        ingredientBox.addActionListener(e -> {
            BinContent selected = (BinContent) ingredientBox.getSelectedItem();

            if (selected == null)
                return;

            int capacity = selected.getCapacity();
            capacityLabel.setText("Max Capacity: " + capacity);
            quantitySpinner.setModel(new SpinnerNumberModel(1.0, 0.0, capacity, 1.0));
        });

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
        controller.assignItemToBin(truck, bin, selected.clone(), quantity);

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
