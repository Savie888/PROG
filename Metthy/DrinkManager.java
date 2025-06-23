package Metthy;

import java.util.Scanner;
import java.util.ArrayList;

public class DrinkManager {

    //private CoffeeTruck truck;
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Drink> drinks = new ArrayList<>();
    private ArrayList<CoffeeTruck> trucks;
    private ArrayList<StorageBin> bins;
    private double coffee_price_per_gram;
    private double milk_price_per_ounce;
    private double water_price_per_ounce;

    public DrinkManager(ArrayList<CoffeeTruck> trucks, ArrayList<StorageBin> bins){

        this.trucks = trucks;
        this.bins = bins;
    }

    public void setIngredientPrices() {

        System.out.println("Enter price of coffee bean per gram: ");
        coffee_price_per_gram = scanner.nextDouble();

        System.out.println("Enter price of milk per ounce: ");
        milk_price_per_ounce = scanner.nextDouble();

        System.out.println("Enter price of water per ounce: ");
        water_price_per_ounce = scanner.nextDouble();
    }

    public double getCupSize(String size){

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

    public double[] getIngredients(String coffeeType, String coffeeSize){

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
                default -> {
                    System.out.println("Invalid drink type.");
                }
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

    public double getCoffeeGrams(String coffeeType, String size){

        double espressoOz = getIngredients(coffeeType, size)[0];
        return (espressoOz * 28.34952) / 18.0;
    }

    public double getMilkOz(String coffeeType, String size){

        return getIngredients(coffeeType, size)[1];
    }

    public double getWaterOz(String coffeeType, String size){

        return getIngredients(coffeeType, size)[2];
    }

    public void showIngredients(String coffeeType, String coffeeSize){

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

    private String getDrinkType(){

        int option;
        String coffeeType = "", repeat = "";

        do{
            System.out.println("Select drink type:");
            System.out.println("1 - Americano");
            System.out.println("2 - Latte");
            System.out.println("3 - Cappuccino");
            System.out.print("Enter choice: ");
            option = scanner.nextInt();
            scanner.nextLine();  //flush

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

    private String getDrinkSize(){

        int option;
        String size = "", repeat = "";

        do{
            System.out.println("Select size:");
            System.out.println("1 - Small");
            System.out.println("2 - Medium");
            System.out.println("3 - Large");
            System.out.print("Enter choice: ");
            option = scanner.nextInt();
            scanner.nextLine();  //flush

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

    private boolean hasSufficientIngredients(StorageBin beanBin, StorageBin milkBin, StorageBin waterBin, StorageBin cupBin, double[] ingredients){

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

    public void prepareDrink(CoffeeTruck truck){

        String coffeeType, coffeeSize;
        double[] ingredients;

        System.out.println("\n--- Prepare Coffee Drink ---");
        coffeeType = getDrinkType();
        coffeeSize = getDrinkSize();

        if(coffeeType == null || coffeeSize == null)
            System.out.println("Invalid input! Drink preparation cancelled");

        else{

            System.out.printf("Preparing %s (%s)...\n", coffeeType, coffeeSize);
            showIngredients(coffeeType, coffeeSize);

            ingredients = getIngredients(coffeeType, coffeeSize);

            StorageBin beanBin = truck.findBin("Coffee Beans");
            StorageBin milkBin = truck.findBin("Milk");
            StorageBin waterBin = truck.findBin("Water");
            StorageBin cupBin = truck.findBin(coffeeSize + " Cup");

            if(hasSufficientIngredients(beanBin, milkBin, waterBin, cupBin, ingredients)){

                double espressoOz = ingredients[0];
                double milkOz = ingredients[1];
                double waterOz = ingredients[2];
                double grams = (espressoOz * 28.34952) / 18.0;

                beanBin.useQuantity(grams);
                milkBin.useQuantity(milkOz);
                waterBin.useQuantity(waterOz);
                cupBin.useQuantity(1);

                System.out.printf("Used: %.2f g beans, %.2f oz milk, %.2f oz water, 1 %s cup\n",
                        grams, milkOz, waterOz, coffeeSize.toLowerCase());
            }

            else
                System.out.println("Not enough ingredients or cups. Drink preparation cancelled.");
        }
    }


    public double calculateCoffeeCost(String coffeeType, String coffeeSize){

        double coffeeCost = coffee_price_per_gram * getCoffeeGrams(coffeeType, coffeeSize);
        double milkCost = milk_price_per_ounce * getMilkOz(coffeeType, coffeeSize);
        double waterCost = water_price_per_ounce * getWaterOz(coffeeType, coffeeSize);

        return  coffeeCost + milkCost + waterCost;
    }


    public void displayDrinkMenu(){

        int i, j, k;
        String[] types = {"Latte", "Cappuccino", "Americano"};
        String[] sizes = {"Small", "Medium", "Large"};

        System.out.println("\n--- Drinks Menu ---");

        for(i = 0; i < types.length; i++){

            for(j = 0; j < sizes.length; j++){

                String type = types[i];
                String size = sizes[j];
                double price = calculateCoffeeCost(type, size);
                drinks.add(new Drink(type, size, price));
            }
        }

        for(k = 0; k < drinks.size(); k++){

            Drink drink = drinks.get(k);
            System.out.printf("%s %s - $%.2f\n", drink.getSize(), drink.getCoffeeType(), drink.getPrice());
        }
    }

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
                    displayDrinkMenu();
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

    public double getCoffee_price_per_gram(){

        return coffee_price_per_gram;
    }

    public double getMilk_price_per_ounce(){

        return milk_price_per_ounce;
    }

    public double getWater_price_per_ounce(){

        return water_price_per_ounce;
    }
}
