package Metthy.View;

import Metthy.Controller.MenuController;

import javax.swing.*;
import java.awt.*;

public class MenuView extends View{

    private MenuController menuController;
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private MainMenuPanel mainMenuPanel;
    //private RemoveTruckPanel removeTruckPanel;
    //private SimulateTruckPanel simulateTruckPanel;
    //private DashboardPanel dashboardPanel;

    public MenuView(MenuController menuController){

        super();
        this.menuController = menuController;

        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize panels
        mainMenuPanel = new MainMenuPanel(this, menuController);
        //simulateTruckPanel = new SimulateTruckPanel(this);
        //dashboardPanel = new DashboardPanel(this);

        // Add panels to cardPanel
        cardPanel.add(mainMenuPanel, "MAIN_MENU");
        //cardPanel.add(simulateTruckPanel, "SIMULATE_TRUCK");
        //cardPanel.add(dashboardPanel, "DASHBOARD");

        frame.add(cardPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize
        frame.setVisible(true);
    }

    public void registerTruckPanels(TruckView truckView){

        cardPanel.add(truckView.getCreateTruckPanel(), "CREATE_TRUCK");
        cardPanel.add(truckView.getTruckLoadoutPanel(), "TRUCK_LOADOUT");
        cardPanel.add(truckView.getSimulateTruckPanel(), "SIMULATE_TRUCK");
        //cardPanel.add(truckView.getDashboardPanel(), "DASHBOARD");
    }

    public void showPanel(String panelName){

        cardLayout.show(cardPanel, panelName);
    }

    public void displayWelcomeMessage(){

        System.out.println("Welcome to Java Jeeps Coffee Truck Simulator");
    }

    /**
     * Displays the main menu and handles user interaction.
     * <p>
     * The menu allows the user to:
     * <ul>
     *     <li>Create a coffee truck</li>
     *     <li>Simulate business actions like making drinks</li>
     *     <li>View sales and inventory dashboard</li>
     *     <li>Exit the program</li>
     * </ul>
     * </p>
     * The method runs in a loop until the user chooses to exit.
     */
    public void displayMainMenu() {

        System.out.print("\n=== Main Menu ===\n");
        System.out.println("1 - Create a Coffee Truck");
        System.out.println("2 - Decommission a Coffee Truck");
        System.out.println("3 - Simulate Coffee Truck Business");
        System.out.println("4 - View Dashboard");
        System.out.println("5 - Exit\n");
    }

    public int getMenuSelection(){

        int option;

        System.out.println("Select an option: ");
        option = scanner.nextInt();
        scanner.nextLine(); //Clear excess line

        return option;
    }

    public int getSimulationMenuInput(){

        int option;

        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    public void displaySimulationMenu(){

        System.out.println("\n=== Simulation Menu ===");
        System.out.println("1 - Prepare Coffee Drinks");
        System.out.println("2 - View Truck Information");
        System.out.println("3 - Truck Maintenance");
        System.out.println("4 - Exit to Main Menu");
        System.out.println("Select an Option: ");
    }

    public int getTruckMaintenanceMenuInput(){

        int option;

        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    /**
     * Displays the truck maintenance menu for a specific coffee truck.
     * <p>Allows the user to:</p>
     * <ul>
     *   <li>Restock bins (either all or individually)</li>
     *   <li>Modify storage bin contents (either all or individually)</li>
     *   <li>Empty bins (either all or individually)</li>
     *   <li>Edit the truck's name or location</li>
     *   <li>Edit global drink ingredient prices</li>
     * </ul>
     *
     */
    public void displayTruckMaintenanceMenu(){

        System.out.println("\n=== Truck Maintenance ===");
        System.out.println("1 - Restock Bins (only works on bins with an assigned item)");
        System.out.println("2 - Modify Storage Bin Contents");
        System.out.println("3 - Empty Storage Bins");
        System.out.println("4 - Edit Truck Name");
        System.out.println("5 - Edit Truck Location");
        System.out.println("6 - Edit Pricing (will affect pricing for all trucks)");
        System.out.println("7 - Exit Menu");
        System.out.println("Select an Option: ");
    }

    public int restockMenu(){

        int option;

        System.out.println("1 - Restock all bins");
        System.out.println("2 - Restock one bin");
        System.out.print("Select an option: ");
        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    public int modifyMenu(){

        int option;

        System.out.println("1 - Modify all bins");
        System.out.println("2 - Modify one bin");
        System.out.print("Select an option: ");
        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    public int emptyMenu(){

        int option;

        System.out.println("1 - Empty all bins");
        System.out.println("2 - Empty one bin");
        System.out.print("Select an option: ");
        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    public String enterNewName(){

        String name;

        System.out.print("Enter new name: ");
        name = scanner.nextLine();
        //scanner.nextLine(); //Absorb new line

        return name;
    }

    public String enterNewLocation(){

        String location;

        System.out.print("Enter new location: ");
        location = scanner.nextLine();
        //scanner.nextLine(); //Absorb new line

        return location;
    }

}
