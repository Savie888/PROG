package Metthy.Controller;

import Metthy.View.MenuView;

public class MenuController{

    private final MenuView menuView;
    private final TruckController truckController;
    private boolean running;

    public MenuController(MenuView menuView){

        this.menuView = menuView;
        this.truckController = new TruckController(menuView);
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
