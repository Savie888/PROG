package Metthy.Controller;

import Metthy.Model.RegularCoffeeTruck;
import Metthy.Model.TruckModel;
import Metthy.View.MenuView;

import java.util.ArrayList;

public class MenuController extends Controller{

    private final MenuView menuView;
    private final TruckController truckController;
    private boolean running;

    public MenuController(){

        menuView = new MenuView();
        this.truckController = new TruckController(menuView);
    }

    public void mainMenu(int option){

        switch (option) {

            case 1:
                truckController.truckCreation();
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


    public void coffeeMenu(){

        int option;

        running = true;

        while(running){

            menuView.displayCoffeeMenu();
            option = menuView.getCoffeeMenuInput();

            switch (option){

                case 1:
                    //Prepare Drink
                    break;
                case 2:
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
            }
        }


    }

    @Override
    public void start() {

        int option;

        running = true;

        while(running){

            menuView.displayMainMenu();
            option = menuView.getMenuSelection();
            mainMenu(option);
        }
    }
}
