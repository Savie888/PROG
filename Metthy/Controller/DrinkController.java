package Metthy.Controller;

import Metthy.Model.*;
import Metthy.View.DrinkView;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Centralized manager for handling drink configurations, pricing, and ingredient calculations.
 *
 * <p>This class is responsible for:
 * <ul>
 *     <li>Initializing a standard menu of espresso-based drinks</li>
 *     <li>Accepting and storing ingredient prices (coffee beans, milk, water)</li>
 *     <li>Calculating ingredient quantities based on drink type and size</li>
 *     <li>Converting between fluid ounces and grams using brew ratios</li>
 *     <li>Displaying ingredient breakdowns for individual drinks</li>
 * </ul>
 *
 */
public class DrinkController {

    /**
     * Scanner object for reading user input from the console.
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * List of available drinks that can be prepared by the coffee trucks.
     */
    private final ArrayList<Drink> drinks;

    private final DrinkView drinkView;

    /**
     * Price per gram of coffee beans.
     */
    private double coffeeGramPrice;
    /**
     * Price per fluid ounce of milk.
     */
    private double milkOzPrice;
    /**
     * Price per fluid ounce of water.
     */
    private double waterOzPrice;
    /**
     * Price of a syrup add-on.
     */
    private double syrupOzPrice;

    private double extraShotPrice;

    /**
     * Constructs a new DrinkManager and sets up the drink menu
     */
    public DrinkController(){

        drinks = new ArrayList<>();
        drinkView = new DrinkView();

        initializeDrinkMenu(); //Set up the drink menu
    }

    /**
     * Prompts the user to input the unit prices for coffee ingredients (coffee beans, milk, and water).
     */
    public void setIngredientPrices(){

        do{
            coffeeGramPrice = drinkView.enterCoffeeBeanGramPrice();
        } while(coffeeGramPrice < 0);

        do{
            milkOzPrice = drinkView.enterMilkOzPrice();
        } while(milkOzPrice < 0);

        do{
            waterOzPrice = drinkView.enterWaterOzPrice();
        } while(waterOzPrice < 0);

        do{
            syrupOzPrice = drinkView.enterSyrupOzPrice();
        } while(syrupOzPrice < 0);

        do{
            extraShotPrice = drinkView.enterExtraShotPrice();
        } while(extraShotPrice < 0);
    }

    /**
     * Initializes the drink menu with all combinations of coffee types and sizes.
     */
    private void initializeDrinkMenu(){

        String[] types = {"Latte", "Cappuccino", "Americano"};
        String[] sizes = {"Small", "Medium", "Large"};
        int i, j;

        for(i = 0; i < types.length; i++){

            for(j = 0; j < sizes.length; j++){
                Drink drink = null;

                if(types[i].equals("Latte"))
                    drink = new Latte(sizes[j]);

                else if(types[i].equals("Cappuccino"))
                    drink = new Cappuccino(sizes[j]);

                else if(types[i].equals("Americano"))
                    drink = new Americano(sizes[j]);

                if (drink != null) {
                    drinks.add(drink);
                }
            }
        }
    }

    /**
     * Displays the drinks menu
     *
     */
    public void displayDrinksMenu(){

        int i, j, k;
        double price;
        double[] ingredients;

        String[] types = {"Americano", "Latte", "Cappuccino"};
        String[] sizes = {"Small", "Medium", "Large"};

        drinkView.drinkMenuHeader();

        for(i = 0; i < types.length; i++){

            String type = types[i];
            System.out.println(type + ":");

            for(j = 0; j < sizes.length; j++){

                String size = sizes[j];

                //Find the matching drink
                for(k = 0; k < drinks.size(); k++){

                    Drink drink = drinks.get(k);

                    if(drink.getType().equalsIgnoreCase(type) && drink.getSize().equalsIgnoreCase(size)){
                        ingredients = getBaseIngredients(type, size);
                        price = calculateCoffeeCost(type, ingredients, 18.0); //Calculate total coffee cost
                        drink.setPrice(price); //Set the coffee's price
                        System.out.printf("  %s - $%.2f\n", size, price);
                    }
                }
            }
        }
    }

    /**
     * Displays the main coffee menu for the truck, allowing the user to view
     * available drinks or prepare a new drink.
     *
     */
    public void prepareCoffeeMenu(CoffeeTruck truck){

        int option;

        boolean running = true;

        while(running){

            drinkView.displayCoffeeMenu();
            option = drinkView.getCoffeeMenuInput();

            switch (option){

                case 1:
                    displayDrinksMenu();
                    truck.prepareDrink(); //Prepare Drink
                    break;
                case 2:
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
            }
        }
    }

    /**
     * Calculates the total fluid ounces corresponding to the size of a drink.
     *
     * @param size the drink size (e.g., "Small", "Medium", "Large")
     * @return the size of the cup in fluid ounces, or 0 if the size is invalid
     */
    private double getCupSize(String size){

        double cupSize;

        switch(size){

            case "Small":
                cupSize = 8.0;
                break;
            case "Medium":
                cupSize = 12.0;
                break;
            case "Large":
                cupSize = 16.0;
                break;
            default:
                cupSize = 0;
                break;
        }
        return cupSize;
    }

    /**
     * Calculates the amount of each ingredient required to prepare a drink using the default brewing ratios.
     *
     * @param coffeeType the type of coffee
     * @param size       the size of the drink
     * @return a double array containing:
     *         [0] coffeeGrams  – amount of ground coffee needed (in grams)
     *         [1] milkOz       – amount of milk (in fluid ounces)
     *         [2] totalWaterOz – total water required including brew water (in fluid ounces)
     *         [3] cup          - number of cups to be used (always 1)
     */
    private double[] getBaseIngredients(String coffeeType, String size){

        double cupSize = getCupSize(size);
        double espressoVolumeOz = 0, milkOz = 0, waterOz = 0;
        double[] ingredients ={0, 0, 0, 1};

        switch(coffeeType){
            case "Americano" -> {
                espressoVolumeOz = cupSize * (1.0 / 3);
                waterOz = cupSize * (2.0 / 3);
            }
            case "Latte" -> {
                espressoVolumeOz = cupSize * (1.0 / 5);
                milkOz = cupSize * (4.0 / 5);
            }
            case "Cappuccino" -> {
                espressoVolumeOz = cupSize * (1.0 / 3);
                milkOz = cupSize * (2.0 / 3);
            }
        }

        ingredients[0] = espressoVolumeOz;
        ingredients[1] = milkOz;
        ingredients[2] = waterOz;

        return ingredients;
    }

    /**
     * Returns the amount of coffee beans (in grams) required for the given drink.
     *
     * @param ingredients the ingredients needed to brew the coffee drink.
     * @param ratio       the ratio of coffee to water used for espresso brewing.
     * @return the amount of coffee beans required, in grams.
     */
    public double getCoffeeBeanGrams(double[] ingredients, double ratio){

        double espressoVolumeOz = ingredients[0];

        //Convert espresso volume to brewed water mass in grams
        double brewedWaterGrams = espressoVolumeOz * 28.34952;

        //Coffee grams = water grams divided by ratio
        return brewedWaterGrams / ratio;
    }

    /**
     * Returns the amount of milk (in fluid ounces) required for the given drink.
     *
     * @param ingredients the ingredients needed to brew the coffee drink.
     * @return the amount of milk required, in fluid ounces.
     */
    public double getMilkOz(double[] ingredients){

        return ingredients[1];
    }

    /**
     * Returns the total amount of water (in fluid ounces) required for the given drink.
     *
     * @param coffeeType   the type of coffee.
     * @param ingredients  the ingredients needed to brew the coffee drink.
     * @param ratio        the ratio of coffee to water used for espresso brewing.
     * @return the total amount of water required, in fluid ounces.
     */
    public double getWaterOz(String coffeeType, double[] ingredients, double ratio){

        double espressoVolumeOz = ingredients[0];
        double waterOz = ingredients[2];

        double brewedWater = espressoVolumeOz * ratio; //Water used to brew espresso
        double brewedWaterOz = brewedWater / 28.34952; //Convert to fluid ounces

        double totalWater;

        //For Americano, use the full water amount
        if(coffeeType.equalsIgnoreCase("Americano"))
            totalWater = brewedWaterOz + waterOz;

        //For Latte and Cappuccino, use only the water needed to brew the espresso
        else
            totalWater = brewedWaterOz;

        return totalWater;
    }

    public double[] getAdjustedIngredients(String coffeeType, String size, double ratio){

        double[] ingredients = getBaseIngredients(coffeeType, size); //Get base ingredients
        double[] adjustedIngredients = {0, 0, 0, 1};

        adjustedIngredients[0] = getCoffeeBeanGrams(ingredients, ratio);
        adjustedIngredients[1] = getMilkOz(ingredients);
        adjustedIngredients[2] = getWaterOz(coffeeType, ingredients, ratio);

        return adjustedIngredients;
    }

    /**
     * Checks whether the truck has enough ingredients to prepare the desired drink.
     *
     * @param bins        the storage bins of a truck
     * @param ingredients the ingredients needed to brew the coffee drink.
     * @return true if all ingredients are available, false otherwise.
     */
    public boolean hasSufficientIngredients(StorageBin[] bins, double[] ingredients){

        int i;
        double available, required;
        boolean flag = true;

        for(i = 0; i < ingredients.length && flag; i++){

            StorageBin bin = bins[i];

            if(bin == null || bin.getContent() == null)
                flag = false;

            else {
                BinContent content = bin.getContent();
                available = content.getQuantity();
                required = ingredients[i];

                if (available < required)
                    flag = false;
            }
        }

        return flag;
    }

    /**
     * Deducts the required ingredients from the corresponding storage bins.
     *
     * @param bins        the storage bins of a truck.
     * @param ingredients the ingredients needed to brew the coffee drink.
     */
    public void useIngredients(StorageBin[] bins, double[] ingredients){

        int i;

        for(i = 0; i < bins.length; i++){

            StorageBin bin = bins[i];
            BinContent content = bin.getContent();

            if(content != null)
                content.useQuantity(ingredients[i]);
        }
    }

    /**
     * Searches the drink menu for a drink that matches the specified type and size.
     *
     * @param coffeeType  the type of coffee drink.
     * @param size        the size of the drink.
     * @return the matching Drink object, or null if not found.
     */
    public Drink getDrink(String coffeeType, String size){

        int i, found = 0;
        Drink drink = null;

        for(i = 0; i < drinks.size() && found == 0; i++){

            Drink d = drinks.get(i);

            if(d.getType().equalsIgnoreCase(coffeeType) && d.getSize().equalsIgnoreCase(size)){
                found = 1;
                drink = d;
            }
        }

        return drink;
    }

    public double getSyrupOzPrice(){

        return syrupOzPrice;
    }

    public double getExtraShotPrice(){

        return extraShotPrice;
    }

    public double getCoffeeGramPrice(){

        return coffeeGramPrice;
    }

    /**
     * Calculates the total cost of making a coffee drink
     *
     * @param coffeeType   the type of coffee drink.
     * @param ingredients  the ingredients needed to brew the coffee drink.
     * @param ratio        the ratio of coffee to water used for espresso brewing.
     * @return The total price of the drink.
     */
    public double calculateCoffeeCost(String coffeeType, double[] ingredients, double ratio){

        double coffeeCost = coffeeGramPrice * getCoffeeBeanGrams(ingredients, ratio);
        double milkCost = milkOzPrice * getMilkOz(ingredients);
        double waterCost = waterOzPrice * getWaterOz(coffeeType, ingredients, ratio);

        return coffeeCost + milkCost + waterCost;
    }

    public DrinkView getDrinkView(){

        return drinkView;
    }
}
