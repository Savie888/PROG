package Metthy;

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

    private final ArrayList<AddOn> addOns = new ArrayList<>();

    public SpecialCoffeeTruck(String name, String location, DrinkManager drinkManager){

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

                    if(restock == 1)
                        restockAllBins(); //Restock all bins

                    else if(restock == 2){
                        System.out.println("Enter Bin Number: ");
                        binNumber = scanner.nextInt();
                        restockOneBin(binNumber); //Restock selected bin
                    }
                    break;
                case 2:
                    System.out.println("1 - Modify all bins");
                    System.out.println("2 - Modify one bin");
                    System.out.println("Select an option: ");
                    int modify = scanner.nextInt();

                    if(modify == 1)
                        modifyAllBins(); //Modify all bins

                    else if(modify == 2){
                        System.out.println("Enter bin number to modify: ");
                        int binNum = scanner.nextInt();

                        if(binNum == 9 || binNum == 10)
                            modifySyrupBin(binNum); //Modify syrup bin
                        else
                            modifyBin(binNum); //Modify regular bin
                    }
                    break;
                case 3:
                    System.out.println("1 - Empty all bins");
                    System.out.println("2 - Empty one bin");
                    System.out.println("Select an option: ");
                    int empty = scanner.nextInt();

                    if(empty == 1)
                        emptyAllBins(); //Empty all bins

                    else if(empty == 2){
                        System.out.println("Enter Bin Number: ");
                        binNumber = scanner.nextInt();
                        emptyOneBin(binNumber); //Empty selected bin
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

    public double selectAddOnAmount(){

        double amount;

        do{
            System.out.println("Enter amount: ");
            amount = scanner.nextDouble();

            if(amount < 0 || amount > 640)
                System.out.println("Invalid quantity!");

        } while(amount < 0 || amount > 640);

        return amount;
    }

    public String selectAddOnType(){

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

        }while(!type.equalsIgnoreCase(""));

        return type;
    }

    public ArrayList<AddOn> selectAddOns(){

        String type, repeat;
        double amount;

        do{
            type = selectAddOnType();

            amount = selectAddOnAmount();

            addOns.add(new AddOn(type, amount));

            System.out.println("Add more? (yes/no): ");
            repeat = scanner.nextLine();

        } while(repeat.equalsIgnoreCase("yes"));

        return addOns;
    }

    /**
     * Records a drink sale to a truck's sales log.
     *
     * @param coffeeType   the type of coffee sold
     * @param size         the size of the drink
     * @param coffeeGrams  the grams of coffee beans used
     * @param milk         the ounces of milk used
     * @param water        the ounces of water used
     * @param price        the price of the drink
     */
    //WIP, add syrup and extra shots
    protected void recordSale(String coffeeType, String size, String brewType, double coffeeGrams,
                              double milk, double water, ArrayList<AddOn> addOns, double price){

        StringBuilder addOnDetails = new StringBuilder();

        if (addOns != null && !addOns.isEmpty()) {
            addOnDetails.append(" | Add-Ons: ");
            for (int i = 0; i < addOns.size(); i++) {
                AddOn a = addOns.get(i);
                addOnDetails.append(String.format("%.1f oz %s", a.getAmount(), a.getType()));
                if (i < addOns.size() - 1) {
                    addOnDetails.append(", ");
                }
            }
        }
        salesLog.add(String.format("Drink: %s (%s) | %.2f g beans, %.2f oz milk, %.2f oz water | %.2f%s | $%.2f",
                coffeeType, size, coffeeGrams, milk, water, addOnDetails.toString(), price));
    }

    /**
     * Handles the process of preparing a drink.
     *
     */
    @Override
    protected void prepareDrink(){

        int i;
        String coffeeType, coffeeSize, brewType, add;
        double espressoGrams, milkOz, waterOz, price, ratio;
        double[] ingredients;
        ArrayList<AddOn> addOns = new ArrayList<>();

        System.out.println("\n--- Prepare Coffee Drink ---");
        coffeeType = drinkManager.selectDrinkType();
        coffeeSize = drinkManager.selectDrinkSize();
        brewType = drinkManager.selectBrewType();
        ratio = drinkManager.getBrewRatio(brewType);

        if(coffeeType == null || coffeeSize == null)
            System.out.println("Invalid input! Drink preparation cancelled");

        else{

            System.out.println("Add syrup add-ons? (yes/no)");
            add = scanner.nextLine();

            if(add.equalsIgnoreCase("yes"))
                addOns = selectAddOns();

            Drink drink = drinkManager.getDrink(coffeeType, coffeeSize);
            drink.setBrewType(brewType);

            ingredients = drinkManager.getAdjustedIngredients(coffeeType, coffeeSize, ratio); //Get the ingredients needed for the drink

            drinkManager.showIngredients(coffeeType, coffeeSize, brewType, ingredients); //Show required ingredients

            StorageBin beanBin = findBin("Coffee Beans"); //Find bin with coffee beans
            StorageBin milkBin = findBin("Milk"); //Find bin with milk
            StorageBin waterBin = findBin("Water"); //Find bin with water
            StorageBin cupBin = findBin(coffeeSize + " Cup"); //Find bin with specified cup size
            StorageBin[] bins = { beanBin, milkBin, waterBin, cupBin };

            //Check if storage bins have sufficient ingredients
            if(drinkManager.hasSufficientIngredients(bins, ingredients)){

                espressoGrams = ingredients[0];
                milkOz = ingredients[1];
                waterOz = ingredients[2];
                price = drink.getPrice();

                //Deduct ingredients from storage bins
                drinkManager.useIngredients(bins, ingredients);

                System.out.printf("\n>>> Preparing %s Cup...\n", coffeeSize);
                System.out.printf(">>> Brewing %s Espresso - %.2f grams of coffee...\n", brewType, espressoGrams);

                if(milkOz > 0)
                    System.out.println(">>> Adding Milk...");

                if(coffeeType.equalsIgnoreCase("Americano"))
                    System.out.println(">>> Adding Water...");

                if(!addOns.isEmpty()){
                    for(i = 0; i < addOns.size(); i++){
                        AddOn addOn = addOns.get(i);
                        System.out.println(">>> Adding " + addOn.getType() + " Syrup");
                        price += addOn.getAmount() * drinkManager.getSyrupPrice();
                    }
                }

                System.out.printf(">>> %s Done!\n", coffeeType);

                System.out.printf("Total Price: $%.2f\n", price);

                addToTotalSales(price); //Update total sales
                recordSale(coffeeType, coffeeSize, espressoGrams, milkOz, waterOz, price); //Update sales log
            }

            else
                System.out.println("Not enough ingredients or cups. Drink preparation cancelled.");
        }
    }

}
