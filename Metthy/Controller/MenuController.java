package Metthy.Controller;

import Metthy.View.MenuView;
import Metthy.View.TruckView;

public class MenuController{

    private final MenuView menuView;
    private final TruckView truckView;
    private final TruckController truckController;
    private boolean running;

    public MenuController(){

        this.menuView = new MenuView(this);
        this.truckController = new TruckController(menuView);
        this.truckView = truckController.getTruckView();
    }

    public void showSimulateTruckPanel(){

        truckView.startTruckSimulation();
        menuView.showPanel("SIMULATE_TRUCK");
    }

    public void mainMenu(int option){

        switch (option) {

            case 1:
                //truckController.truckCreation();
                break;
            case 2:
                truckController.truckRemoval();
                break;
            case 3:
                truckController.simulateMenu();
                break;
            case 4:
                truckController.displayDashboard();
                break;
            case 5:
                running = false;
                System.out.println("Exiting program. Goodbye!");
                break;
            default:
                System.out.println("Invalid option. Please select again.");
        }

        System.out.println();
    }

    public void start() {

        int option;

        menuView.displayWelcomeMessage();

        running = true;

        while(running){

            menuView.displayMainMenu();
            option = menuView.getMenuSelection();
            mainMenu(option);
        }
    }
}
