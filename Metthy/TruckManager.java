package Metthy;

import java.util.Scanner;
import java.util.ArrayList;

public class TruckManager {

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<CoffeeTruck> trucks = new ArrayList<>(); //ArrayList containing all created trucks
    private ArrayList<StorageBin> bins = new ArrayList<>();
    private DrinkManager drinkManager = new DrinkManager();
    boolean pricesSet = false;

    public void createTruck() {

        int flag;
        String repeat = "yes";
        String name, location = "";
        System.out.println("Creating a Coffee Truck...");

        while(repeat.toLowerCase().equals("yes")){

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
                setInitialLoadout(truck);
                trucks.add(truck);

                System.out.println("Coffee Truck Created");
                System.out.print("Create another truck? (yes/no): ");
                repeat = scanner.nextLine();
            }
        }

        //After all trucks are created, set up DrinkManager and prices
        if(trucks.size() > 0 && !pricesSet){
            drinkManager.setPrices();
            pricesSet = true;
        }

        else{
            System.out.println("Update Prices?: ");
            String updatePrices = scanner.nextLine();

            if(updatePrices.equalsIgnoreCase("yes"))
                drinkManager.setPrices();
        }

        drinkManager.displayDrinkMenu();
    }

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

    public void setInitialLoadout(CoffeeTruck truck) {

        int binIndex = 0, maxCapacity, quantity = 0;
        String itemType = "";

        System.out.println("\n--- Initial Loadout for " + truck.getName() + " ---");

        bins = truck.getBins();

        while (binIndex < bins.size()) {

            StorageBin bin = bins.get(binIndex);
            int binNumber = bin.getBinNumber() + 1;
            System.out.println("\nSetting up Bin #" + binNumber);

            System.out.println("Choose item to store in Bin #" + binNumber + ":");
            System.out.println("1. Small Cup");
            System.out.println("2. Medium Cup");
            System.out.println("3. Large Cup");
            System.out.println("4. Coffee Beans");
            System.out.println("5. Milk");
            System.out.println("6. Water");
            System.out.print("Select item number (0 to skip this bin): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); //Consume newline

            if (choice == 0) {
                System.out.println("Skipping Bin #" + binNumber);
                binIndex++;
                continue;
            }

            switch (choice) {

                case 1:
                    itemType = "Small Cup";
                    break;
                case 2:
                    itemType = "Medium Cup";
                    break;
                case 3:
                    itemType = "Large Cup";
                    break;
                case 4:
                    itemType = "Coffee Beans";
                    break;
                case 5:
                    itemType = "Milk";
                    break;
                case 6:
                    itemType = "Water";
                    break;
                default:
                    itemType = null;
                    break;
            }

            if(itemType == null){
                System.out.println("Invalid selection. Please try again.");
                //Don't increment binIndex to allow retry
            }

            else{
                maxCapacity = bin.getCapacityForItem(itemType);
                System.out.print("Enter quantity (max " + maxCapacity + "): ");
                quantity = scanner.nextInt();
                scanner.nextLine(); //Consume newline

                if (quantity < 0 || quantity > maxCapacity)
                    System.out.println("Invalid quantity. Must be between 0 and " + maxCapacity + ".");

                else {
                    //grammar for itemType
                    truck.assignItemToBin(binIndex, itemType, quantity);
                    System.out.println("Bin #" + binNumber + " loaded with " + quantity + " of " + itemType);
                    binIndex++;
                }
            }
        }

        System.out.println("\nInitial Loadout for " + truck.getName() + " complete!\n");
    }


    public int simulateTruck(){

        int i, index = 0;
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

                System.out.println("Select truck number to simulate: ");
                index = scanner.nextInt();

                if(index < 1 || index > trucks.size()){
                    System.out.println("Invalid truck number selected. Try Again? (yes/no): ");
                    repeat = scanner.nextLine();
                }
            }
        }while(repeat.equalsIgnoreCase("yes"));

        return index;
    }

    public double getEspressoGrams(String size){

        double espressoGrams = 0;

        switch(size){

            case "Small":
                espressoGrams = 10;
                break;
            case "Medium":
                espressoGrams = 15;
                break;
            case "Large":
                espressoGrams = 20;
                break;
            default:
                System.out.println("Invalid Input. Cancelling.\n");
                break;
        }

        return espressoGrams;
    }

    public void prepareDrink(CoffeeTruck truck){

        String coffeeType, coffeeSize;
        double espressoGrams;

        System.out.println("\n--- Prepare Coffee Drink ---");
        coffeeType = getDrinkType();
        coffeeSize = getDrinkSize();

        if(coffeeType == null || coffeeSize == null){
            System.out.println("Invalid input! Drink preparation cancelled");
        }

        else{
            // Example: You’d calculate required ingredients here
            System.out.printf("Preparing %s (%s)...\n", coffeeType, coffeeSize);

            // Placeholder: Deduct ingredients, compute cost, update sales
            System.out.println("Drink prepared successfully!\n");
        }
    }

    private String getDrinkType(){

        int option;
        String coffeeType = "", repeat = "";

        do{
            System.out.println("Select drink type:");
            System.out.println("1 - Café Americano");
            System.out.println("2 - Latte");
            System.out.println("3 - Cappuccino");
            System.out.print("Enter choice: ");
            option = scanner.nextInt();
            scanner.nextLine();  //flush

            switch(option){

                case 1:
                    coffeeType = "Café Americano";
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

    public void simulateMenu(){

        int index, option;

        index = simulateTruck();

        do{
            System.out.println("=== Simulation Menu ===");
            System.out.println("1 - Prepare Coffee Drinks");
            System.out.println("2 - View Truck Information");
            System.out.println("3 - Bin Maintenance");
            System.out.println("4 - Exit to Main Menu");
            System.out.println("Select an Option: ");
            option = scanner.nextInt();

            switch(option){

                case 1:
                    prepareDrink(trucks.get(index));
                    break;
                case 2:
                    // viewTruckInformation(selectedTruck);
                    break;
                case 3:
                    // performMaintenance(selectedTruck);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } while(option != 4);
    }
}
/*
    public void binSetupMenu(CoffeeTruck truck){

        int binNumber, quantity, choice;
        boolean flag = false;
        String itemName;

        do{

            System.out.println("\n--- Bin Setup for " + truck.getName() + " ---");
            //truck.displayStorageBins();
            System.out.println("Options:");
            System.out.println("1. Assign item to bin");
            System.out.println("2. Clear bin");
            System.out.println("3. Exit setup");

            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch(choice){

                case 1:

                    do{
                        System.out.print("Enter bin number (1-8): ");
                        binNumber = scanner.nextInt();
                        scanner.nextLine(); //consume newline

                        System.out.print("Enter item name: ");
                        itemName = scanner.nextLine();

                        System.out.print("Enter quantity: ");
                        quantity = scanner.nextInt();
                        scanner.nextLine(); //consume newline

                        flag = truck.assignItemToBin(binNumber, itemName, quantity);
                    } while(!flag);
                    break;

                case 2:
                    System.out.print("Enter bin number to clear: ");
                    binNumber = scanner.nextInt();
                    scanner.nextLine(); //consume newline

                    truck.emptyBin(binNumber);
                    break;

                case 3:
                    System.out.println("Exiting Bin Setup...");
                    flag = true;
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while(flag); //Repeat until flag is true
    }
}
*/

