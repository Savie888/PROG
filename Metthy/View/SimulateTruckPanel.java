package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulateTruckPanel extends BasePanel{

    private final TruckController truckController;

    private JPanel formPanel;
    private JLabel truckSelectorLabel, errorLabel;
    private JComboBox<CoffeeTruck> truckComboBox;
    private JButton selectButton, prepareDrinkButton, displayTruckInfoButton, truckMaintenanceButton;

    private CoffeeTruck selectedTruck;

    public SimulateTruckPanel(TruckController truckController){

        this.truckController = truckController;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("regular.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Truck Simulation");
        backgroundPanel.add(title, BorderLayout.NORTH);

        //Form Background Panel
        JPanel formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        //Form Panel
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());
        formPanel.setBackground((new Color(123, 79, 43)));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 20, 200));

        //Set up truck selector layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        truckComboBox = new JComboBox<>();
        truckComboBox.setPreferredSize(new Dimension(300, 25));
        truckSelectorLabel = new JLabel("Select a Coffee Truck:");
        truckSelectorLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        truckSelectorLabel.setForeground(Color.BLACK);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        //Setup Panel
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridBagLayout());
        comboPanel.setOpaque(false);
        comboPanel.setPreferredSize(new Dimension(300, 25));

        truckComboBox.addActionListener(e -> {
            CoffeeTruck selected = (CoffeeTruck) truckComboBox.getSelectedItem();
            if (selected != null)
                selectButton.setEnabled(true); //If selected truck is valid, enable select button
        });

        //Add components to comboPanel

        //Row 0: Truck Select Label
        gbc.gridy = 0;
        comboPanel.add(truckSelectorLabel, gbc);

        //Row 1: Truck Combo Box
        gbc.gridy++;
        comboPanel.add(truckComboBox, gbc);

        //Row 2: Error Label
        gbc.gridy++;
        comboPanel.add(errorLabel, gbc);

        //Row 3: Select Button
        gbc.gridy++;
        selectButton = createButton("Select Truck");
        selectButton.setEnabled(false);
        comboPanel.add(selectButton, gbc);

        selectButton.addActionListener(e ->{
            playSound("select_sound_effect.wav");
            selectedTruck = (CoffeeTruck) truckComboBox.getSelectedItem();

            prepareDrinkButton.setEnabled(true);
            displayTruckInfoButton.setEnabled(true);
            truckMaintenanceButton.setEnabled(true);
        });

        formPanel.add(comboPanel, BorderLayout.WEST); //Add comboPanel to the left of formPanel

        //Set up button panel layout
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        prepareDrinkButton = createButton("Prepare Drink");
        prepareDrinkButton.setEnabled(false);
        displayTruckInfoButton = createButton("Display Truck Information");
        displayTruckInfoButton.setEnabled(false);
        truckMaintenanceButton = createButton("Truck Maintenance");
        truckMaintenanceButton.setEnabled(false);

        JButton exitButton = createButton("Exit to Main Menu");

        //Row 0: Prepare Drink Button
        gbc.gridy = 0;
        buttonPanel.add(prepareDrinkButton, gbc);
        buttonPanel.add(Box.createVerticalStrut(15));

        //Row 1: Display Truck Information Button
        gbc.gridy++;
        buttonPanel.add(displayTruckInfoButton, gbc);
        buttonPanel.add(Box.createVerticalStrut(15));

        //Row 2: Truck Maintenance Button
        gbc.gridy++;
        buttonPanel.add(truckMaintenanceButton, gbc);
        buttonPanel.add(Box.createVerticalStrut(15));

        //Row 3: Exit Button
        gbc.gridy++;
        buttonPanel.add(exitButton, gbc);
        buttonPanel.add(Box.createVerticalStrut(15));

        formPanel.add(buttonPanel, BorderLayout.EAST);

        prepareDrinkButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.showPanel("PREPARE_DRINK");
            resetTruckSelector();
        });

        displayTruckInfoButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            truckController.truckInformationPanel(selectedTruck);
            resetTruckSelector();
        });

        truckMaintenanceButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.getMenuController().sh
            truckController.truckMaintenancePanel(selectedTruck);
            resetTruckSelector();
        });

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //Add a slight delay before exiting so the sound plays
            truckController.mainMenuPanel();
            resetTruckSelector();
        });

        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }


    public void startSimulation(){

        ArrayList<CoffeeTruck> trucks = truckController.getTrucks();

        if (trucks.isEmpty()) {
            errorLabel.setText("No trucks available");
            truckSelectorLabel.setText("");
        }

        else {
            errorLabel.setText("");
            truckSelectorLabel.setText("Select a Coffee Truck:");

            truckComboBox.removeAllItems();

            for(int i = 0; i < trucks.size(); i++){

                CoffeeTruck truck = trucks.get(i);

                if(truck != null)
                    truckComboBox.addItem(truck);
            }
        }

        formPanel.revalidate();
        formPanel.repaint();
    }

    public void resetTruckSelector(){

        truckComboBox.setSelectedItem(null); //Deselect
        selectedTruck = null;

        // Disable buttons again
        selectButton.setEnabled(false);
        prepareDrinkButton.setEnabled(false);
        displayTruckInfoButton.setEnabled(false);
        truckMaintenanceButton.setEnabled(false);

        // Optional: also clear error messages or labels
        errorLabel.setText("");
    }
}
