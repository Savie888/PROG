package Metthy.View;

import Metthy.Controller.TruckController;

import javax.swing.*;
import java.awt.*;

public class CreateTruckPanel extends BasePanel {

    private final TruckController controller;
    private JLabel errorLabel;
    private JTextField nameField, locationField;
    private JComboBox<String> typeBox;
    private JButton createButton;
    private JPanel formPanel;

    public CreateTruckPanel(TruckController controller){

        this.controller = controller;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("BG_jeep.png"));
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

        //Stylized title
        JLabel titleLabel = new JLabel("Create trucks", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(true);

        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 4),
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

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);

        // === Form Panel ===
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false); // keep background image visible
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 400, 50, 400)); // adjust padding as needed

        setupNameField();

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(errorLabel);

        backgroundPanel.add(formPanel, BorderLayout.CENTER);
    }

    private void setupNameField(){

        // Name field
        nameField = new JTextField(20);
        nameField.setMaximumSize(new Dimension(400, 30));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(new JLabel("Enter unique truck name:"));
        formPanel.add(nameField);

        nameField.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                errorLabel.setText("Name can't be empty.");
                return;
            }
            if (!controller.isTruckNameUnique(name)) {
                errorLabel.setText("Name already used.");
                return;
            }

            errorLabel.setText(" ");
            setupLocationField();
        });
    }

    public void setupLocationField(){

        // Location field
        locationField = new JTextField(20);
        locationField.setMaximumSize(new Dimension(400, 30));
        locationField.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(new JLabel("Enter unique truck location:"));
        formPanel.add(locationField);
        formPanel.revalidate();
        formPanel.repaint();

        locationField.addActionListener(e -> {
            String location = locationField.getText().trim();
            if (location.isEmpty()) {
                errorLabel.setText("Location can't be empty.");
                return;
            }
            if (!controller.isTruckLocationUnique(location)) {
                errorLabel.setText("Location already used.");
                return;
            }

            errorLabel.setText(" ");
            setupTruckTypeSelector();
        });
    }

    public void setupTruckTypeSelector(){

        String[] options = { "1. Regular", "2. Special" };
        typeBox = new JComboBox<>(options);
        typeBox.setMaximumSize(new Dimension(400, 30));
        typeBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        createButton = new JButton("Create Truck");
        createButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(new JLabel("Select truck type:"));
        formPanel.add(typeBox);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createButton);
        formPanel.revalidate();
        formPanel.repaint();

        createButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            int type = Integer.parseInt(((String) typeBox.getSelectedItem()).substring(0, 1));

            controller.truckCreation(name, location, type);
        });
    }
}
