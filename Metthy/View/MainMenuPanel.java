package Metthy.View;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel{

    public MainMenuPanel(MenuView menuView) {

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("BG_jeep.png"));
        JLabel backgroundPanel = new JLabel(backgroundImage);
        backgroundPanel.setLayout(new BorderLayout());

        // Stylized title
        JLabel titleLabel = new JLabel("Java Jeeps Coffee Truck Simulator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.RED);
        titleLabel.setOpaque(false);
        titleLabel.setBackground(new Color(28, 20, 34)); // Persona 5 Crossroads bar vibe

        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel to center the button panel
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        // Actual button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton createTruckButton = new JButton("Create Truck");
        JButton simulateTruckButton = new JButton("Simulate Truck");
        JButton dashboardButton = new JButton("Dashboard");
        JButton exitButton = new JButton("Exit");

        createTruckButton.addActionListener(e -> menuView.showPanel("CREATE_TRUCK"));
        simulateTruckButton.addActionListener(e -> menuView.showPanel("SIMULATE_TRUCK"));
        dashboardButton.addActionListener(e -> menuView.showPanel("DASHBOARD"));
        exitButton.addActionListener(e -> System.exit(0));

        // Style and center align
        JButton[] buttons = { createTruckButton, simulateTruckButton, dashboardButton, exitButton };
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 40));
            buttonPanel.add(btn);
            buttonPanel.add(Box.createVerticalStrut(15));
        }

        centerWrapper.add(buttonPanel); // Centered in GridBagLayout
        backgroundPanel.add(centerWrapper, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }
        /*
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent so background shows through

        //buttonPanel.setBackground(new Color(45, 34, 59));
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JButton createTruckButton = new JButton("Create Truck");
        JButton simulateTruckButton = new JButton("Simulate Truck");
        JButton dashboardButton = new JButton("Dashboard");
        JButton exitButton = new JButton("Exit");

        // Example button action: switch to CreateTruck panel
        createTruckButton.addActionListener(e -> menuView.showPanel("CREATE_TRUCK"));
        simulateTruckButton.addActionListener(e -> menuView.showPanel("SIMULATE_TRUCK"));
        dashboardButton.addActionListener(e -> menuView.showPanel("DASHBOARD"));
        exitButton.addActionListener(e -> System.exit(0));

        // Style buttons
        for (JButton button : new JButton[]{createTruckButton, simulateTruckButton, dashboardButton, exitButton}) {
            button.setFont(new Font("SansSerif", Font.PLAIN, 20));
            button.setFocusPainted(false);
            button.setBackground(new Color(80, 60, 100));
            button.setForeground(Color.WHITE);
        }

        buttonPanel.add(createTruckButton);
        buttonPanel.add(simulateTruckButton);
        buttonPanel.add(dashboardButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw image scaled to panel size
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
*/
}
