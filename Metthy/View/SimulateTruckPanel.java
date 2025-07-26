package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.SpecialCoffeeTruck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulateTruckPanel extends BasePanel{

    private final TruckController truckController;
    private final MenuView menuView;
    private BackgroundPanel backgroundPanel;
    private JPanel titleWrapper, formPanel, formBackgroundPanel;
    private JLabel titleLabel;
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
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 20, 200));

        trucks = truckController.getTrucks();

        DefaultComboBoxModel<CoffeeTruck> comboBoxModel = new DefaultComboBoxModel<>();

        for(int i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);
            comboBoxModel.addElement(truck);
        }

        JComboBox<CoffeeTruck> truckComboBox = new JComboBox<CoffeeTruck>(comboBoxModel);
        truckComboBox.setMaximumSize(new Dimension(200, 200));
        truckComboBox.addActionListener(e -> {

            CoffeeTruck selected = (CoffeeTruck) truckComboBox.getSelectedItem();
                if (selected != null) {
                    //simulateTruck(selected); // Or enable buttons, update info, etc.
                    prepareDrinkButton.setEnabled(true);
                    displayTruckInfoButton.setEnabled(true);
                    truckMaintenanceButton.setEnabled(true);
                }

        });

        formPanel.add(truckComboBox, BorderLayout.WEST); //Add scroll panel to the left

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        //Set up layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

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
}
