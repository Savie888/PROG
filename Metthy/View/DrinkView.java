package Metthy.View;

import Metthy.Model.BinContent;
import Metthy.Model.StorageBin;
import Metthy.Model.Syrup;

import java.util.ArrayList;

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

    public double enterExtraShotPrice(){

        double price;

        System.out.println("Enter price of an extra espresso shot: ");
        price = scanner.nextDouble();

        return price;
    }

    public BinContent enterSyrupName() {

        String name;
        BinContent content;

        scanner.nextLine(); //Absorb excess line
        System.out.println("Enter syrup name (e.g., Vanilla, Grape): ");
        name = scanner.nextLine();

        content = new Syrup(name);

        return content;
    }
    //Messages

    public void drinkMenuHeader(){
        System.out.println("\n--- Drinks Menu ---");
    }

    public void prepareDrinkHeader() {
        System.out.println("\n--- Prepare Coffee Drink ---");
    }

    public void invalidDrinkPrepInputMessage() {
        System.out.println("Invalid input! Drink preparation cancelled.");
    }

    public void showInsufficientIngredients() {

        System.out.println("Not enough ingredients or cups. Drink preparation cancelled.");
    }

    public void missingSyrupBinMessage(String syrupType){

        System.out.println("Error: Missing syrup bin for '" + syrupType + "'.");
    }

    public void notEnoughSyrupMessage(String syrupType, double amount){

        System.out.printf("Error: Not enough %s syrup in stock (needs %.2f oz).\n", syrupType, amount);
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

    public boolean hasAvailableSyrup(ArrayList<StorageBin> bins) {

        int i;

        for(i = 8; i < bins.size(); i++){
            StorageBin bin = bins.get(i);
            BinContent content = bin.getContent();
            if (content instanceof Syrup && content.getQuantity() > 0)
                return true;
        }

        return false;
    }

    public void displayAvailableSyrup(ArrayList<StorageBin> bins){

        int i;

        System.out.println("Available syrups: ");

        for(i = 8; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            BinContent content = bin.getContent();

            if(content instanceof Syrup && content.getQuantity() > 0)
                System.out.printf("- %s: %.2f oz\n", content.getName(), content.getQuantity());
        }
    }

    public BinContent selectAddOn(ArrayList<StorageBin> bins){

        int i;
        String type;
        BinContent addOn = null;

        do{
            System.out.println("Choose a syrup add-on: ");
            type = scanner.nextLine();

            for(i = 8; i < bins.size(); i++){

                StorageBin bin = bins.get(i);

                if (bin != null && bin.getContent() != null){
                    BinContent content = bin.getContent();

                    if(type.equalsIgnoreCase(content.getName())){
                        addOn = content;
                        break;
                    }
                }
            }

            if (addOn == null) {
                System.out.println("Invalid or unavailable syrup. Please try again.");
            }

        }while(addOn == null);

        return addOn;
    }

    public double selectAddOnAmount(){

        double amount;

        do{
            System.out.println("Enter amount: ");
            amount = scanner.nextDouble();
            scanner.nextLine(); //Clear excess line

            if(amount < 0 || amount > 640)
                System.out.println("Invalid quantity!");

        } while(amount < 0 || amount > 640);

        return amount;
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
