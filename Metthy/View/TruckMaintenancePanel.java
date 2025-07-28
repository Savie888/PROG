package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;

import javax.swing.*;
import java.awt.*;

public class TruckMaintenancePanel extends BasePanel {

    private CoffeeTruck selectedTruck;

    public TruckMaintenancePanel(TruckController truckController){

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("special.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        TitleWrapper title = new TitleWrapper("Truck Maintenance");
        backgroundPanel.add(title, BorderLayout.NORTH);

        //Form Background Panel
        JPanel formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 20, 200));

        //Center wrapper for the button panel
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton restockButton = createButton("Restock Bins");
        JButton modifyButton = createButton("Modify Bins");
        JButton emptyButton = createButton("Empty Bins");
        JButton editNameButton = createButton("Edit Truck Name");
        JButton editLocationButton = createButton("Edit Truck Location");
        JButton editPricesButton = createButton("Edit Prices");
        JButton exitButton = createButton("Exit to Simulation Menu");

        buttonPanel.add(restockButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(modifyButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(emptyButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(editNameButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(editLocationButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(editPricesButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        restockButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.showPanel("CREATE_TRUCK");
        });

        modifyButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuController.showRemoveTruckPanel();
        });

        emptyButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");

        });

        editNameButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.showPanel("DASHBOARD");
        });

        editLocationButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.showPanel("DASHBOARD");
        });

        editPricesButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            //menuView.showPanel("DASHBOARD");
        });

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            truckController.simulateTruckPanel(); //Return to simulate truck panel
        });

        centerWrapper.add(buttonPanel);
        formBackgroundPanel.add(centerWrapper, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    public void startMaintenance(CoffeeTruck truck){

        this.selectedTruck = truck;
    }
}
