package Metthy.View;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel{

    public MainMenuPanel(MenuView menuView) {

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("Red_City_BG.jpg"));
        Image image = backgroundImage.getImage();

        Image scaledImage = image.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                Image.SCALE_SMOOTH);

        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundPanel = new JLabel(scaledBackgroundImage);
        backgroundPanel.setLayout(new BorderLayout());

        //Setup Title
        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.X_AXIS));
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(120, 0, 10, 0)); // Adds spacing from top

        // Stylized title
        JLabel titleLabel = new JLabel("Java Jeeps Coffee Truck Simulator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(Color.BLACK); //Persona 5 Crossroads bar vibe
        titleLabel.setOpaque(true);

        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 4),
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // top, left, bottom, right padding
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

        JButton createTruckButton = new JButton("Create Truck");
        JButton simulateTruckButton = new JButton("Simulate Truck");
        JButton dashboardButton = new JButton("Dashboard");
        JButton exitButton = new JButton("Exit");

        createTruckButton.addActionListener(e -> {

            SoundPlayer.playSound("select_sound_effect.wav");
            menuView.showPanel("CREATE_TRUCK");
        });

        simulateTruckButton.addActionListener(e -> {
            SoundPlayer.playSound("select_sound_effect.wav");
            menuView.showPanel("SIMULATE_TRUCK");
        });

        dashboardButton.addActionListener(e -> {
            SoundPlayer.playSound("select_sound_effect.wav");
            menuView.showPanel("DASHBOARD");
        });

        exitButton.addActionListener(e -> {
            SoundPlayer.playSound("select_sound_effect.wav");
            // Add a slight delay before exiting so the sound plays
            Timer timer = new Timer(300, evt -> System.exit(0));
            timer.setRepeats(false);
            timer.start();
        });

        // Style and center align
        JButton[] buttons = { createTruckButton, simulateTruckButton, dashboardButton, exitButton };
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            Dimension originalSize = new Dimension(200, 50);
            btn.setMaximumSize(originalSize);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
            btn.setFont(new Font("SansSerif", Font.BOLD, 18));

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

        Dimension hoverSize = new Dimension(210, 70);
        Timer[] currentTimer = {null};

        // Set initial size
        btn.setPreferredSize(ogSize);
        btn.setMaximumSize(ogSize);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(btn, ogSize, hoverSize, 5, currentTimer);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startAnimation(btn, hoverSize, ogSize, 5, currentTimer);
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
