package Metthy.Model;

import Metthy.DrinkManager;
import Metthy.StorageBin;

import java.util.ArrayList;
import java.util.Scanner;

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
    public RegularCoffeeTruck(String name, String location, TruckManager truckManager){

        super(name, location, truckManager);

        int i;

        //Create the 8 storage bins
        for(i = 0; i <= 7; i++)
            bins.add(new StorageBin(i + 1));
    }



}