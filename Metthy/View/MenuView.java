package Metthy.View;

import Metthy.Controller.MainController;
import Metthy.Model.CoffeeTruck;

import javax.swing.*;
import java.awt.*;

public class MenuView{

    private MainController mainController;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private MainMenuPanel mainMenuPanel;
    private CreateTruckPanel createTruckPanel;
    private TruckLoadoutPanel truckLoadoutPanel;
    private RemoveTruckPanel removeTruckPanel;
    private SimulateTruckPanel simulateTruckPanel;
    private PrepareDrinkPanel prepareDrinkPanel;
    private TruckInfoPanel truckInfoPanel;
    private TruckMaintenancePanel truckMaintenancePanel;
    private DashboardPanel dashboardPanel;

    public MenuView(MainController mainController){

        super();
        this.mainController = mainController;

        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Initialize panels
        mainMenuPanel = new MainMenuPanel(mainController);
        createTruckPanel = new CreateTruckPanel(mainController);
        truckLoadoutPanel = new TruckLoadoutPanel(mainController);
        removeTruckPanel = new RemoveTruckPanel(mainController);
        simulateTruckPanel = new SimulateTruckPanel(mainController);
        prepareDrinkPanel = new PrepareDrinkPanel(mainController);
        truckInfoPanel = new TruckInfoPanel(mainController);
        truckMaintenancePanel = new TruckMaintenancePanel(mainController);
        dashboardPanel = new DashboardPanel(mainController);

        //Add to card layout
        cardPanel.add(mainMenuPanel, "MAIN_MENU");
        cardPanel.add(createTruckPanel, "CREATE_TRUCK");
        cardPanel.add(truckLoadoutPanel, "TRUCK_LOADOUT");
        cardPanel.add(removeTruckPanel, "REMOVE_TRUCK");
        cardPanel.add(simulateTruckPanel, "SIMULATE_TRUCK");
        cardPanel.add(prepareDrinkPanel, "PREPARE_DRINK");
        cardPanel.add(truckInfoPanel, "TRUCK_INFO");
        cardPanel.add(truckMaintenancePanel, "TRUCK_MAINTENANCE");
        cardPanel.add(dashboardPanel, "DASHBOARD");

        cardPanel.setVisible(true);

        frame.add(cardPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize
        frame.setVisible(true);
    }

    //PANEL DISPLAYERS

    public void showPanel(String panelName){

        cardLayout.show(cardPanel, panelName);
    }

    public void showMainMenu(){

        showPanel("MAIN_MENU");
    }

    public void showTruckCreationPanel(){

        showPanel("CREATE_TRUCK");
    }

    public void showTruckLoadoutPanel(CoffeeTruck truck, Runnable backAction, boolean fromTruckCreation) {

        truckLoadoutPanel.startLoadout(truck, backAction, fromTruckCreation);
        showPanel("TRUCK_LOADOUT");
    }

    public void showRemoveTruckPanel(){

        removeTruckPanel.start();
        showPanel("REMOVE_TRUCK");
    }

    /**
     * Displays the truck simulation menu for a selected coffee truck.
     * <p>Allows user to:</p>
     * <ul>
     *   <li>Prepare coffee drinks using the truck's inventory</li>
     *   <li>View the selected truck's information</li>
     *   <li>Enter the truck maintenance submenu</li>
     *   <li>Exit to the main menu</li>
     * </ul>
     */
    public void showTruckSimulationMenu(){

        simulateTruckPanel.startSimulation();
        showPanel("SIMULATE_TRUCK");
    }

    public void showPrepareDrinkPanel(CoffeeTruck truck){

        prepareDrinkPanel.startPreparation(truck);
        showPanel("PREPARE_DRINK");
    }

    public void showTruckInformationPanel(CoffeeTruck truck){

        truckInfoPanel.displayTruckInfo(truck);
        showPanel("TRUCK_INFO");
    }

    /**
     * Displays the truck maintenance menu for a selected coffee truck.
     * <p>Allows the user to:</p>
     * <ul>
     *   <li>Restock bins (either all or individually)</li>
     *   <li>Modify storage bin contents (either all or individually)</li>
     *   <li>Empty bins (either all or individually)</li>
     *   <li>Edit the truck's name or location</li>
     *   <li>Edit global drink ingredient prices</li>
     * </ul>
     */
    public void showTruckMaintenancePanel(CoffeeTruck truck){

        truckMaintenancePanel.startMaintenance(truck);
        showPanel("TRUCK_MAINTENANCE");
    }

    public void showDashboardPanel(){

        dashboardPanel.displayDashboard();
        showPanel("DASHBOARD");
    }
}
