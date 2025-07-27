package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RemoveTruckPanel extends BasePanel{

    private TruckController truckController;
    private MenuView menuView;
    private BackgroundPanel backgroundPanel;
    private JPanel titleWrapper, formBackgroundPanel, formPanel, comboPanel, bottomPanel;
    private JLabel titleLabel, truckSelectorLabel, errorLabel;
    private JButton selectButton, removeButton, exitButton;
    private JComboBox<CoffeeTruck> truckComboBox;
    private CoffeeTruck selectedTruck;
    private ArrayList<CoffeeTruck> trucks;

    public RemoveTruckPanel(TruckController truckController, MenuView menuView){

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
        titleLabel = new JLabel("Truck Removal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(false);
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
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 250, 20, 250));

        //Form Panel
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());
        formPanel.setBackground((new Color(123, 79, 43)));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 600, 20, 600));

        //Set up truck selector layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
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
        comboPanel = new JPanel();
        comboPanel.setLayout(new GridBagLayout());
        comboPanel.setOpaque(false);
        comboPanel.setPreferredSize(new Dimension(300, 200));

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
            removeButton.setEnabled(true);
        });

        //Row 4: Remove Button
        gbc.gridy++;
        removeButton = createButton("Remove Truck");
        removeButton.setEnabled(false);
        comboPanel.add(removeButton, gbc);

        removeButton.addActionListener(e ->{
            playSound("select_sound_effect.wav");
            selectedTruck = (CoffeeTruck) truckComboBox.getSelectedItem();
            truckController.truckRemoval(selectedTruck);
            resetTruckSelector();
        });

        //Bottom Panel
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false); // match your translucent look

        JButton exitButton = createButton("Exit to Main Menu");
        bottomPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            menuView.showPanel("MAIN_MENU");
            resetTruckSelector();
        });

        formPanel.add(comboPanel, BorderLayout.CENTER);
        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add bottom panel
    }

    public void start(){

        trucks = truckController.getTrucks();

        truckSelectorLabel.setText("Select a Coffee Truck:");

        truckComboBox.removeAllItems();

        for(int i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);

            if(truck != null)
                truckComboBox.addItem(truck);
        }

        formPanel.revalidate();
        formPanel.repaint();
    }

    public void resetTruckSelector(){

        truckComboBox.setSelectedItem(null); //Deselect
        selectedTruck = null;

        // Disable buttons again
        selectButton.setEnabled(false);
        removeButton.setEnabled(false);
    }
}
