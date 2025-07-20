package Metthy.View;

import Metthy.Model.BinContent;
import Metthy.Model.CoffeeTruck;
import Metthy.Model.RegularCoffeeTruck;
import Metthy.Model.SpecialCoffeeTruck;
import Metthy.StorageBin;

import java.util.ArrayList;

public class TruckView extends View{

    private final ArrayList<StorageBin> bins;

    public TruckView(ArrayList<StorageBin> bins){

        super();

        this.bins = bins;
    }

    //PROMPTS AND DISPLAYS
    /**
     * Prompts user to enter a unique truck name
     *
     * @return the unique name entered
     */
    public String enterUniqueName(){

        String name;

        System.out.print("Enter truck name: ");
        name = scanner.nextLine();

        return name;
    }

    /**
     * Prompts user to enter a unique truck location
     *
     * @return the unique location entered
     */
    public String enterUniqueLocation(){

        String location;

        System.out.print("Enter truck location: ");
        location = scanner.nextLine();

        return location;
    }

    public int enterTruckType(){

        int option;

        System.out.println("Choose Truck Type:");
        System.out.println("1. Regular Coffee Truck");
        System.out.println("2. Special Coffee Truck");
        option = scanner.nextInt();
        scanner.nextLine(); //Clear excess line

        return option;
    }

    public String showLoadoutPrompt(){

        String set;

        //Option to set up storage bins
        System.out.println("Set up storage bins?: (yes/no)");
        set = yesOrNo();

        return set;
    }

    public void showBinPrompt(int binNumber){

        System.out.println("\nSetting up Bin #" + binNumber);
    }

    public void showBinSkipPrompt(int binNumber){

        System.out.println("Skipping Bin #" + binNumber);
    }

    public void showBinLoadedMessage(int binNumber, BinContent content, double quantity){

        String itemType = content.getName();
        String unit;
        String formattedQuantity;

        switch (itemType.toLowerCase()) {
            case "water":
            case "milk":
                unit = "ounces of " + itemType;
                formattedQuantity = String.format("%.1f", quantity);
                break;

            case "coffee beans":
                unit = "grams of " + itemType;
                formattedQuantity = String.format("%.1f", quantity);
                break;

            default:
                unit = "cups";
                formattedQuantity = String.format("%d", (int) quantity);
                break;
        }

        System.out.printf("Bin #%d loaded with %s %s%n", binNumber, formattedQuantity, unit);
    }

    public String showDefaultLoadoutPrompt(CoffeeTruck truck){

        String choice;

        System.out.println("\n--- Setting Loadout for " + truck.getName() + " ---");
        System.out.print("Set Storage Bins to default loadout? (yes/no): ");
        choice = yesOrNo();

        return choice;
    }

    public void showLoadoutComplete(CoffeeTruck truck){

        System.out.println("\nLoadout for " + truck.getName() + " complete!\n");
    }

    public String repeatTruckCreationPrompt(){

        String repeat;

        System.out.println("Coffee Truck Created\n");

        System.out.print("Create another truck? (yes/no): ");
        repeat = yesOrNo();

        return repeat;
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

            //Bin is empty if no item assigned
            if(item == null)
                System.out.println("Bin #" + binNumber + ": [Empty]");

            else
                System.out.printf("Bin #%d: %s - %.2f / %d\n", binNumber, item, quantity, capacity);
        }
    }

    /**
     * Displays the truck's name, location, storage bin contents,transaction history, and total sales.
     */
    public void displayInfo(CoffeeTruck selectedTruck){

        int i;
        String truckType;

        if(selectedTruck instanceof SpecialCoffeeTruck)
            truckType = "Special Truck";
        else
            truckType = "Regular Truck";

        System.out.println("\n[" + truckType + "]");
        System.out.println("Name - " + selectedTruck.getName() + " | " + "Location: " + selectedTruck.getLocation());
        displayBins();
        System.out.println("--- Transactions ---");

        if(selectedTruck.getSalesLog().isEmpty())
            System.out.println("No transactions recorded.");

        else{
            for(i = 0; i < selectedTruck.getSalesLog().size(); i++){

                String sale = selectedTruck.getSalesLog().get(i);
                System.out.println(sale);
            }
        }
        System.out.printf("\nTotal Sales: $%.2f\n", selectedTruck.getTotalSales());
    }

    public void displayTruckDeployment(int regularCount, int specialCount, int total){

        System.out.println("\n--- Truck Deployment ---");
        System.out.println("Regular Trucks   : " + regularCount); //Display total number of regular trucks
        System.out.println("Special Trucks   : " + specialCount); //Display total number of regular trucks
        System.out.println("------------------------");
        System.out.println("Total Trucks     : " + total); //Display total number of trucks

    }

    //SELECTS

    public int selectIngredientToStore(int binNumber){

        int choice;

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

        return choice;
    }

    public double selectIngredientQuantity(double maxCapacity){

        double quantity;

        System.out.print("Enter quantity (max " + maxCapacity + "): ");
        quantity = scanner.nextDouble();
        scanner.nextLine(); //Consume newline

        return quantity;
    }

    public int selectTruck(ArrayList<CoffeeTruck> trucks) {

        int i, truckIndex;

        System.out.println("--- List of Trucks ---");

        for (i = 0; i < trucks.size(); i++) {
            //Display list of available trucks
            CoffeeTruck truck = trucks.get(i);
            System.out.printf("Truck %d: Name - %s  Location - %s\n", i + 1, truck.getName(), truck.getLocation());
        }

        System.out.println("Select truck number: ");
        truckIndex = scanner.nextInt();
        scanner.nextLine(); //Consume leftover newline

        return truckIndex;
    }

    public int selectBin(){

        int binNumber;

        System.out.println("Enter Bin Number: ");
        binNumber = scanner.nextInt();
        scanner.nextLine(); //Clear excess line

        return binNumber;
    }

    //CHECKS

    public boolean checkIngredientChoice(int option){

        boolean flag = false;

        if(option < 0 || option > 6){
            printInvalidOption();
            flag = true;
        }

        return flag;
    }

    public boolean checkIngredientQuantity(double quantity, int maxCapacity){

        boolean flag = false;

        if(quantity < 0 || quantity > maxCapacity){
            printInvalidOption();
            flag = true;
        }

        return flag;
    }

    public boolean checkBinNumber(int binNumber){

        boolean valid = false;

        if(binNumber < 0 || binNumber > bins.size()){
            System.out.println("Invalid bin number selected");
            valid = true;
        }

        return valid;
    }

}
