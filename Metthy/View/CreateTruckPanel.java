package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;

import javax.swing.*;
import java.awt.*;

public class CreateTruckPanel extends BasePanel {

    private final TruckController truckController;

    private JLabel errorLabel;
    private JTextField nameField, locationField;
    private JComboBox<String> typeBox;
    private CoffeeTruck truck;
    private static boolean pricesInitialized = false;

    public CreateTruckPanel(TruckController truckController){

        this.truckController = truckController;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("regular.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Truck Creation");
        backgroundPanel.add(title, BorderLayout.NORTH);

        //Form Background Panel
        JPanel formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 800, 20, 800));

        //Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground((new Color(123, 79, 43)));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 50, 60));

        //Set up layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        //Row 0: Truck Name Label
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Enter truck name:");
        formPanel.add(nameLabel, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        //Row 1: Truck Name Field
        gbc.gridy++;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        //Row 2: Truck Location Label
        gbc.gridy++;
        JLabel locationLabel = new JLabel("Enter truck location:");
        formPanel.add(locationLabel, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        //Row 3: Truck Location Field
        gbc.gridy++;
        locationField = new JTextField(20);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(locationField, gbc);

        //Row 4: Truck Type ComboBox
        gbc.gridy++;
        String[] truckTypes = {"1. Regular", "2. Special"};
        JLabel truckTypeLabel = new JLabel("Select truck type:");
        formPanel.add(truckTypeLabel, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        gbc.gridy++;
        typeBox = new JComboBox<>(truckTypes);
        formPanel.add(typeBox, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        //Row 5: Create Truck Button
        gbc.gridy++;
        JButton createButton = createButton("Create Truck");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(createButton, gbc);

        createButton.addActionListener(e -> {

            playSound("select_sound_effect.wav"); //Play sound effect

            String name = nameField.getText();
            String location = locationField.getText();
            int type = Integer.parseInt(((String) typeBox.getSelectedItem()).substring(0, 1));

            // Validate name
            if (name.isEmpty()) {
                errorLabel.setText("Name can't be empty.");
                return;
            }

            if (!truckController.isTruckNameUnique(name)) {
                errorLabel.setText("Name already used.");
                return;
            }

            // Validate location
            if (location.isEmpty()) {
                errorLabel.setText("Location can't be empty.");
                return;
            }

            if (!truckController.isTruckLocationUnique(location)) {
                errorLabel.setText("Location already used.");
                return;
            }

            //Clear errors and continue
            errorLabel.setText(" ");

            truck = truckController.truckCreation(name, location, type); //Create truck

            //Set loadout option
            int setLoadout = setLoadoutOption();

            if (setLoadout == JOptionPane.YES_OPTION) {

                int setDefault = setDefaultLoadoutOption();

                if(setDefault == JOptionPane.YES_OPTION){
                    truckController.setDefaultTruckLoadout(truck); //Set to default loadout
                    repeat();
                }

                else{
                    resetFields();
                    truckController.truckLoadoutPanel(truck, () -> truckController.truckCreationPanel(), true); //Show truck loadout panel
                }
            }

            else
                repeat();
        });

        //Row 6: Error Label
        gbc.gridy++;
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        formPanel.add(errorLabel, gbc);

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false); // match your translucent look

        JButton exitButton = createButton("Exit to Main Menu");
        bottomPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            truckController.mainMenuPanel();
        });

        //Add formPanel into the background container
        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add bottom panel

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    private int setLoadoutOption(){

        int response = JOptionPane.showConfirmDialog(
                this,
                "Set up storage bins?",
                "Setup Truck Loadout",
                JOptionPane.YES_NO_OPTION
        );

        return response;
    }

    private int setDefaultLoadoutOption(){

        int set = JOptionPane.showConfirmDialog(
                this,
                "set to default loadout",
                "Set Default Truck Loadout",
                JOptionPane.YES_NO_OPTION
        );

        return set;
    }

    private void resetFields(){

        nameField.setText("");
        locationField.setText("");
        typeBox.setSelectedIndex(0);
        errorLabel.setText(" ");
    }

    private void repeat(){

        String[] options = {"Yes, create another", "Back to Menu"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Truck created successfully!\nCreate another one?",
                "Success",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        resetFields();

        if (!pricesInitialized) {
            initializeIngredientPrices();
            pricesInitialized = true;
        }

        if(choice == 1)
            truckController.mainMenuPanel(); //Return to main menu
    }

    /**
     * Prompts the user to input the prices for coffee ingredients (cups, coffee beans, milk, and water).
     */
    private void initializeIngredientPrices(){

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
