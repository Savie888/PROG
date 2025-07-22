package Metthy.View;

public class DrinkView extends View{


    public DrinkView(){

        super();
    }

    public int getCoffeeMenuInput(){

        int option;

        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    public void displayCoffeeMenu(){

        System.out.println("\n=== Coffee Menu ===");
        System.out.println("1 - Prepare Drink");
        System.out.println("2 - Exit Menu");
        System.out.println("Select an Option: ");
    }

    public double enterCoffeeBeanGramPrice(){

        double price;

        System.out.println("Enter price of coffee bean per gram: ");
        price = scanner.nextDouble();

        return price;
    }

    public double enterMilkOzPrice(){

        double price;

        System.out.println("Enter price of milk per ounce: ");
        price = scanner.nextDouble();

        return price;
    }

    public double enterWaterOzPrice(){

        double price;

        System.out.println("Enter price of water per ounce: ");
        price = scanner.nextDouble();

        return price;
    }

    public double enterSyrupOzPrice(){

        double price;

        System.out.println("Enter price of syrup per ounce: ");
        price = scanner.nextDouble();

        return price;
    }


    //Messages
    public void prepareDrinkHeader() {
        System.out.println("\n--- Prepare Coffee Drink ---");
    }

    public void invalidDrinkPrepInputMessage() {
        System.out.println("Invalid input! Drink preparation cancelled.");
    }

    public void showInsufficientIngredients() {
        System.out.println("Not enough ingredients or cups. Drink preparation cancelled.");
    }

    //PREPARE DRINK

    /**
     * Prompts the user to select a coffee drink type.
     *
     * @return The selected coffee drink type as a String ("Americano", "Latte", or "Cappuccino").
     */
    public String selectDrinkType(){

        int option;
        String coffeeType;

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
                    printInvalidOption();
                    break;
            }
        } while(coffeeType == null);

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
        String size;

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
                    printInvalidOption();
                    break;
            }

        } while(size == null);

        return size;
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

    public void displayPreparationSteps(String type, String size, String brewType, double[] ingredients) {
        System.out.printf("\n>>> Preparing %s Cup...\n", size);
        System.out.printf(">>> Brewing %s Espresso - %.2f grams of coffee...\n", brewType, ingredients[0]);

        if (ingredients[1] > 0)
            System.out.println(">>> Adding Milk...");

        if (type.equalsIgnoreCase("Americano"))
            System.out.println(">>> Adding Water...");

        System.out.printf(">>> %s Done!\n", type);
    }

    public void showTotalPrice(double price) {
        System.out.printf("Total Price: $%.2f\n", price);
    }
}
