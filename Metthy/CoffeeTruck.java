package Metthy;

import java.util.ArrayList;
import java.util.Scanner;

public class CoffeeTruck {

    private String name;
    private String location;
    private ArrayList<StorageBin> bins;
    private final ArrayList<String> salesLog;
    private double totalSales;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a CoffeeTruck with the given name and location.
     * Initializes 8 storage bins and an empty sales log.
     *
     * @param name     the name of the coffee truck
     * @param location the initial location of the truck
     */
    public CoffeeTruck(String name, String location){

        int i;
        this.name = name;
        this.location = location;
        this.bins = new ArrayList<>(8);

        for(i = 0; i < 8; i++)
            bins.add(new StorageBin(i));

        salesLog = new ArrayList<>();
    }

    /**
     * Displays the current contents of all storage bins in the truck.
     * Empty bins are marked as [Empty].
     */
    public void displayBins(){

        int i, capacity, binNumber;
        double quantity;
        String item;

        System.out.println("--- Storage Bins ---");

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            item = bin.getItemType();
            quantity = bin.getItemQuantity();
            capacity = bin.getCapacity();
            binNumber = i + 1;

            if(item == null)
                System.out.println("Bin #" + binNumber + ": [Empty]");

            else
                System.out.printf("Bin #%d: %s - %.2f / %d\n", binNumber, item, quantity, capacity);
        }
    }

    /**
     * Displays the truck's name, location, storage bin contents,
     * transaction history, and total sales.
     */
    public void displayInfo(){

        int i;

        System.out.println("\nName - " + this.name + " | " + "Location: " + this.location);
        displayBins();
        System.out.println("--- Transactions ---");

        if(getSalesLog().isEmpty())
            System.out.println("No transactions recorded.");

        else{
            for(i = 0; i < getSalesLog().size(); i++){

                String sale = getSalesLog().get(i);
                System.out.println(sale);
            }
        }
        System.out.printf("\nTotal Sales: $%.2f\n", getTotalSales());
    }

    /**
     * Assigns a specific item type and quantity to a given bin number.
     *
     * @param binNumber   the index of the bin (0-based)
     * @param itemType    the type of item to store in the bin
     * @param itemQuantity the quantity of the item to assign
     */
    public void assignItemToBin(int binNumber, String itemType, double itemQuantity){

        bins.get(binNumber).assignItem(itemType, itemQuantity);
    }

    /**
     * Modifies the contents of a specified bin.
     *
     * @param binNumber the 1-based bin number (for display and user selection)
     */
    public void modifyBin(int binNumber){

        int choice, maxCapacity;
        int number = binNumber + 1;
        double quantity;
        boolean invalidQuantity = false;

        System.out.println("\nSetting up Bin #" + number);

        StorageBin bin = bins.get(binNumber);

        do{
            System.out.println("Choose item to store in Bin #" + number + ":");
            System.out.println("1. Small Cup");
            System.out.println("2. Medium Cup");
            System.out.println("3. Large Cup");
            System.out.println("4. Coffee Beans");
            System.out.println("5. Milk");
            System.out.println("6. Water");
            System.out.print("Select item number (0 to skip this bin): ");

            choice = scanner.nextInt();
            scanner.nextLine(); //Consume newline

            if(choice == 0){
                System.out.println("Skipping Bin #" + number);
                break;
            }

            else{
                switch(choice){

                    case 1:
                        bin.setItemType("Small Cup");
                        break;
                    case 2:
                        bin.setItemType("Medium Cup");
                        break;
                    case 3:
                        bin.setItemType("Large Cup");
                        break;
                    case 4:
                        bin.setItemType("Coffee Beans");
                        break;
                    case 5:
                        bin.setItemType("Milk");
                        break;
                    case 6:
                        bin.setItemType("Water");
                        break;
                    default:
                        bin.setItemType(null);
                        break;
                }

                if(bin.getItemType() == null)
                    System.out.println("Invalid selection. Please try again.");

                else{
                    maxCapacity = bin.getCapacityForItem(bin.getItemType());
                    System.out.print("Enter quantity (max " + maxCapacity + "): ");
                    quantity = scanner.nextDouble();
                    scanner.nextLine(); //Consume newline

                    if(quantity < 0 || quantity > maxCapacity){

                        System.out.println("Invalid quantity. Must be between 0 and " + maxCapacity + ".");
                        invalidQuantity = true;
                    }

                    else{
                        assignItemToBin(binNumber, bin.getItemType(), quantity);
                        System.out.println("Bin #" + number + " loaded with " + quantity + " of " + bin.getItemType());
                    }
                }
            }
        } while(bin.getItemType() == null || invalidQuantity);
    }

    /**
     * Fills the first 6 bins of a truck with Small Cups, Medium Cups, Large Cups, Coffee Beans, Milk,
     * and Water respectively.
     * Remaining bins are left empty.
     */
    public void setMaxLoadout(){

        int i;
        double quantity;
        String[] itemTypes = {"Small Cup", "Medium Cup", "Large Cup", "Coffee Beans", "Milk", "Water"};

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);

            if(i < itemTypes.length){

                String item = itemTypes[i];
                quantity = 1.0 * bin.getCapacityForItem(item);
                bin.assignItem(item, quantity); //Assign to bin
            }

            else{
                //Leave last 2 bins empty
                bin.empty();
            }
        }
    }

    /**
     * Allows user to set up the loadout of a truck.
     */
    public void setLoadout(){

        int i, binNumber;
        String max;
        ArrayList<StorageBin> bins = getBins();

        System.out.println("\n--- Setting Loadout for " + name + " ---");

        System.out.println("Set Storage Bins to maximum capacity? (yes/no): ");
        max = scanner.nextLine();

        if(max.equalsIgnoreCase("yes"))
            setMaxLoadout();

        else{
            for(i = 0; i < bins.size(); i++){
                StorageBin bin = bins.get(i);
                binNumber = bin.getBinNumber();

                modifyBin(binNumber);
            }

            System.out.println("\nLoadout for " + name + " complete!\n");
        }
    }

    /**
     * Restocks a specific storage bin.
     *
     * @param binNumber the bin number to restock
     */
    public void restockOneBin(int binNumber){

        double quantity;

        if(binNumber >= 1 && binNumber <= bins.size()){

            StorageBin bin = bins.get(binNumber - 1);
            if(bin.getItemType() == null)
                System.out.println("Bin isn't assigned any item, restock failed");

            else{

                System.out.println("Enter quantity to restock (0 for full restock): ");
                quantity = scanner.nextDouble();

                if(quantity == 0){
                    bins.get(binNumber - 1).fill(); //Fill bin to max capacity
                    System.out.printf("Bin %d restocked to max capacity\n", binNumber);
                }

                else
                    bin.addQuantity(quantity); //Add entered quantity to bin
            }
        }

        else
            System.out.println("Invalid bin number.");
    }

    /**
     * Restocks all bins that have an assigned item.
     *
     */
    public void restockAllBins(){

        int i;
        int counter = 0, flag = 1;

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);

            if(bin.getItemType() == null)
                counter++;
        }

        if(counter >= bins.size())
            System.out.println("Error Restocking. Bins have no items assigned");

        else{
            for(i = 0; i < bins.size(); i++){

                StorageBin bin = bins.get(i);

                if(bin.getItemType() == null)
                    flag = 0;

                else
                    bin.fill(); //Fill bin
            }

            if(flag == 1)
                System.out.println("All bins restocked");
            else
                System.out.println("Some bins have no items assigned yet.");
        }
    }

    /**
     * Allows the user to modify all storage bins.
     * User can choose to set bins to maximum capacity or manually adjust each bin.
     */
    public void modifyAllBins(){

        int i;
        String max;

        System.out.println("Set Storage Bins to maximum capacity? (yes/no): ");
        max = scanner.nextLine();

        if(max.equalsIgnoreCase("yes"))
            setMaxLoadout();

        else{
            for(i = 0; i < bins.size(); i++){
                modifyBin(i);
            }
        }
    }

    /**
     * Empties a specific storage bin.
     *
     * @param binNumber the bin number to empty (1-based index)
     */
    public void emptyOneBin(int binNumber){

        if(binNumber >= 1 && binNumber <= bins.size()){

            bins.get(binNumber - 1).empty();
            System.out.println("Bin #" + binNumber + " emptied.");
        }

        else
            System.out.println("Invalid bin number.");
    }

    /**
     * Empties all storage bins.
     */
    public void emptyAllBins(){

        int i;

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            bin.empty(); //Empty bin
        }
        System.out.println("All bins emptied");
    }

    /**
     * Returns the name of a truck.
     *
     * @return the name of the truck
     */
    public String getName(){

        return name;
    }

    /**
     * Returns the current location of a truck.
     *
     * @return the truck location
     */
    public String getLocation(){

        return location;
    }

    /**
     * Sets a truck's name.
     *
     * @param name the new name for the truck
     */
    public void setName(String name){

        this.name = name;
    }

    /**
     * Sets a truck's location.
     *
     * @param location the new location for the truck
     */
    public void setLocation(String location){

        this.location = location;
    }

    /**
     * Returns the list of storage bins associated with a truck.
     *
     * @return an ArrayList of StorageBin objects
     */
    public ArrayList<StorageBin> getBins(){

        return bins;
    }

    /**
     * Finds and returns the first bin that matches the given item name.
     *
     * @param itemName the name of the item to search for
     * @return the matching StorageBin, or null if not found
     */
    public StorageBin findBin(String itemName){

        int i;
        StorageBin result = null;

        bins = getBins();
        String item;

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            item = bin.getItemType();

            if(item != null && item.equalsIgnoreCase(itemName))
                result = bin;
        }

        return result;
    }

    /**
     * Records a drink sale to a truck's sales log.
     *
     * @param coffeeType the type of coffee sold
     * @param size the size of the drink
     * @param grams the grams of coffee beans used
     * @param milk the ounces of milk used
     * @param water the ounces of water used
     * @param price the price of the drink
     */
    public void recordSale(String coffeeType, String size, double grams, double milk, double water, double price){

        salesLog.add(String.format("Drink: %s (%s) | %.2f g beans, %.2f oz milk, %.2f oz water | $%.2f",
                coffeeType, size, grams, milk, water, price));
    }

    /**
     * Returns the list of recorded drink sales for the truck.
     *
     * @return an ArrayList of sales log entries
     */
    public ArrayList<String> getSalesLog(){

        return salesLog;
    }

    /**
     * Adds a specified amount to the truck's total sales.
     *
     * @param amount the amount to add to total sales
     */
    public void addToTotalSales(double amount){

        totalSales += amount;
    }

    /**
     * Returns the total sales revenue for the truck.
     *
     * @return the total sales amount
     */
    public double getTotalSales(){

        return totalSales;
    }
}