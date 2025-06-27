package Metthy;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Manages the creation, tracking, and operations of all coffee trucks in the simulation.
 *
 * <p>This class is responsible for:
 * <ul>
 *     <li>Initializing new trucks (regular and special types)</li>
 *     <li>Setting up truck inventory and loadout</li>
 *     <li>Configuring drink prices via a single shared {@code DrinkManager}</li>
 *     <li>Displaying an interactive menu for user commands</li>
 *     <li>Tracking and summarizing sales across all trucks</li>
 * </ul>
 *
 */
public class TruckManager {

    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<CoffeeTruck> trucks = new ArrayList<>(); //ArrayList containing all created trucks
    private final DrinkManager drinkManager = new DrinkManager();
    private boolean pricesInitialized = false; //Flag to check if prices have been initialized

    /**
     * Checks if the given truck name is already taken.
     *
     * @param name The name to check.
     * @return true if the name is taken; false if it's available.
     */
    private boolean checkName(String name) {

        int i;
        boolean flag = false;

        name = name.trim(); //Trim white spaces

        for(i = 0; i < trucks.size() && !flag; i++){

            CoffeeTruck truck = trucks.get(i);

            if(name.equalsIgnoreCase(truck.getName()))
                flag = true; //Set flag to true if the name is already taken
        }

        return flag; //Return true if name is taken, false otherwise
    }

    /**
     * Checks if the given truck location is already taken.
     *
     * @param location The location to check.
     * @return true if the location is taken; false if it's available.
     */
    private boolean checkLocation(String location) {

        int i;
        boolean flag = false;

        location = location.trim();

        for(i = 0; i < trucks.size() && !flag; i++){

            CoffeeTruck truck = trucks.get(i);

            if (location.equalsIgnoreCase(truck.getLocation()))
                flag = true; //Set flag to true if the location is already taken
        }

        return flag; //Return true if location is taken, false otherwise
    }

    /**
     * Allows user to:
     * <ul>
     *  <li>Create a coffee truck by entering a unique name and location.</li>
     *  <li>Set up the truck's storage bins.</li>
     *  <li>Initialize the drink ingredient prices after a successful truck creation.</li>
     * </ul>
     */
    public void createTruck(){

        int flag;
        String repeat = "yes";
        String name, location = "";
        System.out.println("Creating a Coffee Truck...");

        while(repeat.equalsIgnoreCase("yes")){

            flag = 0;
            System.out.print("Enter truck name: ");
            name = scanner.nextLine();

            //Check if name is available
            if(checkName(name)){
                System.out.println("Error. Name is already taken");
                flag = 1;
            }

            if(flag == 0){
                System.out.print("Enter truck location: ");
                location = scanner.nextLine();

                //Check if location is available
                if(checkLocation(location)){
                    System.out.println("Error. Location is already taken");
                    flag = 1;
                }
            }

            if(flag == 1){
                System.out.print("Try Again? (yes/no): ");
                repeat = scanner.nextLine();
            }

            else{
                //Create new Coffee Truck
                CoffeeTruck truck = new CoffeeTruck(name, location);

                //Option to set up storage bins
                System.out.println("Set up storage bins?: (yes/no)");
                String loadout = scanner.nextLine();

                if(loadout.equalsIgnoreCase("yes"))
                    truck.setLoadout(); //Set up storage bins

                //Add new truck to arraylist of trucks
                trucks.add(truck);

                System.out.println("Coffee Truck Created\n");
                System.out.print("Create another truck? (yes/no): ");
                repeat = scanner.nextLine();
            }
        }

        //After all trucks are created, set up DrinkManager and prices
        if(!trucks.isEmpty() && !pricesInitialized){
            drinkManager.setIngredientPrices();
            pricesInitialized = true; //Set flag to true to avoid repeating price initialization
        }
    }

    /**
     * Displays a list of available trucks and allows the user to select one.
     *
     * @return The index of the selected truck in the list; -1 if no trucks are available.
     */
    public int selectTruck(){

        int i, truckIndex;
        String repeat = "";

        do{
            if(trucks.isEmpty()){
                System.out.println("No trucks available");
                truckIndex = -1; //Assign index to -1 if no trucks available
            }

            else{
                System.out.println("--- List of Trucks ---");

                for(i = 0; i < trucks.size(); i++){

                    //Display list of available trucks
                    CoffeeTruck truck = trucks.get(i);
                    System.out.printf("Truck %d: Name - %s  Location - %s\n", i + 1, truck.getName(), truck.getLocation());
                }

                System.out.println("Select truck number: ");
                truckIndex = scanner.nextInt();
                scanner.nextLine(); //Consume leftover newline
                truckIndex -= 1;

                if(truckIndex < 0 || truckIndex >= trucks.size()){
                    System.out.println("Invalid truck number selected. Try Again? (yes/no): ");
                    repeat = scanner.nextLine();
                }
            }
        }while(repeat.equalsIgnoreCase("yes"));

        return truckIndex;
    }

    /**
     * Removes a coffee truck selected by the user.
     *
     */
    public void removeTruck(){

        int truckIndex = selectTruck();

        if(truckIndex != -1){

            trucks.remove(truckIndex);
            System.out.println("Truck removed");
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
     * @param truck The {@code CoffeeTruck} to perform maintenance on
     */
    private void truckMaintenanceMenu(CoffeeTruck truck){

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
                        truck.restockAllBins(); //Restock all bins

                    else if(restock == 2){
                        System.out.println("Enter Bin Number: ");
                        binNumber = scanner.nextInt();
                        truck.restockOneBin(binNumber); //Restock selected bin
                    }
                    break;
                case 2:
                    System.out.println("1 - Modify all bins");
                    System.out.println("2 - Modify one bin");
                    System.out.println("Select an option: ");
                    int modify = scanner.nextInt();

                    if(modify == 1)
                        truck.modifyAllBins(); //Modify all bins

                    else if(modify == 2){
                        System.out.println("Enter bin number to modify: ");
                        int binNum = scanner.nextInt();
                        truck.modifyBin(binNum); //Modify selected bin
                    }
                    break;
                case 3:
                    System.out.println("1 - Empty all bins");
                    System.out.println("2 - Empty one bin");
                    System.out.println("Select an option: ");
                    int empty = scanner.nextInt();

                    if(empty == 1)
                        truck.emptyAllBins(); //Empty all bins

                    else if(empty == 2){
                        System.out.println("Enter Bin Number: ");
                        binNumber = scanner.nextInt();
                        truck.emptyOneBin(binNumber); //Empty selected bin
                    }
                    break;
                case 4:
                    System.out.println("Enter new name: ");
                    String name = scanner.nextLine();
                    truck.setName(name); //Set new name
                    break;
                case 5:
                    System.out.println("Enter new location: ");
                    String location = scanner.nextLine();
                    truck.setLocation(location); //Set new location
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

    /**
     * Displays the truck simulation menu for performing actions on a selected coffee truck.
     * <p>Allows user to:</p>
     * <ul>
     *   <li>Prepare coffee drinks using the truck's inventory</li>
     *   <li>View the selected truck's information</li>
     *   <li>Enter the truck maintenance submenu</li>
     *   <li>Exit to the main menu</li>
     * </ul>
     */
    public void simulateMenu(){

        int index, option;

        index = selectTruck(); //Let user select a truck

        do{
            System.out.println("\n=== Simulation Menu ===");
            System.out.println("1 - Prepare Coffee Drinks");
            System.out.println("2 - View Truck Information");
            System.out.println("3 - Truck Maintenance");
            System.out.println("4 - Exit to Main Menu");
            System.out.println("Select an Option: ");
            option = scanner.nextInt();
            scanner.nextLine(); //Absorb new line

            switch(option){

                case 1:
                    drinkManager.coffeeMenu(trucks.get(index)); //Display Coffee Menu
                    break;
                case 2:
                    trucks.get(index).displayInfo(); //Display truck information
                    break;
                case 3:
                    truckMaintenanceMenu(trucks.get(index)); //Display truck maintenance menu
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        } while(option != 4);
    }

    /**
     * Displays the total number of deployed trucks and their respective types (Regular or Special).
     * Currently, only shows regular trucks; special truck count is planned for future expansion.
     *
     * @param trucks the list of all deployed coffee trucks
     */
    private void displayTruckDeployment(ArrayList<CoffeeTruck> trucks){

        System.out.println("\n--- Truck Deployment ---");
        System.out.println("Total Trucks     : " + trucks.size()); //Display total number of trucks
        System.out.println("Regular Trucks   : " + trucks.size()); //Display total number of regular trucks
        //System.out.println("Special Trucks   : " + totalSpecial); For MCO2
    }

    /**
     * Aggregates and displays the total inventory across all deployed trucks.
     * Includes coffee beans (grams), milk (oz), water (oz), and cup counts by size.
     *
     * @param trucks the list of all deployed coffee trucks
     */
    private void displayTotalInventory(ArrayList<CoffeeTruck> trucks){

        int i, j;
        int totalSmallCups = 0, totalMediumCups = 0, totalLargeCups = 0;
        double totalCoffeeGrams = 0, totalMilkOz = 0, totalWaterOz = 0, quantity;
        String item;
        ArrayList<StorageBin> bins;

        for(i = 0; i < trucks.size(); i++) {

            CoffeeTruck truck = trucks.get(i);
            bins = truck.getBins();

            for(j = 0; j < bins.size(); j++){

                StorageBin bin = bins.get(j);

                if(bin == null || bin.getItemType() == null)
                    continue; //Skip if bin is empty

                item = bin.getItemType();
                quantity = bin.getItemQuantity();

                //Calculate total inventory
                switch(item){

                    case "Coffee Beans":
                        totalCoffeeGrams += quantity;
                        break;

                    case "Milk":
                        totalMilkOz += quantity;
                        break;

                    case "Water":
                        totalWaterOz += quantity;
                        break;

                    case "Small Cup":
                        totalSmallCups += (int) quantity;
                        break;

                    case "Medium Cup":
                        totalMediumCups += (int) quantity;
                        break;

                    case "Large Cup":
                        totalLargeCups += (int) quantity;
                        break;
                }
            }
        }

        //Display inventory
        System.out.println("\n--- Aggregate Inventory ---");
        System.out.printf("Coffee Beans     : %.2f g\n", totalCoffeeGrams);
        System.out.printf("Milk             : %.2f oz\n", totalMilkOz);
        System.out.printf("Water            : %.2f oz\n", totalWaterOz);
        System.out.println("Small Cups       : " + totalSmallCups);
        System.out.println("Medium Cups      : " + totalMediumCups);
        System.out.println("Large Cups       : " + totalLargeCups);
    }

    /**
     * Displays the sales log and total revenue across all deployed trucks.
     * Also displays the individual sales log and revenue of each truck.
     *
     * @param trucks the list of all deployed coffee trucks
     */
    private void displayTotalSales(ArrayList<CoffeeTruck> trucks){

        int i, j;
        double combinedSales = 0;

        System.out.println("\n--- Sales Summary ---");

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);
            combinedSales += truck.getTotalSales(); //Compute combined sales across all trucks
            ArrayList<String> log = truck.getSalesLog();

            for(j = 0; j < log.size(); j++)
                System.out.println("[" + truck.getName() + "] " + log.get(j));

            //Display total sales of a truck
            System.out.printf("Total for [%s]   : $%.2f\n", truck.getName(), truck.getTotalSales());
        }

        //Display combined total sales of all truck
        System.out.printf("\nTotal Sales    : $%.2f\n", combinedSales);
    }

    /**
     * Displays the full dashboard summary of the Coffee Truck Business.
     * Includes truck deployment stats, total inventory, and a full sales report.
     */
    public void displayDashboard(){

        ArrayList<CoffeeTruck> trucks = getTrucks();

        if(trucks.isEmpty())
            System.out.println("Create Trucks First!!!");

        else{
            System.out.println("\n===== Coffee Truck Dashboard =====");

            //Display Truck Deployment
            displayTruckDeployment(trucks);

            //Display Aggregate Inventory
            displayTotalInventory(trucks);

            //Display Total Sales
            displayTotalSales(trucks);
            System.out.println("==============================");
        }
    }

    /**
     * Returns the list of all deployed coffee trucks.
     *
     * @return the list of deployed {@code CoffeeTruck} instances
     */
    private ArrayList<CoffeeTruck> getTrucks(){

        return trucks;
    }
}

