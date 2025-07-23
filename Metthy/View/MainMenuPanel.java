package Metthy.View;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel{

    private Image backgroundImage;
    private MenuView menuView;

    public MainMenuPanel(MenuView menuView){

        this.menuView = menuView;

        setLayout(new GridBagLayout());

        backgroundImage = new ImageIcon("BG_jeep.png").getImage(); // replace with your path

        // Centered Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(45, 34, 59));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

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
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
        for (JButton button : new JButton[]{createTruckButton, simulateTruckButton, dashboardButton, exitButton}) {
            button.setFont(buttonFont);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 40));
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(15)); // spacing
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(buttonPanel, gbc);
    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        // Draw the background scaled to the panel size
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
