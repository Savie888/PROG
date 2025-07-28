package Metthy.View;

import Metthy.Controller.TruckController;
import Metthy.Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//OBSOLETE

public class TruckView extends View{

    private TruckController truckController;
    private MenuView menuView;
    private JPanel panelContainer;
    private CardLayout cardLayout;


    public TruckView(TruckController truckController, MenuView menuView){

        super();
        this.truckController = truckController;
        this.menuView = menuView;
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

    public void showBinSetupPrompt(int binNumber){

        System.out.println("\nSetting up Bin #" + binNumber);
    }

    public void showBinSkipPrompt(int binNumber){

        System.out.println("Skipping Bin #" + binNumber);
    }

    public void showBinLoadedMessage(int binNumber, BinContent content, double quantity){

        String itemType = content.getName();
        String unit;
        String formattedQuantity;

        if(itemType.equalsIgnoreCase("Water") || itemType.equalsIgnoreCase("Milk")
                || content instanceof Syrup){
            unit = "ounces of " + itemType;
            formattedQuantity = String.format("%.1f", quantity);
        }

        else if(itemType.equalsIgnoreCase("Coffee Bean")){
            unit = "grams of " + itemType;
            formattedQuantity = String.format("%.1f", quantity);
        }

        else{
            unit = "cups";
            formattedQuantity = String.format("%d", (int) quantity);
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

    public void restockFailMessage(){

        System.out.println("Bin isn't assigned any item, restock failed");
    }

    public void binEmptiedMessage(int binNumber){

        System.out.println("Bin #" + binNumber + " emptied.");
    }

    public String repeatTruckCreationPrompt(){

        String repeat;

        System.out.println("Coffee Truck Created\n");

        System.out.print("Create another truck? (yes/no): ");
        repeat = yesOrNo();

        return repeat;
    }

    public void salesSummaryHeader(){
        System.out.println("\n--- Sales Summary ---");
    }

    /**
     * Displays the current contents of all storage bins in the truck.
     * Empty bins are marked as [Empty].
     */
    public void displayBins(ArrayList<StorageBin> bins){

        int i, capacity, binNumber;
        double quantity;
        String item;

        System.out.println("--- Storage Bins ---");

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            BinContent content = bin.getContent();
            binNumber = bin.getBinNumber();

            //Bin is empty if no item assigned
            if(content == null)
                System.out.println("Bin #" + binNumber + ": [Empty]");

            else{
                item = content.getName();
                quantity = content.getQuantity();
                capacity = content.getCapacity();
                System.out.printf("Bin #%d: %s - %.2f / %d\n", binNumber, item, quantity, capacity);
            }
        }
    }

    /**
     * Displays the truck's name, location, storage bin contents,transaction history, and total sales.
     */
    public void displayInfo(CoffeeTruck selectedTruck){

        int i;
        String truckType;
        ArrayList<StorageBin> bins;

        if(selectedTruck instanceof SpecialCoffeeTruck)
            truckType = "Special Truck";
        else
            truckType = "Regular Truck";

        System.out.println("\n[" + truckType + "]");
        System.out.println("Name - " + selectedTruck.getName() + " | " + "Location: " + selectedTruck.getLocation());

        bins = selectedTruck.getBins();
        displayBins(bins);

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

    /**
     * Aggregates and displays the total inventory across all deployed trucks.
     *
     * @param trucks the list of all deployed coffee trucks
     */
    public void displayTotalInventory(ArrayList<CoffeeTruck> trucks){

        int i, j, k;
        int totalSmallCups = 0, totalMediumCups = 0, totalLargeCups = 0;
        double totalCoffeeGrams = 0, totalMilkOz = 0, totalWaterOz = 0, quantity;
        BinContent content;
        String item;
        ArrayList<StorageBin> bins;

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);
            bins = truck.getBins();

            for(j = 0; j < bins.size(); j++){

                StorageBin bin = bins.get(j);

                if(bin == null || bin.getContent() == null)
                    continue; //Skip if bin is empty

                content = bin.getContent();
                item = content.getName();
                quantity = bin.getItemQuantity();

                //Calculate total inventory
                switch(item){

                    case "Coffee Bean":
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
        System.out.println("Small Cups      : " + totalSmallCups);
        System.out.println("Medium Cups     : " + totalMediumCups);
        System.out.println("Large Cups      : " + totalLargeCups);
        System.out.printf("Coffee Beans     : %.2f g\n", totalCoffeeGrams);
        System.out.printf("Milk             : %.2f oz\n", totalMilkOz);
        System.out.printf("Water            : %.2f oz\n", totalWaterOz);
    }

    public void displaySyrupInventory(ArrayList<CoffeeTruck> trucks){

        int i, specialTruckCount = 0;

        System.out.println("\n--- Syrup Inventory by Special Truck ---");

        for(i = 0; i < trucks.size(); i++){

            CoffeeTruck truck = trucks.get(i);

            if(truck instanceof SpecialCoffeeTruck) {

                specialTruckCount++;
                System.out.println("\n--- Special Truck #" + specialTruckCount + " Syrups ---");

                ArrayList<StorageBin> bins = truck.getBins();

                for (StorageBin bin : bins) {
                    if (bin != null && bin.getContent() instanceof Syrup) {
                        Syrup syrup = (Syrup) bin.getContent();
                        String name = syrup.getName();
                        double quantity = syrup.getQuantity();
                        System.out.printf("%-16s : %.2f oz\n", name + " Syrup", quantity);
                    }
                }
            }
        }
    }

    /**
     * Displays the sales log and total revenue across all deployed trucks.
     * Also displays the individual sales log and revenue of each truck.
     *
     * @param trucks the list of all deployed coffee trucks
     */
    public void displayTruckSalesInfo(ArrayList<CoffeeTruck> trucks){

        int i, j;
        double combinedSales = 0;

        salesSummaryHeader();

        for(i = 0; i < trucks.size(); i++){
            CoffeeTruck truck = trucks.get(i);
            combinedSales += truck.getTotalSales(); //Compute combined sales across all trucks
            ArrayList<String> log = truck.getSalesLog();

            for(j = 0; j < log.size(); j++)
                System.out.println("[" + truck.getName() + "] " + log.get(j));

            //Display total sales of a truck
            System.out.printf("Total for [%s]   : $%.2f\n\n", truck.getName(), truck.getTotalSales());
        }

        //Display combined total sales of all truck
        System.out.printf("\nTotal Sales    : $%.2f\n", combinedSales);
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

    public double selectIngredientQuantity(int maxCapacity){

        double quantity;

        System.out.print("Enter quantity (max " + maxCapacity + "): ");
        quantity = scanner.nextDouble();

        return quantity;
    }

    public double selectIngredientRestockQuantity(){

        double quantity;

        System.out.println("Enter quantity to restock (0 for full restock): ");
        quantity = scanner.nextDouble();

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

    public boolean checkIngredientRestockQuantity(double quantity, double currentQuantity, int maxCapacity){

        boolean flag = false;
        double diff = Math.abs(quantity - currentQuantity);

        if(diff > maxCapacity){
            printInvalidOption();
            flag = true;
        }

        return flag;
    }

    /**
     * Displays a list of available trucks and allows the user to select one.
     *
     * @return The index of the selected truck in the list; -1 if no trucks are available.
     */
    public boolean checkTruckNumber(int truckNumber, ArrayList<CoffeeTruck> trucks){

        boolean flag = false;

        if(truckNumber < 1 || truckNumber > trucks.size()){

            System.out.println("Invalid truck number selected");
            flag = true;
        }

        return flag;
    }

    public boolean checkBinNumber(int binNumber, ArrayList<StorageBin> bins){

        boolean valid = false;

        if(binNumber < 0 || binNumber > bins.size()){
            System.out.println("Invalid bin number selected");
            valid = true;
        }

        return valid;
    }

}
