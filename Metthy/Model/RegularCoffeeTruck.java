package Metthy.Model;

import Metthy.Controller.MainController;

/**
 * This class represents a regular coffee truck
 */
public class RegularCoffeeTruck extends CoffeeTruck{

    /**
     * Constructs a regular coffee truck with the given name and location.
     * Initializes 8 storage bins and an empty sales log.
     * @param name         the name of the coffee truck
     * @param location     the initial location of the truck
     */
    public RegularCoffeeTruck(String name, String location, MainController mainController, DrinkManager drinkManager){

        super(name, location, mainController, drinkManager);

        int i;

        //Create the 8 storage bins
        for(i = 0; i <= 7; i++)
            bins.add(new StorageBin(i + 1));
    }
}