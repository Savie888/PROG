package Metthy.View;

import Metthy.Controller.MainController;
import Metthy.Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrepareDrinkPanel extends BasePanel{

    private MainController mainController;

    private JPanel formPanel, drinkListPanel;
    private DefaultListModel<Drink> drinkListModel;
    private JList<Drink> drinkList;

    private CoffeeTruck selectedTruck;

    public PrepareDrinkPanel(MainController mainController){

        this.mainController = mainController;

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

            if(selectedTruck instanceof SpecialCoffeeTruck)
                prepareSpecialDrink(type, size);

            else
                prepareDrink(type, size);
        });

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false); // match your translucent look

        JButton exitButton = createButton("Exit to Simulate Menu");
        bottomPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            mainController.simulateTruckPanel();
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

    /**
     * Sets up the drinks menu
     */
    private void setupDrinkMenu(){

        ArrayList<Drink> drinks = mainController.getDrinks();

        drinkListModel.clear();
        drinkListPanel.removeAll();

        JLabel drinkHeader = new JLabel("Drink Menu");
        drinkHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        drinkListPanel.add(drinkHeader);
        drinkListPanel.add(Box.createVerticalStrut(10));

        mainController.setBaseDrinkPrices();

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

        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.1, 50.0, 0.1); // default, min, max, step
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

        StorageBin beanBin = mainController.findBin(selectedTruck, "Coffee Bean");
        StorageBin milkBin = mainController.findBin(selectedTruck, "Milk");
        StorageBin waterBin = mainController.findBin(selectedTruck, "Water");
        StorageBin cupBin = mainController.findBin(selectedTruck, size + " Cup");
        StorageBin[] bins = { beanBin, milkBin, waterBin, cupBin };

        return bins;
    }

    private void displayPreparationSteps(String type, String size, String brewType, double price, double[] ingredients){

        StringBuilder steps = new StringBuilder();

        steps.append(String.format(">>> Preparing %s Cup...\n", size));
        steps.append(String.format(">>> Brewing %s Espresso - %.2f grams of coffee...\n", brewType, ingredients[0]));

        if (ingredients[1] > 0)
            steps.append(">>> Adding Milk...\n");

        if (type.equalsIgnoreCase("Americano"))
            steps.append(">>> Adding Water...\n");

        steps.append(String.format(">>> %s Done!", type));
        steps.append(String.format("\nTotal Price: ₱%.2f", price));

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

    /**
     * Handles the process of preparing a drink.
     */
    private void prepareDrink(String type, String size){

        Drink drink = mainController.getDrink(type, size); //Get drink object

        double ratio;
        String brewType;

        //Get brew type and ratios
        brewType = "Standard";
        ratio = mainController.getBrewRatio(brewType); //Regular truck drinks can only brew standard drinks

        double[] ingredients = mainController.getRequiredIngredients(type, size, ratio); //Get required ingredients
        StorageBin[] bins = getStorageBins(size); //Get storage bins to use

        if (mainController.hasSufficientIngredients(bins, ingredients)) {
            mainController.useIngredients(bins, ingredients); //Use ingredients from bins
            displayPreparationSteps(type, size, brewType, drink.getPrice(), ingredients);
            mainController.addToTotalSales(selectedTruck, drink.getPrice());
            mainController.recordSale(selectedTruck, type, size, ingredients[0], ingredients[1], ingredients[2], drink.getPrice());
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

    //Yes or No option pane
    private boolean askYesNo(String question) {

        int result = JOptionPane.showConfirmDialog(
                null,
                question,
                "Special Drink Customization",
                JOptionPane.YES_NO_OPTION
        );

        return result == JOptionPane.YES_OPTION;
    }

    private boolean addSyrup(){

        boolean addSyrupAddOns = askYesNo("Add syrup add-ons?");

        return addSyrupAddOns;
    }

    private ArrayList<BinContent> selectAddOns(){

        boolean continueAdding = true;
        ArrayList<Syrup> availableSyrups = mainController.getAvailableSyrup(selectedTruck);
        ArrayList<BinContent> addOns = new ArrayList<>();

        if (availableSyrups.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No syrup available.");
            return addOns;
        }

        while(continueAdding){

            String[] syrupNames = new String[availableSyrups.size()];
            for (int i = 0; i < availableSyrups.size(); i++) {
                syrupNames[i] = availableSyrups.get(i).getName();
            }

            // Let the user pick a syrup
            String selectedSyrup = (String) JOptionPane.showInputDialog(
                    null,
                    "Select a syrup add-on:",
                    "Syrup Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    syrupNames,
                    syrupNames.length > 0 ? syrupNames[0] : null
            );

            if (selectedSyrup == null)
                break; // user cancelled

            BinContent addOn = null;

            // Find the selected BinContent object
            for (BinContent syrup : availableSyrups) {
                if (syrup.getName().equals(selectedSyrup)) {
                    addOn = syrup;
                    break;
                }
            }

            // Validate and add
            if (addOn != null && mainController.hasSufficientSyrup(selectedTruck, addOn.getName())) {
                addOns.add(addOn);
                addOn.useQuantity(1);
            }

            else
                JOptionPane.showMessageDialog(null, "Insufficient syrup for that option.");

            // Ask if user wants to add more
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Continue adding syrup?",
                    "Add Another?",
                    JOptionPane.YES_NO_OPTION
            );

            continueAdding = (result == JOptionPane.YES_OPTION);
        }

        return addOns;
    }

    private boolean addExtraShots(){

        boolean addExtraShots = askYesNo("Add extra espresso shots?");

        return addExtraShots;
    }

    private int selectExtraShots(double coffeeGrams, double remainingCoffeeGrams){

        int maxShots;

        maxShots = (int) (remainingCoffeeGrams / coffeeGrams);

        if (maxShots == 0) {
            JOptionPane.showMessageDialog(null, "Not enough beans for extra shots.");
            return 0;
        }

        // Spinner for selecting shots
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, maxShots, 1));
        spinner.setPreferredSize(new Dimension(100, 25));

        int option = JOptionPane.showOptionDialog(
                null,
                spinner,
                "Enter number of extra espresso shots (0 - " + maxShots + "):",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        if (option == JOptionPane.OK_OPTION) {
            return (int) spinner.getValue();
        }

        return 0;  //User cancelled
    }

    private void displaySpecialPreparationSteps(String type, String size, String brewType, double price, double[] ingredients,
                                                ArrayList<BinContent> addOns, int extraShots, double extraShotCost, double extraCoffeeGrams){

        StringBuilder steps = new StringBuilder();

        steps.append(String.format(">>> Preparing %s Cup...\n", size));
        steps.append(String.format(">>> Brewing %s Espresso - %.2f grams of coffee...\n", brewType, ingredients[0]));

        if (ingredients[1] > 0)
            steps.append(">>> Adding Milk...\n");

        if (type.equalsIgnoreCase("Americano"))
            steps.append(">>> Adding Water...\n");

        if(!addOns.isEmpty()){
            for(BinContent addOn : addOns){
                steps.append(">>> Adding " + addOn.getName() + " Syrup\n");
                price += mainController.getSyrupOzPrice();
            }
        }

        if(extraShots != 0){
            steps.append(String.format(">>> Added %d extra shot%s (%.2f g beans)\n", extraShots, extraShots == 1 ? "" : "s", extraCoffeeGrams));
            price += extraShotCost;
        }

        steps.append(String.format(">>> %s Done!", type));
        steps.append(String.format("\nTotal Price: ₱%.2f", price));

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

    private void prepareSpecialDrink(String type, String size){

        ArrayList<BinContent> addOns = new ArrayList<>();
        double coffeeGrams, remainingCoffeeGrams, extraCoffeeGrams = 0, extraShotCost = 0;
        int extraShots = 0;

        Drink drink = mainController.getDrink(type, size); //Get drink object

        double ratio;
        String brewType;

        //Get brew type and ratios
        brewType = selectBrewType();

        if(brewType.equals("Custom"))
            ratio = getCustomBrewRatio();

        else
            ratio = mainController.getBrewRatio(brewType);

        double[] ingredients = mainController.getRequiredIngredients(type, size, ratio); //Get required ingredients
        StorageBin[] bins = getStorageBins(size); //Get storage bins to use

        if (mainController.hasSufficientIngredients(bins, ingredients)) {

            coffeeGrams = ingredients[0]; //Get coffee beans prior to using
            mainController.useIngredients(bins, ingredients); //Use ingredients from bins
            remainingCoffeeGrams = bins[0].getItemQuantity(); //Get remaining coffee beans

            boolean addOn = addSyrup();

            if(addOn)
                addOns = selectAddOns();

            boolean extra = addExtraShots();

            if(extra){
                extraShots = selectExtraShots(coffeeGrams, remainingCoffeeGrams);
                extraCoffeeGrams = coffeeGrams * extraShots;
                bins[0].useQuantity(extraCoffeeGrams);
                extraShotCost = extraShots * mainController.getExtraShotPrice();
            }

            displaySpecialPreparationSteps(type, size, brewType, drink.getPrice(), ingredients, addOns, extraShots, extraShotCost, extraCoffeeGrams);

            double price = drink.getPrice() + addOns.size() * mainController.getSyrupOzPrice() + extraShotCost;
            mainController.addToTotalSales(selectedTruck, price);
            mainController.recordSpecialSale((SpecialCoffeeTruck) selectedTruck, type, size, brewType,
                    ingredients[0], ingredients[1], ingredients[2], addOns, extraShots, price);
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
