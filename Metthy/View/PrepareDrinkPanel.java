package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.Drink;
import Metthy.Model.SpecialCoffeeTruck;
import Metthy.Model.StorageBin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrepareDrinkPanel extends BasePanel{

    private TruckController truckController;

    private JPanel formPanel, drinkListPanel;
    private DefaultListModel<Drink> drinkListModel;
    private JList<Drink> drinkList;

    private CoffeeTruck selectedTruck;

    public PrepareDrinkPanel(TruckController truckController){

        this.truckController = truckController;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("regular.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Drink Preparation");
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
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 20, 300));

        //Set up drink menu panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        JPanel drinkMenuPanel = new JPanel(new GridBagLayout());
        drinkMenuPanel.setOpaque(false);
        drinkMenuPanel.setPreferredSize(new Dimension(300, 25));

        drinkListPanel = new JPanel();
        drinkListPanel.setLayout(new BoxLayout(drinkListPanel, BoxLayout.Y_AXIS));
        drinkListPanel.setOpaque(false);

        //List of drinks
        gbc.gridy = 0;
        drinkListModel = new DefaultListModel<>();

        drinkList = new JList<>(drinkListModel);
        drinkList.setOpaque(false);

        drinkMenuPanel.add(drinkListPanel, gbc);

        //Drink Utilities Panel
        JPanel drinkPrepPanel = new JPanel();
        drinkPrepPanel.setLayout(new GridBagLayout());
        drinkPrepPanel.setPreferredSize(new Dimension(300, 25));
        drinkPrepPanel.setOpaque(false);

        //Row 0: Drink type label
        gbc.gridy = 0;
        JLabel typeLabel = new JLabel("Select Drink Type");
        String[] drinkTypes = {"Americano", "Latte", "Cappuccino"};
        drinkPrepPanel.add(typeLabel, gbc);

        //Row 1: Drink type selector
        gbc.gridy++;
        JComboBox<String> drinkTypeCombo = new JComboBox<>(drinkTypes);
        drinkTypeCombo.setMaximumSize(new Dimension(150, 30));
        drinkPrepPanel.add(drinkTypeCombo, gbc);
        drinkPrepPanel.add(Box.createVerticalStrut(10));

        //Row 2: Drink size label
        gbc.gridy++;
        JLabel sizeLabel = new JLabel("Select Size");
        String[] sizes = { "Small", "Medium", "Large" };
        drinkPrepPanel.add(sizeLabel, gbc);

        //Row 3: Drink size selector
        gbc.gridy++;
        JComboBox<String> sizeCombo = new JComboBox<>(sizes);
        sizeCombo.setMaximumSize(new Dimension(150, 30));
        drinkPrepPanel.add(sizeCombo, gbc);
        drinkPrepPanel.add(Box.createVerticalStrut(10));

        //Row 4: Prepare button
        gbc.gridy++;
        JButton prepareButton = new JButton("Prepare Drink");
        prepareButton.setBackground(Color.black);
        prepareButton.setForeground(Color.WHITE);
        prepareButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        prepareButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        drinkPrepPanel.add(prepareButton, gbc);

        prepareButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");

            String type = (String) drinkTypeCombo.getSelectedItem();
            String size = (String) sizeCombo.getSelectedItem();
            prepareDrink(type, size);
        });

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false); // match your translucent look

        JButton exitButton = createButton("Exit to Simulate Menu");
        bottomPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            truckController.simulateTruckPanel();
        });

        formPanel.add(drinkMenuPanel, BorderLayout.WEST);
        formPanel.add(drinkPrepPanel, BorderLayout.EAST);

        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add bottom panel

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    public void startPreparation(CoffeeTruck truck){

        this.selectedTruck = truck;

        setupDrinkMenu();
    }

    private void setupDrinkMenu(){

        ArrayList<Drink> drinks = truckController.getDrinks();

        drinkListModel.clear();
        drinkListPanel.removeAll();

        JLabel drinkHeader = new JLabel("Drink Menu");
        drinkHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        drinkListPanel.add(drinkHeader);
        drinkListPanel.add(Box.createVerticalStrut(10));

        for (Drink drink : drinks) {
            JLabel drinkLabel = new JLabel(drink.toString(), SwingConstants.CENTER);
            drinkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            drinkListPanel.add(drinkLabel);
        }

        drinkListPanel.revalidate();
        drinkListPanel.repaint();
    }

    private String selectBrewType(){

        String[] options = { "Standard", "Light", "Strong", "Custom" };
        String brewType = (String) JOptionPane.showInputDialog(
                null,
                "Select Brew Type:",
                "Brew Type Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                "Standard"
        );

        // If user cancels or closes dialog
        if (brewType == null) {
            return "Standard";
        }

        return brewType;
    }

    private double getCustomBrewRatio(){

        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1); // default, min, max, step
        JSpinner spinner = new JSpinner(model);

        double result = JOptionPane.showConfirmDialog(
                null,
                spinner,
                "Select Custom Espresso-to-Water Ratio (Standard ratio is 18.0)",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION)
            return (double) spinner.getValue();

        else
            return 18.0; //Default brew ratio
    }

    private StorageBin[] getStorageBins(String size){

        StorageBin beanBin = truckController.findBin(selectedTruck, "Coffee Bean");
        StorageBin milkBin = truckController.findBin(selectedTruck, "Milk");
        StorageBin waterBin = truckController.findBin(selectedTruck, "Water");
        StorageBin cupBin = truckController.findBin(selectedTruck, size + " Cup");
        StorageBin[] bins = { beanBin, milkBin, waterBin, cupBin };

        return bins;
    }

    public void displayPreparationSteps(String type, String size, String brewType, double price, double[] ingredients){

        StringBuilder steps = new StringBuilder();

        steps.append(String.format(">>> Preparing %s Cup...\n", size));
        steps.append(String.format(">>> Brewing %s Espresso - %.2f grams of coffee...\n", brewType, ingredients[0]));

        if (ingredients[1] > 0)
            steps.append(">>> Adding Milk...\n");

        if (type.equalsIgnoreCase("Americano"))
            steps.append(">>> Adding Water...\n");

        steps.append(String.format(">>> %s Done!", type));
        steps.append(String.format("\nTotal Price: $%.2f", price));

        JTextArea textArea = new JTextArea(steps.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Drink Preparation",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void prepareDrink(String type, String size){

        Drink drink = truckController.getDrink(type, size); //Get drink object

        double ratio;
        String brewType;

        //Get brew type and ratios
        if(selectedTruck instanceof SpecialCoffeeTruck){

            brewType = selectBrewType();

            if(brewType.equals("Custom"))
                ratio = getCustomBrewRatio();

            else
                ratio = truckController.getBrewRatio(brewType);
        }

        else{
            brewType = "Standard";
            ratio = truckController.getBrewRatio(brewType); //Regular truck drinks can only brew standard drinks
        }

        double[] ingredients = truckController.getRequiredIngredients(type, size, ratio); //Get required ingredients
        StorageBin[] bins = getStorageBins(size); //Get storage bins to use

        if (truckController.hasSufficientIngredients(bins, ingredients)) {
            truckController.useIngredients(bins, ingredients); //Use ingredients from bins
            displayPreparationSteps(type, size, brewType, drink.getPrice(), ingredients);
            truckController.addToTotalSales(selectedTruck, drink.getPrice());
            truckController.recordSale(selectedTruck, type, size, ingredients[0], ingredients[1], ingredients[2], drink.getPrice());
        }

        else {
            JOptionPane.showMessageDialog(
                    null,
                    "Not enough ingredients or cups.\nDrink preparation cancelled.",
                    "Insufficient Ingredients",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
