package Metthy;

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
public class DrinkManager {

    /**
     * Scanner object for reading user input from the console.
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * List of available drinks that can be prepared by the coffee trucks.
     */
    private final ArrayList<Drink> drinks = new ArrayList<>();
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
    private double syrupPrice;

    /**
     * Constructs a new DrinkManager and sets up the drink menu
     */
    public DrinkManager(){

        setupDrinkMenu(); //Set up the drink menu
    }

    /**
     * Initializes the drink menu with all combinations of coffee types and sizes.
     *
     */
    private void setupDrinkMenu(){

        int i, j;

        String[] types = {"Latte", "Cappuccino", "Americano"};
        String[] sizes = {"Small", "Medium", "Large"};

        for(i = 0; i < types.length; i++){
            for(j = 0; j < sizes.length; j++){

                String type = types[i];
                String size = sizes[j];
                drinks.add(new Drink(type, size, 0.0));
            }
        }
    }

    /**
     * Prompts the user to input the unit prices for coffee ingredients (coffee beans, milk, and water).
     *
     */
    public void setIngredientPrices() {

        System.out.println("Enter price of coffee bean per gram: ");
        coffeeGramPrice = scanner.nextDouble();

        System.out.println("Enter price of milk per ounce: ");
        milkOzPrice = scanner.nextDouble();

        System.out.println("Enter price of water per ounce: ");
        waterOzPrice = scanner.nextDouble();

        System.out.println("Enter price of a syrup add-on: ");
        syrupPrice = scanner.nextDouble();
    }

    /**
     * Prompts the user to select a coffee drink type.
     *
     * @return The selected coffee drink type as a String ("Americano", "Latte", or "Cappuccino"),
     *         or null if the user cancels.
     */
    public String selectDrinkType(){

        int option;
        String coffeeType, repeat;

        do{
            repeat = "";

            System.out.println("Select drink type:");
            System.out.println("1 - Americano");
            System.out.println("2 - Latte");
            System.out.println("3 - Cappuccino");
            System.out.print("Enter choice: ");
            option = scanner.nextInt();
            scanner.nextLine();  //Clear excess line

            switch(option){

                case 1:
                    coffeeType = "Americano";
                    break;
                case 2:
                    coffeeType = "Latte";
                    break;
                case 3:
                    coffeeType = "Cappuccino";
                    break;
                default:
                    coffeeType = null;
                    System.out.println("Invalid Choice. Try again? (yes/no): ");
                    repeat = scanner.nextLine();
                    break;
            }
        } while(repeat.equalsIgnoreCase("yes"));

        return coffeeType;
    }

    /**
     * Prompts the user to select a coffee drink size.
     *
     * @return The selected coffee size as a String ("Small", "Medium", or "Large"),
     *         or null if the user cancels.
     */
    public String selectDrinkSize(){

        int option;
        String size, repeat = "";

        do{
            System.out.println("\nSelect drink size:");
            System.out.println("1 - Small");
            System.out.println("2 - Medium");
            System.out.println("3 - Large");
            System.out.print("Enter choice: ");
            option = scanner.nextInt();
            scanner.nextLine();  //Clear new line

            switch(option){

                case 1:
                    size = "Small";
                    break;
                case 2:
                    size = "Medium";
                    break;
                case 3:
                    size = "Large";
                    break;
                default:
                    size = null;
                    System.out.println("Invalid Choice. Try again? (yes/no): ");
                    repeat = scanner.nextLine();
                    break;
            }

        } while(repeat.equalsIgnoreCase("yes"));

        return size;
    }

    public String selectBrewType(){

        int option;
        String brewType, repeat;

        do{
            repeat = "";

            System.out.println("\nSelect brew type:");
            System.out.println("1 - Standard");
            System.out.println("2 - Light");
            System.out.println("3 - Strong");
            System.out.println("4 - Custom");
            System.out.print("Enter choice: ");
            option = scanner.nextInt();
            scanner.nextLine();  //Clear excess line

            switch(option){

                case 1:
                    brewType = "Standard";
                    break;
                case 2:
                    brewType = "Light";
                    break;
                case 3:
                    brewType = "Strong";
                    break;
                case 4:
                    brewType = "Custom";
                    break;
                default:
                    brewType = "Standard";
                    System.out.println("Invalid Choice. Try again? (yes/no): ");
                    repeat = scanner.nextLine();
                    break;
            }
        } while(repeat.equalsIgnoreCase("yes"));

        if(repeat.equalsIgnoreCase("no"))
            System.out.println("No valid brew type selected, using standard brew type");

        return brewType;
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

    public double getBrewRatio(String brewType){

        double ratio;

        switch(brewType){

            case "Strong":
                ratio = 15.0;
                break;
            case "Light":
                ratio = 20.0;
                break;
            case "Custom":
                System.out.println("Enter custom ratio (Standard ratio is 18.0): ");
                ratio = scanner.nextDouble();
                break;
            default:
                ratio = 18.0;
                break;
        }

        return ratio;
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

        if(cupSize == 0)
            System.out.println("Invalid Size. Cancelling");

        else{
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
                default ->
                    System.out.println("Invalid drink type.");
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
     * Displays the required ingredient amounts for a coffee drink
     *
     * @param coffeeType   the type of coffee.
     * @param size         the drink size.
     * @param brewType     the type of coffee brew.
     * @param ingredients  the ingredients needed to brew the coffee drink.
     */
    public void showIngredients(String coffeeType, String size, String brewType, double[] ingredients){

        double espressoGrams = ingredients[0];
        double milkOz = ingredients[1];
        double waterOz = ingredients[2];

        System.out.printf("\nRequired Ingredients for %s %s (%s):\n", size, coffeeType, brewType);
        System.out.printf("- Coffee Beans: %.2f g\n", espressoGrams);
        System.out.printf("- Milk: %.2f oz\n", milkOz);
        System.out.printf("- Water: %.2f oz\n", waterOz);
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

        boolean flag = true;

        for(i = 0; i < ingredients.length && flag; i++)
            if(bins[i] == null || bins[i].getItemQuantity() < ingredients[i])
                flag = false;

        return flag;
    }

    /**
     * Deducts the required ingredients from the corresponding storage bins.
     *
     * @param bins        the storage bins of a truck
     * @param ingredients the ingredients needed to brew the coffee drink.
     */
    public void useIngredients(StorageBin[] bins, double[] ingredients){

        int i;

        for(i = 0; i < bins.length; i++){

            StorageBin bin = bins[i];
            bin.useQuantity(ingredients[i]);
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

        System.out.println("\n--- Drinks Menu ---");

        for(i = 0; i < types.length; i++){

            String type = types[i];
            System.out.println(type + ":");

            for(j = 0; j < sizes.length; j++){

                String size = sizes[j];

                //Find the matching drink
                for(k = 0; k < drinks.size(); k++){

                    Drink drink = drinks.get(k);

                    if(drink.getCoffeeType().equalsIgnoreCase(type) && drink.getSize().equalsIgnoreCase(size)){
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
     * Searches the drink menu for a drink that matches the specified type and size.
     *
     * @param type the type of coffee drink.
     * @param size the size of the drink.
     * @return the matching Drink object, or null if not found.
     */
    public Drink getDrink(String type, String size){

        int i, found = 0;
        Drink drink = null;

        for(i = 0; i < drinks.size() && found == 0; i++){

            Drink d = drinks.get(i);

            if(d.getCoffeeType().equalsIgnoreCase(type) && d.getSize().equalsIgnoreCase(size)){
                found = 1;
                drink = d;
            }
        }

        return drink;
    }

    public double getSyrupPrice(){

        return syrupPrice;
    }

    public double getCoffeeGramPrice(){

        return coffeeGramPrice;
    }

    /**
     * Calculates the total cost of making a coffee drink
     *
     * @param coffeeType   the type of coffee.
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

}
