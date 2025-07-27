package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulateTruckPanel extends BasePanel{

    private final TruckController truckController;
    private final MenuView menuView;
    private BackgroundPanel backgroundPanel;
    private JPanel titleWrapper, formPanel, formBackgroundPanel, truckSelectorPanel;
    private JLabel titleLabel, truckSelectorLabel, errorLabel;
    private JComboBox<CoffeeTruck> truckComboBox;
    private DefaultComboBoxModel<CoffeeTruck> model;
    private JButton prepareDrinkButton, displayTruckInfoButton, truckMaintenanceButton, exitButton;
    private ArrayList<CoffeeTruck> trucks;

    public SimulateTruckPanel(TruckController truckController, MenuView menuView){

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
        titleLabel = new JLabel("Truck Simulation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(false);

        //Force label to hug its text
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
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 20, 200));

        //Form Panel
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());
        formPanel.setBackground((new Color(123, 79, 43)));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 20, 200));

        //Set up truck selector layout
        truckSelectorPanel = new JPanel(new GridBagLayout());
        truckSelectorPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        trucks = truckController.getTrucks();

        truckComboBox = new JComboBox<>();
        truckComboBox.setPreferredSize(new Dimension(300, 25));
        truckSelectorLabel = new JLabel("Select a Coffee Truck:");
        truckSelectorLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        truckSelectorLabel.setForeground(Color.WHITE);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

// Layout setup
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridBagLayout());
        comboPanel.setOpaque(false);
        comboPanel.setPreferredSize(new Dimension(300, 25));

        truckComboBox.addActionListener(e -> {
            CoffeeTruck selected = (CoffeeTruck) truckComboBox.getSelectedItem();
            if (selected != null) {
                System.out.println("Selected: " + selected.getName());
            }
        });

        // Add components to comboPanel
        gbc.gridy = 0;
        comboPanel.add(truckSelectorLabel, gbc);
        comboPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        gbc.gridy++;
        comboPanel.add(truckComboBox, gbc);
        comboPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gbc.gridy++;
        comboPanel.add(errorLabel, gbc);

        formPanel.add(comboPanel, BorderLayout.WEST); //Add Truck Selector to the left

        /*
        //Row 0: Error Label
        gbc.gridy = 0;
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        truckSelectorPanel.add(errorLabel, gbc);

        //Row 1: Truck Selector Label
        gbc.gridy++;
        truckSelectorLabel = new JLabel();
        truckSelectorPanel.add(truckSelectorLabel, gbc);

        //Row 2: Truck Selector ComboBox
        gbc.gridy++;
        truckComboBox = new JComboBox<>();
        truckComboBox.setPreferredSize(new Dimension(300, 25));

        truckSelectorPanel.add(truckComboBox, gbc);

        formPanel.add(truckSelectorPanel, BorderLayout.WEST); //Add Truck Selector to the left

        truckComboBox.addActionListener(e -> {
            CoffeeTruck selected = (CoffeeTruck) truckComboBox.getSelectedItem();

            if (selected != null){
                prepareDrinkButton.setEnabled(true);
                displayTruckInfoButton.setEnabled(true);
                truckMaintenanceButton.setEnabled(true);
            }
        });
*/
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

        exitButton = createButton("Exit to Main Menu");

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
        });

        displayTruckInfoButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.showPanel("TRUCK_INFO");
        });

        truckMaintenanceButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.showPanel("TRUCK_MAINTENANCE");
        });

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            // Add a slight delay before exiting so the sound plays
            menuView.showPanel("MAIN_MENU");
        });

        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
    }


    public void startSimulation(){

        //this.trucks = truckController.getTrucks();
        setupTruckSelector();
    }
    //ArrayList<CoffeeTruck> trucks
    private void setupTruckSelector() {

        trucks = truckController.getTrucks();

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
                if(truck != null) {
                    truckComboBox.addItem(truck);
                    System.out.println("Adding truck: " + truck.getName());
                }
            }
        }

        formPanel.revalidate();
        formPanel.repaint();
    }
}
