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
     * Calculates the amount of each ingredient required to prepare a drink.
     *
     * @param coffeeType the type of coffee
     * @param coffeeSize the size of the drink
     * @return a double array containing the required ingredients [coffeeGrams, milkOz, totalWaterOz]
     */
    private double[] getIngredients(String coffeeType, String coffeeSize){

        double cupSize = getCupSize(coffeeSize);
        double espressoOz = 0, milkOz = 0, waterOz = 0;

        if(cupSize == 0)
            System.out.println("Invalid Size. Cancelling");

        else{
            switch(coffeeType){
                case "Americano" -> {
                    espressoOz = cupSize * (1.0 / 3);
                    waterOz = cupSize * (2.0 / 3);
                }
                case "Latte" -> {
                    espressoOz = cupSize * (1.0 / 5);
                    milkOz = cupSize * (4.0 / 5);
                }
                case "Cappuccino" -> {
                    espressoOz = cupSize * (1.0 / 3);
                    milkOz = cupSize * (2.0 / 3);
                }
                default ->
                    System.out.println("Invalid drink type.");
            }
        }

        // Espresso requires water: 1 part coffee, 18 parts water → total 19 parts
        double espressoGrams = espressoOz * 28.34952;
        double coffeeGrams = espressoGrams / 19.0;
        double brewWaterGrams = coffeeGrams * 18;
        double brewWaterOz = brewWaterGrams / 28.34952;

        // Total water = espresso brewing water + extra water (e.g., in Americano)
        double totalWaterOz = brewWaterOz + waterOz;

        return new double[] {coffeeGrams, milkOz, totalWaterOz};
    }

    /**
     * Returns the amount of coffee beans (in grams) required for the given drink.
     *
     * @param coffeeType the type of coffee (e.g., "Latte", "Cappuccino", "Americano")
     * @param size       the drink size (e.g., "Small", "Medium", "Large")
     * @return the amount of coffee beans required, in grams
     */
    private double getCoffeeGrams(String coffeeType, String size){

        double espressoOz = getIngredients(coffeeType, size)[0];
        return (espressoOz * 28.34952) / 18.0;
    }

    /**
     * Returns the amount of milk (in fluid ounces) required for the given drink.
     *
     * @param coffeeType the type of coffee (e.g., "Latte", "Cappuccino", "Americano")
     * @param size       the drink size (e.g., "Small", "Medium", "Large")
     * @return the amount of milk required, in fluid ounces
     */
    private double getMilkOz(String coffeeType, String size){

        return getIngredients(coffeeType, size)[1];
    }

    /**
     * Returns the total amount of water (in fluid ounces) required for the given drink.
     *
     * @param coffeeType the type of coffee (e.g., "Latte", "Cappuccino", "Americano")
     * @param size       the drink size (e.g., "Small", "Medium", "Large")
     * @return the total amount of water required, in fluid ounces
     */
    private double getWaterOz(String coffeeType, String size){

        return getIngredients(coffeeType, size)[2];
    }

    /**
     * Displays the required ingredient amounts for a given coffee type and size.
     *
     * @param coffeeType the type of coffee (e.g., "Latte", "Cappuccino", "Americano")
     * @param coffeeSize the size of the drink (e.g., "Small", "Medium", "Large")
     */
    private void showIngredients(String coffeeType, String coffeeSize){

        double[] ingredients = getIngredients(coffeeType, coffeeSize);
        double espressoOz = ingredients[0];
        double milkOz = ingredients[1];
        double waterOz = ingredients[2];
        double espressoGrams = espressoOz * 28.34952 / 18;

        System.out.printf("Required Ingredients for %s (%s):\n", coffeeType, coffeeSize);
        System.out.printf("- Espresso: %.2f oz (%.2f g)\n", espressoOz, espressoGrams);
        System.out.printf("- Milk: %.2f oz\n", milkOz);
        System.out.printf("- Water: %.2f oz\n", waterOz);
    }

    /**
     * Prompts the user to select a coffee drink type.
     *
     * @return The selected coffee drink type as a String ("Americano", "Latte", or "Cappuccino"),
     *         or null if the user cancels.
     */
    private String selectDrinkType(){

        int option;
        String coffeeType, repeat = "";

        do{
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
    private String selectDrinkSize(){

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

    /**
     * Checks whether the truck has enough ingredients to prepare the desired drink.
     *
     * @param beanBin  The storage bin for coffee beans.
     * @param milkBin  The storage bin for milk.
     * @param waterBin The storage bin for water.
     * @param cupBin   The storage bin for the correct cup size.
     * @param ingredients An array containing required amounts: [beans (g), milk (oz), water (oz)].
     * @return true if all ingredients and one cup are available, false otherwise.
     */
    private boolean hasSufficientIngredients(StorageBin beanBin, StorageBin milkBin, StorageBin waterBin,
                                             StorageBin cupBin, double[] ingredients){

        double requiredBeans = ingredients[0];
        double requiredMilk = ingredients[1];
        double requiredWater = ingredients[2];

        boolean flag = true;

        if(beanBin == null || beanBin.getItemQuantity() < requiredBeans)
            flag = false;

        if(milkBin == null || milkBin.getItemQuantity() < requiredMilk)
            flag = false;

        if(waterBin == null || waterBin.getItemQuantity() < requiredWater)
            flag = false;

        if(cupBin == null || cupBin.getItemQuantity() < 1)
            flag = false;

        return flag;
    }
    /**
     * Deducts the required quantities of ingredients from the corresponding storage bins
     * during drink preparation.
     *
     * @param beanBin   the storage bin holding coffee beans
     * @param espressoGrams the amount of coffee beans used in grams
     * @param milkBin   the storage bin holding milk
     * @param milkOz    the amount of milk used in fluid ounces
     * @param waterBin  the storage bin holding water
     * @param waterOz   the amount of water used in fluid ounces
     * @param cupBin    the storage bin holding cups for the selected drink size
     */
    private void useIngredients(StorageBin beanBin, double espressoGrams, StorageBin milkBin, double milkOz,
                                StorageBin waterBin, double waterOz, StorageBin cupBin){

        beanBin.useQuantity(espressoGrams);
        milkBin.useQuantity(milkOz);
        waterBin.useQuantity(waterOz);
        cupBin.useQuantity(1);
    }

    /**
     * Displays the drinks menu
     *
     */
    public void displayDrinksMenu(){

        int i, j, k;
        double price;
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
                        price = calculateCoffeeCost(type, size);
                        drink.setPrice(price);
                        System.out.printf("  %s - $%.2f\n", size, price);
                    }
                }
            }
        }
    }

    /**
     * Searches the drink menu for a drink that matches the specified type and size.
     *
     * @param type the type of coffee drink (e.g., "Latte", "Cappuccino")
     * @param size the size of the drink (e.g., "Small", "Medium", "Large")
     * @return the matching Drink object, or null if not found
     */
    private Drink getDrink(String type, String size){

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

    /**
     * Handles the process of preparing a drink.
     *
     * @param truck The coffee truck preparing the drink.
     */
    public void prepareDrink(CoffeeTruck truck){

        String coffeeType, coffeeSize;
        double espressoOz, milkOz, waterOz, espressoGrams, price;
        double[] ingredients;

        System.out.println("\n--- Prepare Coffee Drink ---");
        coffeeType = selectDrinkType();
        coffeeSize = selectDrinkSize();

        if(coffeeType == null || coffeeSize == null)
            System.out.println("Invalid input! Drink preparation cancelled");

        else{

            Drink drink = getDrink(coffeeType, coffeeSize);

            System.out.printf("Preparing %s (%s)...\n", coffeeType, coffeeSize);
            showIngredients(coffeeType, coffeeSize);

            ingredients = getIngredients(coffeeType, coffeeSize); //Get the ingredients needed for the drink

            StorageBin beanBin = truck.findBin("Coffee Beans"); //Find bin with coffee beans
            StorageBin milkBin = truck.findBin("Milk"); //Find bin with milk
            StorageBin waterBin = truck.findBin("Water"); //Find bin with water
            StorageBin cupBin = truck.findBin(coffeeSize + " Cup"); //Find bin with specified cup size

            //Check if storage bins have sufficient ingredients
            if(hasSufficientIngredients(beanBin, milkBin, waterBin, cupBin, ingredients)){

                espressoOz = ingredients[0];
                milkOz = ingredients[1];
                waterOz = ingredients[2];
                espressoGrams = (espressoOz * 28.34952) / 18.0;
                price = drink.getPrice();

                //Deduct ingredients from storage bins
                useIngredients(beanBin, espressoGrams, milkBin, milkOz, waterBin, waterOz, cupBin);

                System.out.printf("\n>>> Preparing %s Cup...\n", coffeeSize);
                System.out.printf(">>> Brewing Standard espresso - %.2f grams of coffee...\n", espressoGrams);

                if(milkOz > 0)
                    System.out.println(">>> Adding Milk...");

                if(coffeeType.equalsIgnoreCase("Americano"))
                    System.out.println(">>> Adding Water...");

                System.out.printf(">>> %s Done!\n", coffeeType);

                System.out.printf("Total Price: $%.2f\n", price);

                truck.addToTotalSales(price); //Update total sales
                truck.recordSale(coffeeType, coffeeSize, espressoGrams, milkOz, waterOz, price); //Update sales log
            }

            else
                System.out.println("Not enough ingredients or cups. Drink preparation cancelled.");
        }
    }

    /**
     * Calculates the total cost of making a coffee drink
     *
     * @param coffeeType The type of coffee (e.g., "Latte").
     * @param coffeeSize The size of the drink (e.g., "Medium").
     * @return The total price of the drink.
     */
    private double calculateCoffeeCost(String coffeeType, String coffeeSize){

        double coffeeCost = coffeeGramPrice * getCoffeeGrams(coffeeType, coffeeSize);
        double milkCost = milkOzPrice * getMilkOz(coffeeType, coffeeSize);
        double waterCost = waterOzPrice * getWaterOz(coffeeType, coffeeSize);

        return coffeeCost + milkCost + waterCost;
    }

    /**
     * Displays the main coffee menu for the truck, allowing the user to view
     * available drinks or prepare a new drink.
     *
     * @param truck The coffee truck accessing the drink features.
     */
    public void coffeeMenu(CoffeeTruck truck){

        int option;

        do{
            System.out.println("\n=== Coffee Menu ===");
            System.out.println("1 - View Drink Menu");
            System.out.println("2 - Prepare Drink");
            System.out.println("3 - Exit Menu");
            System.out.println("Enter option: ");
            option = scanner.nextInt();

            switch(option){

                case 1:
                    displayDrinksMenu();
                    break;
                case 2:
                    prepareDrink(truck);
                    break;
                case 3:
                    System.out.println("Exiting Menu");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while(option != 3);
    }
}
