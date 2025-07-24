package Metthy.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel{

    public MainMenuPanel(MenuView menuView) {

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("BG_jeep.png"));
        JLabel backgroundPanel = new JLabel(backgroundImage);
        backgroundPanel.setLayout(new BorderLayout());

        // Stylized title
        JLabel titleLabel = new JLabel("Java Jeeps Coffee Truck Simulator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
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
            Dimension originalSize = new Dimension(200, 50);
            btn.setMaximumSize(originalSize);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(190, 0, 0)); // deep red, like Persona 5 palette
            btn.setForeground(Color.BLACK);

            addButtonHoverAnimation(btn, originalSize);

            buttonPanel.add(btn);
            buttonPanel.add(Box.createVerticalStrut(15));
        }

        centerWrapper.add(buttonPanel); // Centered in GridBagLayout
        backgroundPanel.add(centerWrapper, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    private void addButtonHoverAnimation(JButton btn, Dimension ogSize) {

        Dimension originalSize = ogSize;
        Dimension hoverSize = new Dimension(210, 70);
        Timer[] currentTimer = {null};

        // Set initial size
        btn.setPreferredSize(originalSize);
        btn.setMaximumSize(originalSize);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(btn, originalSize, hoverSize, 5, currentTimer);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startAnimation(btn, hoverSize, originalSize, 5, currentTimer);
            }
        });
    }

    private void startAnimation(JButton button, Dimension from, Dimension to, int steps, Timer[] currentTimer) {
        if (currentTimer[0] != null && currentTimer[0].isRunning()) {
            currentTimer[0].stop();
        }

        int deltaW = (to.width - from.width) / steps;
        int deltaH = (to.height - from.height) / steps;

        final int[] currentStep = {0};

        currentTimer[0] = new Timer(15, null);

        currentTimer[0].addActionListener(e -> {
            if (currentStep[0] >= steps) {
                button.setPreferredSize(to);
                button.setMaximumSize(to);
                button.revalidate();
                currentTimer[0].stop();
                return;
            }

            int newW = from.width + deltaW * (currentStep[0] + 1);
            int newH = from.height + deltaH * (currentStep[0] + 1);

            button.setPreferredSize(new Dimension(newW, newH));
            button.setMaximumSize(new Dimension(newW, newH));
            button.revalidate();

            currentStep[0]++;
        });

        currentTimer[0].start();
    }
}
