package Metthy.Controller;

import Metthy.DrinkManager;
import Metthy.Driver;
import Metthy.Model.Model;
import Metthy.RegularCoffeeTruck;
import Metthy.SpecialCoffeeTruck;
import Metthy.View.View;

import java.util.ArrayList;

public class Controller {

    private final Model model;
    private final View view;
    private boolean running;
    public ArrayList<RegularCoffeeTruck> trucks;

    public Controller(Model model, View view){

        this.model = model;
        this.view = view;
    }

    public void truckCreation(){

        int truckType;
        String name, location;
        String setLoadout, repeat;

        do{
            do{
                name = view.enterUniqueName();
            } while(model.checkName(name));

            do{
                location = view.enterUniqueLocation();
            } while(model.checkLocation(location));

            do{
                truckType = view.enterTruckType();
            } while(model.checkTruckType(truckType));

            do{
                setLoadout = view.setLoadoutPrompt();
            } while(model.checkYesOrNo(setLoadout));

            model.createTruck(name, location, truckType, setLoadout);

            repeat = view.repeatPrompt();

        } while(repeat.equalsIgnoreCase("yes"));
    }

    public void truckRemoval(){

    }

    public void viewDashboard(){

    }

    public void mainMenu(int option) {

        switch (option) {

            case 1:
                truckCreation();
                break;
            case 2:
                manager.removeTruck();
                break;
            case 3:
                manager.simulateMenu();
                break;
            case 4:
                manager.displayDashboard();
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
