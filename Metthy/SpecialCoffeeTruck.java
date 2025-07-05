package Metthy;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a special coffee truck
 */
public class SpecialCoffeeTruck extends RegularCoffeeTruck {

    /**
     * List of storage bins.
     */
    private final ArrayList<StorageBin> bins = new ArrayList<>(10);
    /**
     * Scanner object for reading user input from the console.
     */
    private final Scanner scanner = new Scanner(System.in);

    public SpecialCoffeeTruck(String name, String location){

        super(name, location);

        int i;

        //Create the 10 storage bins
        for(i = 1; i <= 10; i++)
            bins.add(new StorageBin(i));
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
            System.out.println("4. Sweetener (sucrose)");
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
                        bin.setItemType("Sweetener");
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
}
