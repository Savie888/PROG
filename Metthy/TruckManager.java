package Metthy;

import java.util.Scanner;
import java.util.ArrayList;

public class TruckManager {

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<CoffeeTruck> trucks = new ArrayList<>(); //ArrayList containing all created trucks
    private ArrayList<StorageBin> bins = new ArrayList<>();
    private DrinkManager drinkManager = new DrinkManager(trucks, bins);
    private boolean pricesInitialized = false;

    public boolean checkName(String name, ArrayList<CoffeeTruck> trucks) {

        int i, flag = 0;
        name = name.toLowerCase();

        for (i = 0; i < trucks.size() && flag == 0; i++) {

            CoffeeTruck truck = trucks.get(i);

            if (name.equals(truck.getName().toLowerCase()))
                flag = 1; //Set flag to 1 if the name is already taken
        }

        return flag == 0; //If flag is 0, then name is still available
    }

    public boolean checkLocation(String location, ArrayList<CoffeeTruck> trucks) {

        int i, flag = 0;
        location = location.toLowerCase();

        for (i = 0; i < trucks.size() && flag == 0; i++) {

            CoffeeTruck truck = trucks.get(i);

            if (location.equals(truck.getLocation().toLowerCase()))
                flag = 1; //Set flag to 1 if the location is already taken
        }

        return flag == 0; //If flag is 0, then location is still available
    }

    public void createTruck(){

        int flag;
        String repeat = "yes";
        String name, location = "";
        System.out.println("Creating a Coffee Truck...");

        while(repeat.equalsIgnoreCase("yes")){

            flag = 0;
            System.out.print("Enter truck name: ");
            name = scanner.nextLine();

            if(!checkName(name, trucks)){
                System.out.println("Error. Name is already taken");
                flag = 1;
            }

            if(flag == 0){
                System.out.print("Enter truck location: ");
                location = scanner.nextLine();

                if(!checkLocation(location, trucks)){
                    System.out.println("Error. Location is already taken");
                    flag = 1;
                }
            }

            if(flag == 1){
                System.out.print("Try Again? (yes/no): ");
                repeat = scanner.nextLine();
            }

            else{
                CoffeeTruck truck = new CoffeeTruck(name, location);

                System.out.println("Set up storage bins?: (yes/no)");
                String loadout = scanner.nextLine();

                if(loadout.equalsIgnoreCase("yes"))
                    truck.setLoadout(); //Set up storage bins

                trucks.add(truck);

                System.out.println("Coffee Truck Created\n");
                System.out.print("Create another truck? (yes/no): ");
                repeat = scanner.nextLine();
            }
        }

        //After all trucks are created, set up DrinkManager and prices
        if(!trucks.isEmpty() && !pricesInitialized){
            drinkManager.setIngredientPrices();
            pricesInitialized = true;
        }
    }

    public int selectTruck(){

        int i, index;
        String repeat = "";

        do{
            if(trucks.isEmpty()){
                System.out.println("No trucks available");
                index = -1;
            }

            else{
                System.out.println("--- List of Trucks ---");

                for(i = 0; i < trucks.size(); i++){

                    CoffeeTruck truck = trucks.get(i);
                    System.out.printf("Truck %d: Name - %s  Location - %s\n", i + 1, truck.getName(), truck.getLocation());
                }

                System.out.println("Select truck number: ");
                index = scanner.nextInt();
                index -= 1;

                if(index < 0 || index > trucks.size()){
                    System.out.println("Invalid truck number selected. Try Again? (yes/no): ");
                    repeat = scanner.nextLine();
                }
            }
        }while(repeat.equalsIgnoreCase("yes"));

        return index;
    }

    public void truckMaintenanceMenu(CoffeeTruck truck){

        int option;

        do{
            System.out.println("\n=== Truck Maintenance ===");
            System.out.println("1 - Restock Bins (only works if bin is assigned an item already)");
            System.out.println("2 - Modify Storage Bin Contents");
            System.out.println("3 - Empty Storage Bins");
            System.out.println("4 - Edit Truck Name"); //works
            System.out.println("5 - Edit Truck Location"); //works
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
                    int restock  = scanner.nextInt();

                    if(restock == 1)
                        truck.restockAllBins(); //Restock all bins

                    else if(restock == 2){
                        System.out.println("Enter Bin Number: ");
                        int binNumber = scanner.nextInt();
                        truck.restockOneBin(binNumber); //Restock bin
                    }
                    break;
                case 2:
                    System.out.println("1 - Modify all bins");
                    System.out.println("2 - Modify one bin");
                    System.out.println("Select an option: ");
                    int modify = scanner.nextInt();

                    if(modify == 1)
                        truck.modifyAllBins();

                    else if(modify == 2){
                        System.out.println("Enter bin number to modify: ");
                        int binNum = scanner.nextInt();
                        truck.modifyBin(binNum);
                    }
                    break;
                case 3:
                    System.out.println("1 - Empty all bins");
                    System.out.println("2 - Empty one bin");
                    System.out.println("Select an option: ");
                    int empty = scanner.nextInt();

                    if(empty == 1)
                        truck.emptyAllBins(); //Restock all bins

                    else if(empty == 2){
                        System.out.println("Enter Bin Number: ");
                        int binNumber = scanner.nextInt();
                        truck.emptyOneBin(binNumber); //Restock bin
                    }
                    break;
                case 4:
                    System.out.println("Enter new name: ");
                    String name = scanner.nextLine();
                    truck.setName(name);
                    break;
                case 5:
                    System.out.println("Enter new location: ");
                    String location = scanner.nextLine();
                    truck.setLocation(location);
                    break;
                case 6:
                    drinkManager.setIngredientPrices();
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

    public void simulateMenu(){

        int index, option;

        index = selectTruck();

        do{
            System.out.println("\n=== Simulation Menu ===");
            System.out.println("1 - Prepare Coffee Drinks");
            System.out.println("2 - View Truck Information");
            System.out.println("3 - Truck Maintenance");
            System.out.println("4 - Exit to Main Menu");
            System.out.println("Select an Option: ");
            option = scanner.nextInt();

            switch(option){

                case 1:
                    drinkManager.coffeeMenu(trucks.get(index));
                    break;
                case 2:
                    trucks.get(index).displayInfo();
                    break;
                case 3:
                    truckMaintenanceMenu(trucks.get(index));
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

    public void displayTruckDeployment(ArrayList<CoffeeTruck> trucks){

        System.out.println("\n--- Truck Deployment ---");
        System.out.println("Total Trucks     : " + trucks.size());
        System.out.println("Regular Trucks   : " + trucks.size());
        //System.out.println("Special Trucks   : " + totalSpecial); MCO2
    }

    public void displayTotalInventory(ArrayList<CoffeeTruck> trucks){

        int i, j;
        int totalSmallCups = 0;
        int totalMediumCups = 0;
        int totalLargeCups = 0;
        double totalCoffeeGrams = 0;
        double totalMilkOz = 0;
        double totalWaterOz = 0;
        ArrayList<StorageBin> bins;

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);
            bins = truck.getBins();

            for(j = 0; j < bins.size(); j++){

                StorageBin bin = bins.get(j);

                if(bin == null || bin.getItemType() == null)
                    continue; //Skip if bin is empty

                String item = bin.getItemType();
                double qty = bin.getItemQuantity();

                if(item.equals("Coffee Beans"))
                    totalCoffeeGrams += qty;

                else if (item.equals("Milk"))
                    totalMilkOz += qty;

                else if (item.equals("Water"))
                    totalWaterOz += qty;

                else if (item.equals("Small Cup"))
                    totalSmallCups += (int)qty;

                else if (item.equals("Medium Cup"))
                    totalMediumCups += (int)qty;

                else if (item.equals("Large Cup"))
                    totalLargeCups += (int)qty;
            }
        }

        System.out.println("\n--- Aggregate Inventory ---");
        System.out.printf("Coffee Beans     : %.2f g\n", totalCoffeeGrams);
        System.out.printf("Milk             : %.2f oz\n", totalMilkOz);
        System.out.printf("Water            : %.2f oz\n", totalWaterOz);
        System.out.println("Small Cups       : " + totalSmallCups);
        System.out.println("Medium Cups      : " + totalMediumCups);
        System.out.println("Large Cups       : " + totalLargeCups);
    }

    public void displayTotalSales(ArrayList<CoffeeTruck> trucks){

        int i, j;
        double combinedSales = 0;

        System.out.println("\n--- Sales Summary ---");

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);
            combinedSales += truck.getTotalSales(); //Compute combined sales across all trucks
            ArrayList<String> log = truck.getSalesLog();

            for(j = 0; j < log.size(); j++)
                System.out.println("[" + truck.getName() + "] " + log.get(j));
        }

        System.out.printf("Total Revenue    : $%.2f\n", combinedSales);
    }

    //Display dashboard
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

    public ArrayList<CoffeeTruck> getTrucks(){

        return trucks;
    }
}

