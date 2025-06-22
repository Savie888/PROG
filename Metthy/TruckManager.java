package Metthys_Folder;

import java.util.Scanner;
import java.util.ArrayList;

public class TruckManager {

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<CoffeeTruck> trucks = new ArrayList<>(); //ArrayList containing all created trucks

    private ArrayList<StorageBin> bins;

    public void createTruck() {

        int flag;
        String repeat = "yes";
        String name, location = "";
        System.out.println("Creating a Coffee Truck...");

        while (repeat.toLowerCase().equals("yes")) {
            flag = 0;
            System.out.print("Enter truck name: ");
            name = scanner.nextLine();

            if (!checkName(name, trucks)) {
                System.out.println("Error. Name is already taken");
                flag = 1;
            }

            if (flag == 0) {
                System.out.print("Enter truck location: ");
                location = scanner.nextLine();

                if (!checkLocation(location, trucks)) {
                    System.out.println("Error. Location is already taken");
                    flag = 1;
                }
            }

            if (flag == 1){
                System.out.println("Try Again? (yes/no): ");
                repeat = scanner.nextLine();
            }

            else{
                CoffeeTruck truck = new CoffeeTruck(name, location); //Create new Truck
                setInitialLoadout(truck);
                trucks.add(truck);
                System.out.println("Coffee Truck Created");
                truck.displayInfo();
                repeat = "";
            }
        }
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

            if (itemType == null){

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

                    truck.assignItemToBin(binIndex, itemType, quantity);
                    System.out.println("Bin #" + binNumber + " loaded with " + quantity + " of " + itemType);
                    binIndex++;
                }
            }
        }

        System.out.println("\nInitial Loadout for " + truck.getName() + " complete!\n");
    }

    public void setInitialPrices(){


    }
    public void displayDashboard(){

        int i, j;

        if(trucks.isEmpty())
            System.out.println("\nNo trucks available.");

        else{
            System.out.println("\n===== Coffee Truck Dashboard =====");

            for(i = 0; i < trucks.size(); i++){

                CoffeeTruck truck = trucks.get(i);
                System.out.println("\nTruck: " + truck.getName());

                ArrayList<StorageBin> bins = truck.getBins();

                for(j = 0; j < bins.size(); j++){

                    StorageBin bin = bins.get(j);
                    String item = bin.getItemType();

                    if(item == null)
                        item = "Empty";

                    int quantity = bin.getQuantity();
                    int capacity = bin.getCapacityForItem(item);

                    System.out.printf("Bin #%d | Item: %-12s | Qty: %3d / %3d%n",
                            bin.getBinNumber() + 1, item, quantity, capacity);
                }
            }

        }
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