package Metthy.Model;

import Metthy.DrinkManager;
import Metthy.StorageBin;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a special coffee truck
 */
public class SpecialCoffeeTruck extends RegularCoffeeTruck {

    /**
     * Scanner object for reading user input from the console.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a special coffee truck with the given name and location.
     * Initializes 10 storage bins and an empty sales log.
     *
     * @param name         the name of the coffee truck
     * @param location     the initial location of the truck
     * @param drinkManager the shared drink manager to be used by all trucks
     */
    public SpecialCoffeeTruck(String name, String location, DrinkManager drinkManager){

        //Call regular coffee truck constructor
        super(name, location, drinkManager);

        //Create the 2 extra storage bins for syrups
        getBins().add(new StorageBin(9));
        getBins().add(new StorageBin(10));
    }

    public void modifySyrupBin(int binNumber){

        int choice, maxCapacity = 640;
        int binIndex = binNumber - 1;
        double quantity;
        boolean invalidQuantity;
        String itemType;

        System.out.println("\nSetting up Bin #" + binNumber);

        StorageBin bin = bins.get(binIndex);

        do{
            invalidQuantity = false;

            System.out.println("Choose syrup add-on to store in Bin #" + binNumber + ":");
            System.out.println("1. Hazelnut");
            System.out.println("2. Chocolate");
            System.out.println("3. Almond");
            System.out.println("4. Sucrose (Sweetener)");
            System.out.print("Select item number (0 to skip this bin): ");

            choice = scanner.nextInt();
            scanner.nextLine(); //Consume newline

            if(choice == 0){
                System.out.println("Skipping Bin #" + binNumber);
                break;
            }

            else{
                switch(choice){

                    case 1:
                        bin.setItemType("Hazelnut");
                        break;
                    case 2:
                        bin.setItemType("Chocolate");
                        break;
                    case 3:
                        bin.setItemType("Almond");
                        break;
                    case 4:
                        bin.setItemType("Sucrose");
                        break;
                    default:
                        bin.setItemType(null);
                        break;
                }

                if(bin.getItemType() == null)
                    System.out.println("Invalid selection. Please try again.");

                else{
                    System.out.print("Enter quantity (max " + maxCapacity + "): ");
                    quantity = scanner.nextDouble();
                    scanner.nextLine(); //Consume newline

                    if(quantity < 0 || quantity > maxCapacity){
                        System.out.println("Invalid quantity. Must be between 0 and " + maxCapacity + ".");
                        invalidQuantity = true;
                    }

                    else{
                        itemType = bin.getItemType();
                        assignItemToBin(binIndex, itemType, quantity);
                        System.out.println("Bin #" + binNumber + " loaded with " + quantity + " ounces of " + itemType);
                    }
                }
            }
        } while(bin.getItemType() == null || invalidQuantity);
    }

    public void setSpecialLoadout(){

        int i, binNumber;
        String max;
        ArrayList<StorageBin> bins = getBins();

        System.out.println("\n--- Setting Loadout for " + name + " ---");

        System.out.println("Set Storage Bins to default loadout? (yes/no): ");
        max = scanner.nextLine();

        if(max.equalsIgnoreCase("yes"))
            setDefaultLoadout();

        else{
            for(i = 0; i < 8; i++){
                StorageBin bin = bins.get(i);
                binNumber = bin.getBinNumber();

                modifyBin(binNumber);
            }

            for(i = 8; i < bins.size(); i++){

                StorageBin bin = bins.get(i);
                binNumber = bin.getBinNumber();

                modifySyrupBin(binNumber);
            }

            System.out.println("\nLoadout for " + name + " complete!\n");
        }
    }

    /**
     * Displays the truck maintenance menu for a specific coffee truck.
     * <p>Allows the user to:</p>
     * <ul>
     *   <li>Restock bins (either all or individually)</li>
     *   <li>Modify storage bin contents (either all or individually)</li>
     *   <li>Empty bins (either all or individually)</li>
     *   <li>Edit the truck's name or location</li>
     *   <li>Edit global drink ingredient prices</li>
     * </ul>
     *
     */
    @Override
    public void truckMaintenanceMenu(){

        int option, restock, binNumber;

        do{
            System.out.println("\n=== Truck Maintenance ===");
            System.out.println("1 - Restock Bins (only works on bins with an assigned item)");
            System.out.println("2 - Modify Storage Bin Contents");
            System.out.println("3 - Empty Storage Bins");
            System.out.println("4 - Edit Truck Name");
            System.out.println("5 - Edit Truck Location");
            System.out.println("6 - Edit Pricing (will affect pricing for all trucks)");
            System.out.println("7 - Exit Menu");
            System.out.println("Select an Option: ");
            option = scanner.nextInt();
            scanner.nextLine(); //Absorb new line

            switch(option){

                case 1:
                    System.out.println("1 - Restock all bins");
                    System.out.println("2 - Restock one bin");
                    System.out.println("Select an option: ");
                    restock  = scanner.nextInt();
                    scanner.nextLine(); //Clear excess line

                    if(restock == 1)
                        restockAllBins(); //Restock all bins

                    else if(restock == 2){
                        binNumber = selectBinNumber();
                        restockBin(binNumber); //Restock selected bin
                    }
                    break;
                case 2:
                    System.out.println("1 - Modify all bins");
                    System.out.println("2 - Modify one bin");
                    System.out.println("Select an option: ");
                    int modify = scanner.nextInt();
                    scanner.nextLine(); //Clear excess line

                    if(modify == 1)
                        modifyAllBins(); //Modify all bins

                    else if(modify == 2){
                        binNumber = selectBinNumber();

                        if(binNumber == 9 || binNumber == 10)
                            modifySyrupBin(binNumber); //Modify syrup bin
                        else
                            modifyBin(binNumber); //Modify regular bin
                    }
                    break;
                case 3:
                    System.out.println("1 - Empty all bins");
                    System.out.println("2 - Empty one bin");
                    System.out.println("Select an option: ");
                    int empty = scanner.nextInt();
                    scanner.nextLine(); //Clear excess line

                    if(empty == 1)
                        emptyAllBins(); //Empty all bins

                    else if(empty == 2){
                        binNumber = selectBinNumber();
                        emptyBin(binNumber); //Empty selected bin
                    }
                    break;
                case 4:
                    System.out.println("Enter new name: ");
                    String name = scanner.nextLine();
                    setName(name); //Set new name
                    break;
                case 5:
                    System.out.println("Enter new location: ");
                    String location = scanner.nextLine();
                    setLocation(location); //Set new location
                    break;
                case 6:
                    drinkManager.setIngredientPrices(); //Set ingredient prices
                    break;
                case 7:
                    System.out.println("Exiting Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        } while(option != 7);
    }

    private String selectAddOnType(){

        String type;
        int option;

        do{
            System.out.println("Choose a syrup add-on: ");
            System.out.println("1. Hazelnut");
            System.out.println("2. Chocolate");
            System.out.println("3. Almond");
            System.out.println("4. Sucrose (Sweetener)");
            System.out.println("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); //Clear excess line

            switch(option){

                case 1:
                    type = "Hazelnut";
                    break;
                case 2:
                    type = "Chocolate";
                    break;
                case 3:
                    type = "Almond";
                    break;
                case 4:
                    type = "Sucrose";
                    break;
                default:
                    type = "";
                    System.out.println("Invalid option selected");
                    break;
            }

        }while(type.equalsIgnoreCase(""));

        return type;
    }

    private double selectAddOnAmount(){

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

    private ArrayList<AddOn> selectAddOns(){

        String type, repeat;
        ArrayList<AddOn> addOns = new ArrayList<>();
        double amount;
        boolean validAddOn;

        do{
            type = selectAddOnType();
            amount = selectAddOnAmount();

            validAddOn = hasSufficientSyrup(type, amount);

            if(validAddOn)
                addOns.add(new AddOn(type, amount));

            System.out.println("Continue adding? (yes/no): ");
            repeat = scanner.nextLine();

        } while(repeat.equalsIgnoreCase("yes"));

        return addOns;
    }

    private boolean hasSufficientSyrup(String syrupType, double amount){

        boolean validAddOns = true;

        StorageBin syrupBin = findBin(syrupType); //Find the bin containing the specified add-on

        if(syrupBin == null){
            System.out.println("Error: Missing syrup bin for '" + syrupType + "'.");
            validAddOns = false;
        }

        else if(syrupBin.getItemQuantity() < amount){
            System.out.printf("Error: Not enough %s syrup in stock (needs %.2f oz).\n", syrupType, amount);
            validAddOns = false;
        }

        return validAddOns;
    }

    private void useSyrupAddOns(ArrayList<AddOn> addOns){

        int i;
        String syrupType;
        double amount;

        for(i = 0; i < addOns.size(); i++){

            AddOn addOn = addOns.get(i);
            syrupType = addOn.getType();
            amount  = addOn.getAmount();

            StorageBin syrupBin = findBin(syrupType);
            syrupBin.useQuantity(amount);
        }
    }

    private int selectExtraShots(double coffeeGrams, double remainingCoffeeGrams){

        int shots, maxShots;

        maxShots = (int) (remainingCoffeeGrams / coffeeGrams);

        do{
            System.out.printf("Enter number of extra espresso shots (0 - %d): ", maxShots);
            shots = scanner.nextInt();
            scanner.nextLine(); //Clear excess line

            if(shots < 0 || shots > maxShots)
                System.out.println("Invalid number entered");

        } while(shots < 0 || shots > maxShots);

        return shots;
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
    private void recordSale(String coffeeType, String size, String brewType, double coffeeGrams,
                              double milk, double water, ArrayList<AddOn> addOns, int extraShots, double price){

        int i;
        double extraCoffeeGrams;

        String drinkInfo;
        StringBuilder ingredients = new StringBuilder();
        StringBuilder addOnDetails = new StringBuilder();

        //Build base ingredient info string (without extra shots or add-ons)
        ingredients.append(String.format("%.2f g beans, %.2f oz milk, %.2f oz water", coffeeGrams, milk, water));

        //Build formatted drink info line
        drinkInfo = String.format("Drink: %s %s (%s)", coffeeType, size, brewType);

        //Include extra shot info if any
        if(extraShots > 0){
            extraCoffeeGrams = coffeeGrams * extraShots;
            ingredients.append(String.format(" + %d extra shot%s (%.2f g beans)", extraShots, (extraShots == 1 ? "" : "s"), extraCoffeeGrams));
        }

        //Build add-on details, if any
        if(!addOns.isEmpty()){
            addOnDetails.append("Add-Ons: ");

            for(i = 0; i < addOns.size(); i++){
                AddOn a = addOns.get(i);
                addOnDetails.append(String.format("%.1f oz %s", a.getAmount(), a.getType()));

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

    /**
     * Handles the process of preparing a drink.
     *
     */
    @Override
    protected void prepareDrink(){

        int i, extraShots = 0;
        String add, extra;
        double coffeeGrams, milkOz, waterOz, remainingCoffeeGrams, price;
        double extraCoffeeGrams = 0, extraShotCost = 0;
        double[] ingredients;
        ArrayList<AddOn> addOns = new ArrayList<>();

        System.out.println("\n--- Prepare Coffee Drink ---");
        String coffeeType = drinkManager.selectDrinkType();
        String coffeeSize = drinkManager.selectDrinkSize();
        String brewType = drinkManager.selectBrewType();
        double ratio = drinkManager.getBrewRatio(brewType);

        if(coffeeType == null || coffeeSize == null)
            System.out.println("Invalid input! Drink preparation cancelled");

        else{
            Drink drink = drinkManager.getDrink(coffeeType, coffeeSize);
            drink.setBrewType(brewType);

            ingredients = drinkManager.getAdjustedIngredients(coffeeType, coffeeSize, ratio); //Get the ingredients needed for the drink
            drinkManager.showIngredients(coffeeType, coffeeSize, brewType, ingredients); //Show required ingredients

            StorageBin beanBin = findBin("Coffee Beans"); //Find bin with coffee beans
            StorageBin milkBin = findBin("Milk"); //Find bin with milk
            StorageBin waterBin = findBin("Water"); //Find bin with water
            StorageBin cupBin = findBin(coffeeSize + " Cup"); //Find bin with specified cup size
            StorageBin[] bins = {beanBin, milkBin, waterBin, cupBin};

            System.out.println("Add syrup add-ons? (yes/no)");
            add = scanner.nextLine();

            if(add.equalsIgnoreCase("yes"))
                addOns = selectAddOns();

            //Check if storage bins have sufficient ingredients and syrup quantities
            if(drinkManager.hasSufficientIngredients(bins, ingredients)){

                coffeeGrams = ingredients[0];
                milkOz = ingredients[1];
                waterOz = ingredients[2];
                price = drink.getPrice();

                drinkManager.useIngredients(bins, ingredients); //Deduct ingredients from storage bins

                remainingCoffeeGrams = beanBin.getItemQuantity(); //Get remaining coffee bean quantity

                useSyrupAddOns(addOns); //Deduct ingredients from syrup bins

                System.out.println("Add extra espresso shots? (yes/no): ");
                extra = scanner.nextLine();

                if(extra.equalsIgnoreCase("yes")){
                    extraShots = selectExtraShots(coffeeGrams, remainingCoffeeGrams);
                    extraCoffeeGrams = coffeeGrams * extraShots;
                    beanBin.useQuantity(coffeeGrams * extraShots);
                    extraShotCost = extraShots * coffeeGrams * drinkManager.getCoffeeGramPrice();
                }

                System.out.printf("\n>>> Preparing %s Cup...\n", coffeeSize);
                System.out.printf(">>> Brewing %s Espresso - %.2f grams of coffee...\n", brewType, coffeeGrams);

                if(milkOz > 0)
                    System.out.println(">>> Adding Milk...");

                if(coffeeType.equalsIgnoreCase("Americano"))
                    System.out.println(">>> Adding Water...");

                System.out.println(price);
                if(!addOns.isEmpty()){
                    for(i = 0; i < addOns.size(); i++){
                        AddOn addOn = addOns.get(i);
                        System.out.println(">>> Adding " + addOn.getType() + " Syrup");
                        price += addOn.getAmount() * drinkManager.getSyrupPrice();
                    }
                }
                System.out.println(price);

                if(extraShots != 0){
                    System.out.printf(">>> Added %d extra shot%s (%.2f g beans)\n", extraShots, extraShots == 1 ? "" : "s", extraCoffeeGrams);
                    price += extraShotCost;
                }

                System.out.printf(">>> %s Done!\n", coffeeType);

                System.out.printf("Total Price: $%.2f\n", price);

                addToTotalSales(price); //Update truck's total sales
                recordSale(coffeeType, coffeeSize, brewType, coffeeGrams, milkOz, waterOz, addOns, extraShots, price); //Update sales log
            }

            else
                System.out.println("Not enough ingredients. Drink preparation cancelled.");
        }
    }
}
