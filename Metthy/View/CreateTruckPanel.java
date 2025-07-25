package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.CoffeeTruck;

import javax.swing.*;
import java.awt.*;

public class CreateTruckPanel extends BasePanel {

    private final TruckController truckController;
    private final TruckView truckView;
    private final MenuView menuView;
    private BackgroundPanel backgroundPanel;
    private JPanel titleWrapper, formPanel, formBackgroundPanel;
    private JLabel titleLabel, errorLabel, nameLabel, locationLabel, truckTypeLabel;
    private JTextField nameField, locationField;
    private JComboBox<String> typeBox;
    private JButton createButton;
    private CoffeeTruck truck;

    public CreateTruckPanel(TruckController controller, MenuView menuView, TruckView truckView){

        this.truckController = controller;
        this.menuView = menuView;
        this.truckView = truckView;

        //Setup Background Image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("regular.png"));
        Image image = backgroundImage.getImage();
        backgroundPanel = new BackgroundPanel(image);

        //Setup Title
        titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.X_AXIS));
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0)); // Adds spacing from top

        //Stylized title
        titleLabel = new JLabel("Truck Creation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setMaximumSize(new Dimension(5, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(false);

        //Force label to hug its text
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setMaximumSize(titleLabel.getPreferredSize());

        //Add label centered in wrapper
        titleWrapper.add(Box.createHorizontalGlue());
        titleWrapper.add(titleLabel);
        titleWrapper.add(Box.createHorizontalGlue());

        backgroundPanel.add(titleWrapper, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);

        //Form Background Panel
        formBackgroundPanel = new TranslucentPanel();
        formBackgroundPanel.setLayout(new BorderLayout());
        formBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(0, 700, 20, 700));

        //Form Panel
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground((new Color(123, 79, 43)));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 50, 60));

        //Set up layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        //Row 0: Truck Name Label
        gbc.gridy = 0;
        nameLabel = new JLabel("Enter truck name:");
        formPanel.add(nameLabel, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        //Row 1: Truck Name Field
        gbc.gridy++;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        //Row 2: Truck Location Label
        gbc.gridy++;
        locationLabel = new JLabel("Enter truck location:");
        formPanel.add(locationLabel, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        //Row 3: Truck Location Field
        gbc.gridy++;
        locationField = new JTextField(20);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(locationField, gbc);

        //Row 4: Truck Type ComboBox
        gbc.gridy++;
        String[] truckTypes = {"1. Regular", "2. Special"};
        truckTypeLabel = new JLabel("Select truck type:");
        formPanel.add(truckTypeLabel, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        gbc.gridy++;
        typeBox = new JComboBox<>(truckTypes);
        formPanel.add(typeBox, gbc);
        formPanel.add(Box.createVerticalStrut(5));

        //Row 5: Create Truck Button
        gbc.gridy++;
        createButton = new JButton("Create Truck");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(createButton, gbc);

        createButton.addActionListener(e -> {

            playSound("select_sound_effect.wav"); //Play sound effect

            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            int type = Integer.parseInt(((String) typeBox.getSelectedItem()).substring(0, 1));

            // Validate name
            if (name.isEmpty()) {
                errorLabel.setText("Name can't be empty.");
                return;
            }
            if (!controller.isTruckNameUnique(name)) {
                errorLabel.setText("Name already used.");
                return;
            }

            // Validate location
            if (location.isEmpty()) {
                errorLabel.setText("Location can't be empty.");
                return;
            }
            if (!controller.isTruckLocationUnique(location)) {
                errorLabel.setText("Location already used.");
                return;
            }

            // If all is good, clear errors and continue
            errorLabel.setText(" ");

            truck = controller.truckCreation(name, location, type); //Create truck

            //Set loadout option
            int setLoadout = setLoadoutOption();

            if (setLoadout == JOptionPane.YES_OPTION) {

                int setDefault = setDefaultLoadoutOption();

                if(setDefault == JOptionPane.YES_OPTION){
                    truckController.setDefaultTruckLoadout(truck);
                    repeat();
                }

                else{
                    resetFields();
                    truckView.showTruckLoadoutPanel(truck);
                }
            }

            else
                repeat();

        });

        //Row 6: Error Label
        gbc.gridy++;
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        formPanel.add(errorLabel, gbc);

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false); // match your translucent look

        JButton backButton = createButton("Exit to Main Menu");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            playSound("select_sound_effect.wav");
            menuView.showPanel("MAIN_MENU");
        });

        //Add formPanel into the background container
        formBackgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(formBackgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); //Add south panel
    }

    public int setLoadoutOption(){

        int response = JOptionPane.showConfirmDialog(
                this,
                "Set up storage bins?",
                "Setup Truck Loadout",
                JOptionPane.YES_NO_OPTION
        );

        return response;
    }

    public int setDefaultLoadoutOption(){

        int set = JOptionPane.showConfirmDialog(
                this,
                "set to default loadout",
                "Set Default Truck Loadout",
                JOptionPane.YES_NO_OPTION
        );

        return set;
    }

    private void resetFields(){

        nameField.setText("");
        locationField.setText("");
        typeBox.setSelectedIndex(0);
        errorLabel.setText(" ");
    }

    public void repeat(){

        int choice = JOptionPane.showOptionDialog(
                this,
                "Truck created successfully!\nCreate another one?",
                "Success",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] { "Yes, create another", "Back to Menu" },
                "Yes, create another"
        );

        if(choice == 0)
            resetFields();
        else
            menuView.showPanel("MAIN_MENU");
    }

    public void truckLoadout(CoffeeTruck truck){

        //Set loadout option
        int response = setLoadoutOption();

        if (response == JOptionPane.YES_OPTION) {

            int set = setDefaultLoadoutOption();

            if(set == JOptionPane.YES_OPTION)
                truckController.setDefaultTruckLoadout(truck);

            else{

                truckView.showTruckLoadoutPanel(truck);
            }
        }

    }


}
