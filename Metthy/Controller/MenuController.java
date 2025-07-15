package Metthy.Controller;

import Metthy.Model.RegularCoffeeTruck;
import Metthy.Model.TruckModel;
import Metthy.View.MenuView;

import java.util.ArrayList;

public class MenuController extends Controller{

    private final MenuView menuView;
    private final TruckModel truckModel;
    private final TruckController truckController;
    private boolean running;

    public MenuController(){

        menuView = new MenuView();
        truckModel = new TruckModel();
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
                simulateMenu();
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

    /**
     * Displays the truck simulation menu for performing actions on a selected coffee truck.
     * <p>Allows user to:</p>
     * <ul>
     *   <li>Prepare coffee drinks using the truck's inventory</li>
     *   <li>View the selected truck's information</li>
     *   <li>Enter the truck maintenance submenu</li>
     *   <li>Exit to the main menu</li>
     * </ul>
     */
    public void simulateMenu(){

        int truckNumber, option, choice;
        ArrayList<RegularCoffeeTruck> trucks = truckModel.getTrucks();

        running = true;

        do{
            truckNumber = truckController.selectTruck();
        } while(truckModel.checkTruckIndex(truckNumber - 1, trucks));

        RegularCoffeeTruck selectedTruck = trucks.get(truckNumber - 1);

        menuView.displaySimulationMenu();
        option = menuView.getSimulationMenuInput();

        while (running){

            switch (option){

                case 1:
                    coffeeMenu(); //Display Coffee Menu
                    break;
                case 2:
                    truckController.displayTruckInfo(selectedTruck); //Display truck information
                    break;
                case 3:
                    menuView.dispayTruckMaintenanceMenu();
                    choice = menuView.getTruckMaintenanceMenuInput();
                    truckController.truckMaintenanceMenu(selectedTruck, choice); //Display truck maintenance menu
                    break;
                case 4:
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
            }
        }
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

        while (running) {

            menuView.displayMainMenu();
            option = menuView.getMenuSelection();
            mainMenu(option);
        }
    }
}
