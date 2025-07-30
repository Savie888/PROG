package Metthy.Model;

import Metthy.Controller.MainController;

import java.util.ArrayList;

/**
 * The TruckManager class is responsible for managing all coffee trucks in the simulation.
 * It provides utilities for truck creation, ingredient setup, name/location validation, and truck counting.
 */
public class TruckManager{

    /** List of all coffee trucks managed by the system. */
    private ArrayList<CoffeeTruck> trucks;

    /** List of available ingredients used for loading into trucks. */
    private final ArrayList<BinContent> ingredientList;

    /** The MainController. */
    private final MainController mainController;


    public TruckManager(MainController mainController){

        this.trucks = new ArrayList<>();
        ingredientList = new ArrayList<>();
        this.mainController = mainController;

        setIngredients();
    }

    /** Initializes the ingredients to be used. */
    private void setIngredients(){

        ingredientList.add(new SmallCup());
        ingredientList.add(new MediumCup());
        ingredientList.add(new LargeCup());
        ingredientList.add(new CoffeeBean());
        ingredientList.add(new Milk());
        ingredientList.add(new Water());
    }

    /**
     * Checks if the given truck name is already taken.
     *
     * @param name The name to check.
     * @return true if the name is unique; false otherwise.
     */
    public boolean checkTruckName(String name) {

        int i;
        boolean flag = true;

        name = name.trim(); //Trim white spaces

        for(i = 0; i < trucks.size() && flag; i++){

            CoffeeTruck truck = trucks.get(i);

            if(name.equalsIgnoreCase(truck.getName()))
                flag = false; //Set flag to false if the name is already taken
        }

        return flag; //Return true if name is unique, true otherwise
    }

    /**
     * Checks if the given truck location is already taken.
     *
     * @param location The location to check.
     * @return true if the location is unique; false otherwise.
     */
    public boolean checkTruckLocation(String location) {

        int i;
        boolean flag = true;

        location = location.trim();

        for(i = 0; i < trucks.size() && flag; i++){

            CoffeeTruck truck = trucks.get(i);

            if (location.equalsIgnoreCase(truck.getLocation()))
                flag = false; //Set flag to false if the location is already taken
        }

        return flag; //Return true if location is unique, false otherwise
    }

    /**
     * Checks if the bin number is within the valid range (1 to 10).
     *
     * @param binNumber the bin number to validate
     * @return true if bin number is valid, false otherwise
     */
    public boolean checkBinNumber(int binNumber){

        return binNumber > 0 && binNumber <= 10;
    }

    /**
     * Searches for an ingredient in the list by name and returns a clone of it.
     *
     * @param name the name of the ingredient to search for
     * @return a cloned copy of the ingredient if found, null otherwise
     */
    public BinContent getIngredientFromName(String name){

        int i;
        BinContent content;

        for(i = 0; i < ingredientList.size(); i++){

            content = ingredientList.get(i);

            if(content.getName().equalsIgnoreCase(name)){
                return content.clone();
            }
        }

        return null; // Not found
    }

    /** Add truck to the list of trucks. */
    public void addTruck(CoffeeTruck truck){

        trucks.add(truck);
    }

    /**
     * Creates a new coffee truck of the specified type and adds it to the system.
     *
     * @param name            truck name
     * @param location        truck location
     * @param truckType       1 for regular, otherwise special
     * @param drinkManager    reference to the DrinkManager
     * @return the created CoffeeTruck
     */
    public CoffeeTruck createTruck(String name, String location, int truckType, DrinkManager drinkManager){

        CoffeeTruck truck;

        //Create a Regular Coffee Truck
        if(truckType == 1)
            truck = new RegularCoffeeTruck(name, location, mainController, drinkManager);

        //Create a Special Coffee Truck
        else
            truck = new SpecialCoffeeTruck(name, location, mainController, drinkManager);

        addTruck(truck); //Add to list

        return truck;
    }

    /** Remove truck from the list of trucks. */
    public void removeTruck(CoffeeTruck truck){

        trucks.remove(truck);
    }

    /**
     * Returns the number of regular coffee trucks.
     *
     * @return the count of RegularCoffeeTruck instances
     */
    public int getRegularTruckCount(){

        int i, count = 0;

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);

            if(truck instanceof RegularCoffeeTruck)
                count++;
        }

        return count;
    }

    /**
     * Returns the number of special coffee trucks.
     *
     * @return the count of SpecialCoffeeTruck instances
     */
    public int getSpecialTruckCount(){

        int i, count = 0;

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);

            if(truck instanceof SpecialCoffeeTruck)
                count++;
        }

        return count;
    }

    /** Returns the arraylist of trucks*/
    public ArrayList<CoffeeTruck> getTrucks(){

        return trucks;
    }

    /** Returns the ingredient list*/
    public ArrayList<BinContent> getIngredientList() {

        return ingredientList;
    }
}
