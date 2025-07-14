package Metthy.Controller;

import Metthy.Model.Model;
import Metthy.Model.RegularCoffeeTruck;
import Metthy.View.View;

import java.util.ArrayList;

public class Controller {

    private final Model model;
    private final View view;
    private boolean running;

    public Controller(Model model, View view){

        this.model = model;
        this.view = view;
    }


    public void truckCreation(){

        int truckType;
        String name, location, setLoadout, repeat;

        do{
            do{
                name = view.enterUniqueName();
            } while(model.checkName(name));

            do{
                location = view.enterUniqueLocation();
            } while(model.checkLocation(location));

            do{
                truckType = view.enterTruckType();
            } while(!model.checkTruckType(truckType));

            do{
                setLoadout = view.setLoadoutPrompt();
            } while(!model.checkYesOrNo(setLoadout));

            model.createTruck(name, location, truckType, setLoadout);

            repeat = view.repeatPrompt();

        } while(repeat.equalsIgnoreCase("yes"));
    }

    /**
     * Removes a coffee truck selected by the user.
     */
    public void truckRemoval(){

        int truckNumber;
        ArrayList<RegularCoffeeTruck> trucks = model.getTrucks();

        do{
            truckNumber = view.selectTruck(trucks);
        } while(model.checkTruckIndex(truckNumber - 1, trucks));

        model.removeTruck(truckNumber - 1);
    }

    public void coffeeMenu(){

        int option;

        running = true;

        while(running){

            view.displayCoffeeMenu();
            option = view.getCoffeeMenuInput();

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

        int truckNumber, option;
        ArrayList<RegularCoffeeTruck> trucks = model.getTrucks();

        running = true;

        do{
            truckNumber = view.selectTruck(trucks);
        } while(model.checkTruckIndex(truckNumber - 1, trucks));

        RegularCoffeeTruck selectedTruck = trucks.get(truckNumber - 1);

        view.displaySimulationMenu();
        option = view.getSimulationMenuInput();

        while (running){

            switch (option){

                case 1:
                    coffeeMenu(); //Display Coffee Menu
                    break;
                case 2:
                    //selectedTruck.displayInfo(); //Display truck information
                    break;
                case 3:
                    //selectedTruck.truckMaintenanceMenu(); //Display truck maintenance menu
                    break;
                case 4:
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
            }
        }
    }

    public void displayDashboard(){

    }

    public void mainMenu(int option){

        switch (option) {

            case 1:
                truckCreation();
                break;
            case 2:
                truckRemoval();
                break;
            case 3:
                simulateMenu();
                break;
            case 4:
                displayDashboard();
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

    public void start(){

        int option;

        running = true;

        while (running) {

            view.displayMainMenu();
            option = view.getMenuSelection();
            mainMenu(option);
        }

    }
}
