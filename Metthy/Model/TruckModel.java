package Metthy.Model;

import Metthy.DrinkManager;

import java.util.ArrayList;

public class TruckModel extends Model{

    public ArrayList<RegularCoffeeTruck> trucks;
    public DrinkManager drinkManager;

    public TruckModel(){

        this.trucks = new ArrayList<>();
    }

    /**
     * Checks if the given truck name is already taken.
     *
     * @param name The name to check.
     * @return true if the name is taken; false if it's available.
     */
    public boolean checkName(String name) {

        int i;
        boolean flag = false;

        name = name.trim(); //Trim white spaces

        for(i = 0; i < trucks.size() && !flag; i++){

            RegularCoffeeTruck truck = trucks.get(i);

            if(name.equalsIgnoreCase(truck.getName()))
                flag = true; //Set flag to true if the name is already taken
        }

        return flag; //Return true if name is taken, false otherwise
    }

    /**
     * Checks if the given truck location is already taken.
     *
     * @param location The location to check.
     * @return true if the location is taken; false if it's available.
     */
    public boolean checkLocation(String location) {

        int i;
        boolean flag = false;

        location = location.trim();

        for(i = 0; i < trucks.size() && !flag; i++){

            RegularCoffeeTruck truck = trucks.get(i);

            if (location.equalsIgnoreCase(truck.getLocation()))
                flag = true; //Set flag to true if the location is already taken
        }

        return flag; //Return true if location is taken, false otherwise
    }

    public boolean checkTruckType(int choice){

        boolean valid = true;

        if((choice != 1 && choice != 2)){

            System.out.println("Invalid choice. Please try again");
            valid = false;
        }

        return valid;
    }

    public void setLoadout(String set, RegularCoffeeTruck truck){

        if(set.equalsIgnoreCase("yes")){

            if(truck instanceof SpecialCoffeeTruck)
                ((SpecialCoffeeTruck) truck).setSpecialLoadout(); //Set up special truck storage bins

            else
                truck.setLoadout(); //Set up regular truck storage bins
        }
    }

    public void createTruck(String name, String location, int truckType, String set){

        RegularCoffeeTruck truck;

        //Create a Regular Coffee Truck
        if(truckType == 1)
            truck = new RegularCoffeeTruck(name, location, drinkManager);

            //Create a Special Coffee Truck
        else
            truck = new SpecialCoffeeTruck(name, location, drinkManager);

        //Add new truck to arraylist of trucks
        trucks.add(truck);

        setLoadout(set, truck);
    }

    /**
     * Displays a list of available trucks and allows the user to select one.
     *
     * @return The index of the selected truck in the list; -1 if no trucks are available.
     */
    public boolean checkTruckIndex(int truckIndex, ArrayList<RegularCoffeeTruck> trucks){

        boolean valid = false;

        if(truckIndex < 0 || truckIndex > trucks.size())
            valid = true;

        return valid;
    }

    public void removeTruck(int truckIndex){

        trucks.remove(truckIndex);
    }

    public ArrayList<RegularCoffeeTruck> getTrucks(){

        return trucks;
    }

}
