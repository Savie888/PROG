package Metthy.Model;

import Metthy.Controller.MainController;

import java.util.ArrayList;

/**
 * This class represents a special coffee truck
 */
public class SpecialCoffeeTruck extends CoffeeTruck {

    /**
     * Constructs a special coffee truck with the given name and location.
     * Initializes 10 storage bins and an empty sales log.
     *
     * @param name         the name of the coffee truck
     * @param location     the initial location of the truck
     */
    public SpecialCoffeeTruck(String name, String location, MainController mainController, DrinkManager drinkManager){

        //Call regular coffee truck constructor
        super(name, location, mainController, drinkManager);

        int i;

        //Create the 8 storage bins
        for(i = 0; i <= 7; i++)
            bins.add(new StorageBin(i + 1));

        //Create the 2 extra storage bins for syrups
        getBins().add(new StorageBin(9));
        getBins().add(new StorageBin(10));
    }

    /**
     * Records a drink sale to the truck's sales log.
     *
     * @param coffeeType   the type of coffee drink.
     * @param size         the size of the drink.
     * @param coffeeGrams  the grams of coffee beans used.
     * @param milk         the ounces of milk used.
     * @param water        the ounces of water used.
     * @param addOns       list of syrup or other optional add-ons.
     * @param extraShots   number of extra espresso shots.
     * @param price        the price of the drink.
     */
    public void recordSpecialSale(String coffeeType, String size, String brewType, double coffeeGrams,
                                  double milk, double water, ArrayList<BinContent> addOns, int extraShots, double price){

        int i;
        double extraCoffeeGrams;

        String drinkInfo;
        StringBuilder ingredients = new StringBuilder();
        StringBuilder addOnDetails = new StringBuilder();

        //Build base ingredient info string (without extra shots or add-ons)
        ingredients.append(String.format("%.2f g beans, %.2f oz milk, %.2f oz water", coffeeGrams, milk, water));

        //Build formatted drink info line
        drinkInfo = String.format("Drink: %s %s (%s)", size, coffeeType, brewType);

        //Include extra shot info if any
        if(extraShots > 0){
            extraCoffeeGrams = coffeeGrams * extraShots;
            ingredients.append(String.format(" + %d extra shot%s (%.2f g beans)", extraShots, (extraShots == 1 ? "" : "s"), extraCoffeeGrams));
        }

        //Build add-on details, if any
        if(!addOns.isEmpty()){
            addOnDetails.append("Add-Ons: ");

            for(i = 0; i < addOns.size(); i++){
                BinContent addOn = addOns.get(i);
                addOnDetails.append(String.format("%.1f oz %s", 1.0, addOn.getName()));

                if(i < addOns.size() - 1){
                    addOnDetails.append(", ");
                }
            }
        }

        //If no add-ons, display "None"
        String addOnText = addOnDetails.isEmpty() ? "No Add-Ons" : addOnDetails.toString();

        //Format and add to the sales log
        salesLog.add(String.format("%-30s | %-45s | %s | $%.2f", drinkInfo, ingredients, addOnText, price));
    }

}
