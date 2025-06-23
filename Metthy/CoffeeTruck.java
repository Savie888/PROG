package Metthy;

import java.util.ArrayList;
import java.util.Scanner;

public class CoffeeTruck {

    private String name;
    private String location;
    private ArrayList<StorageBin> bins;
    private ArrayList<String> salesLog;
    private double totalSales;
    private Scanner scanner = new Scanner(System.in);

    public CoffeeTruck(String name, String location){

        int i;
        this.name = name;
        this.location = location;
        this.bins = new ArrayList<>(8);

        for(i = 0; i < 8; i++)
            bins.add(new StorageBin(i));

        salesLog = new ArrayList<>();
    }

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

        System.out.printf("\nTotal Sales: $%.2f", getTotalSales());
    }

    public void assignItemToBin(int binNumber, String itemType, double itemQuantity){

        bins.get(binNumber).assignItem(itemType, itemQuantity);
    }

    public void modifyBin(int binNumber){

        int choice;
        System.out.println("\nSetting up Bin #" + binNumber);

        StorageBin bin = bins.get(binNumber - 1);

        do{
            System.out.println("Choose item to store in Bin #" + binNumber + ":");
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
                System.out.println("Skipping Bin #" + binNumber);
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
                    int maxCapacity = bin.getCapacityForItem(bin.getItemType());
                    System.out.print("Enter quantity (max " + maxCapacity + "): ");
                    double quantity = scanner.nextDouble();
                    scanner.nextLine(); //Consume newline

                    if(quantity < 0 || quantity > maxCapacity)
                        System.out.println("Invalid quantity. Must be between 0 and " + maxCapacity + ".");

                    else{
                        //grammar for itemType
                        assignItemToBin(binNumber, bin.getItemType(), quantity);
                        System.out.println("Bin #" + binNumber + " loaded with " + quantity + " of " + bin.getItemType());
                    }
                }
            }
        } while(bin.getItemType() == null);
    }

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

    public void setLoadout(){

        int i;
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
                int binNumber = bin.getBinNumber() + 1;

                modifyBin(binNumber);
            }

            System.out.println("\nLoadout for " + name + " complete!\n");
        }
    }

    public void restockOneBin(int binNumber){

        if(binNumber >= 1 && binNumber <= bins.size()){

            bins.get(binNumber - 1).fill();
            System.out.println("Bin #" + binNumber + " restocked.");
        }

        else if(bins.get(binNumber - 1).getItemType() == null)
            System.out.println("Error, bin isn't assigned any item");

        else
            System.out.println("Invalid bin number.");
    }

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

    public void modifyAllBins(){

        int i;
        String max;

        System.out.println("Set Storage Bins to maximum capacity? (yes/no): ");
        max = scanner.nextLine();

        if(max.equalsIgnoreCase("yes"))
            setMaxLoadout();

        else{
            for(i = 0; i < bins.size(); i++){

                StorageBin bin = bins.get(i);
                modifyBin(i+1);
            }
        }
    }

    public void emptyOneBin(int binNumber){

        if(binNumber >= 1 && binNumber <= bins.size()){

            bins.get(binNumber - 1).empty();
            System.out.println("Bin #" + binNumber + " emptied.");
        }

        else
            System.out.println("Invalid bin number.");
    }

    public void emptyAllBins(){

        int i;

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            bin.empty(); //Empty bin
        }
        System.out.println("All bins empty");

    }

    public String getName(){

        return name;
    }

    public String getLocation(){

        return location;
    }

    public void setName(String name){

        this.name = name;
    }

    public void setLocation(String location){

        this.location = location;
    }

    //Return arraylist containing storage bins
    public ArrayList<StorageBin> getBins(){

        return bins;
    }

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

    public void recordSale(String coffeeType, String size, double grams, double milk, double water, double price){

        salesLog.add(String.format("Drink: %s (%s) | %.2f g beans, %.2f oz milk, %.2f oz water | $%.2f",
                coffeeType, size, grams, milk, water, price));
    }

    public ArrayList<String> getSalesLog(){

        return salesLog;
    }

    public void addToTotalSales(double amount){

        totalSales += amount;
    }

    public double getTotalSales(){

        return totalSales;
    }
}