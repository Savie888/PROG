package Metthy.Model;

import Metthy.Controller.DrinkController;

import java.util.ArrayList;

public class TruckManager extends Model{

    public ArrayList<CoffeeTruck> trucks;
    private final ArrayList<BinContent> ingredientList;

    public TruckManager(){

        this.trucks = new ArrayList<>();
        ingredientList = new ArrayList<>();
        setIngredients();
    }

    private void setIngredients() {

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

    public boolean checkTruckType(int choice){

        boolean valid = true;

        if((choice != 1 && choice != 2)){

            System.out.println("Invalid choice. Please try again");
            valid = false;
        }

        return valid;
    }

    // move above to truck view??

    public BinContent getIngredientFromOption(int option) {

        return ingredientList.get(option - 1);
    }

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

    public void addTruck(CoffeeTruck truck){

        trucks.add(truck);
    }

    public CoffeeTruck createTruck(String name, String location, int truckType, DrinkController drinkController){

        CoffeeTruck truck;

        //Create a Regular Coffee Truck
        if(truckType == 1)
            truck = new RegularCoffeeTruck(name, location, this, drinkController);

        //Create a Special Coffee Truck
        else
            truck = new SpecialCoffeeTruck(name, location, this, drinkController);

        addTruck(truck);

        return truck;
    }

    public void removeTruck(int truckIndex){

        trucks.remove(truckIndex);
    }

    public int getRegularTruckCount(){

        int i, count = 0;

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);

            if(truck instanceof RegularCoffeeTruck)
                count++;
        }

        return count;
    }

    public int getSpecialTruckCount(){

        int i, count = 0;

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);

            if(truck instanceof SpecialCoffeeTruck)
                count++;
        }

        return count;
    }

    public ArrayList<CoffeeTruck> getTrucks(){

        return trucks;
    }

}
