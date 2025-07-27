package Metthy.View;

import Metthy.Controller.MenuController;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends BasePanel{


    public MainMenuPanel(MenuView menuView, MenuController menuController) {

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("BG_jeep.png"));
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.X_AXIS));
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(120, 0, 10, 0)); // Adds spacing from top

        //Stylized title
        JLabel titleLabel = new JLabel("Java Jeeps Coffee Truck Simulator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(true);

        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 4),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Force label to hug its text
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setMaximumSize(titleLabel.getPreferredSize());

        // Add label centered in wrapper
        titleWrapper.add(Box.createHorizontalGlue());
        titleWrapper.add(titleLabel);
        titleWrapper.add(Box.createHorizontalGlue());

        backgroundPanel.add(titleWrapper, BorderLayout.NORTH);

        // Panel to center the button panel
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        // Actual button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton createTruckButton = createButton("Create Truck");
        JButton removeTruckButton = createButton("Remove Truck");
        JButton simulateTruckButton = createButton("Simulate Truck");
        JButton dashboardButton = createButton("View Dashboard");
        JButton exitButton = createButton("Exit Program");

        buttonPanel.add(createTruckButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(removeTruckButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(simulateTruckButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(dashboardButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        createTruckButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            menuView.showPanel("CREATE_TRUCK");
        });

        removeTruckButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            menuController.showRemoveTruckPanel();
        });

        simulateTruckButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            menuController.showSimulateTruckPanel();
        });

        dashboardButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            menuView.showPanel("DASHBOARD");
        });

        exitButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            // Add a slight delay before exiting so the sound plays
            Timer timer = new Timer(100, evt -> System.exit(0));
            timer.setRepeats(false);
            timer.start();
        });

        centerWrapper.add(buttonPanel); // Centered in GridBagLayout
        backgroundPanel.add(centerWrapper, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }
}

