package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.Drink;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrepareDrinkPanel extends BasePanel{

    private TruckController truckController;

    private JPanel formPanel, drinkListPanel;
    private DefaultListModel<Drink> drinkListModel;
    private JList<Drink> drinkList;

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
        JPanel drinkPrepPanel = new TranslucentPanel();
        drinkPrepPanel.setLayout(new BoxLayout(drinkPrepPanel, BoxLayout.Y_AXIS));
        drinkPrepPanel.setOpaque(false);

        //Drink type selector
        JLabel typeLabel = new JLabel("Select Drink Type");
        String[] drinkTypes = {"Americano", "Latte", "Cappuccino"};
        JComboBox<String> drinkTypeCombo = new JComboBox<>(drinkTypes);
        drinkPrepPanel.add(typeLabel);
        drinkPrepPanel.add(drinkTypeCombo);
        drinkPrepPanel.add(Box.createVerticalStrut(10));

        //Drink size selector
        JLabel sizeLabel = new JLabel("Select Size");
        String[] sizes = { "Small", "Medium", "Large" };
        JComboBox<String> sizeCombo = new JComboBox<>(sizes);
        drinkPrepPanel.add(sizeLabel);
        drinkPrepPanel.add(sizeCombo);
        drinkPrepPanel.add(Box.createVerticalStrut(10));

        //Prepare button
        JButton prepareButton = createButton("Prepare Drink");

        drinkPrepPanel.add(prepareButton);


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
}
