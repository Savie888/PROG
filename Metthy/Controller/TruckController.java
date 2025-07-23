package Metthy.Controller;

import Metthy.Model.*;
import Metthy.View.MenuView;
import Metthy.View.TruckView;

import java.util.ArrayList;

public class TruckController{

    private final TruckView truckView;
    private final MenuView menuView;
    private final TruckManager truckManager;
    private final DrinkController drinkController;
    private boolean running;

    public TruckController(MenuView menuView){

        this.menuView = menuView;
        this.truckView = new TruckView();
        this.truckManager = new TruckManager();
        this.drinkController = new DrinkController();
    }

    public void modifyBin(CoffeeTruck truck, int binNumber){

        int option, max;
        double quantity;
        StorageBin bin;
        BinContent content;

        bin = truck.getBins().get(binNumber-1);
        truckView.showBinSetupPrompt(binNumber);

        //Select ingredient to store in bin
        do{
            option = truckView.selectIngredientToStore(binNumber); //Select ingredient to store in bin
        } while(truckView.checkIngredientChoice(option));

        if(option == 0)
            truckView.showBinSkipPrompt(binNumber);

        else{
            content = truckManager.getIngredientFromOption(option);
            max = content.getCapacity();

            do{
                quantity = truckView.selectIngredientQuantity(max);
            } while(truckView.checkIngredientQuantity(quantity, max));

            truck.assignItemToBin(bin, content, quantity);
            truckView.showBinLoadedMessage(binNumber, content, quantity);
        }
    }

    /**
     * Sets up the loadout of a truck.
     */
    public void truckLoadout(CoffeeTruck truck){

        int i;
        String setDefault;
        ArrayList<StorageBin> bins = truck.getBins();

        setDefault = truckView.showDefaultLoadoutPrompt(truck);

        if(truckManager.isYes(setDefault))
            truck.setDefaultLoadout(); //Set to default loadout

        else{
            for(i = 0; i < bins.size(); i++){

                StorageBin bin = bins.get(i);
                truckView.showBinSetupPrompt(bin.getBinNumber());
                modifyBin(truck, bin.getBinNumber());
            }

            truckView.showLoadoutComplete(truck);
        }
    }

    public void specialTruckLoadout(SpecialCoffeeTruck truck){

        int i, binNumber;
        String setDefault;
        ArrayList<StorageBin> bins = truck.getBins();

        setDefault = truckView.showDefaultLoadoutPrompt(truck);

        if(truckManager.isYes(setDefault))
            truck.setDefaultLoadout(); //Set to default loadout

        else{
            for(i = 0; i < bins.size(); i++){

                StorageBin bin = bins.get(i);
                binNumber = bin.getBinNumber();
                truckView.showBinSetupPrompt(binNumber);

                if(binNumber == 9 || binNumber == 10)
                    truck.modifySyrupBin(binNumber);

                else
                    modifyBin(truck, bin.getBinNumber());
            }

            truckView.showLoadoutComplete(truck);
        }
    }

    public void truckCreation(){

        int truckType;
        String name, location, setLoadout, repeat;
        CoffeeTruck truck;

        do{
            do{
                name = truckView.enterUniqueName();
            } while(truckManager.checkName(name));

            do{
                location = truckView.enterUniqueLocation();
            } while(truckManager.checkLocation(location));

            do{
                truckType = truckView.enterTruckType();
            } while(!truckManager.checkTruckType(truckType));

            truck = truckManager.createTruck(name, location, truckType, drinkController);

            do{
                setLoadout = truckView.showLoadoutPrompt();
            } while(!truckManager.checkYesOrNo(setLoadout));


            if(truckManager.isYes(setLoadout))
                truckLoadout(truck);

            //Add new truck to arraylist of trucks
            truckManager.addTruck(truck);

            repeat = truckView.repeatTruckCreationPrompt();

        } while(repeat.equalsIgnoreCase("yes"));

        drinkController.setIngredientPrices(); //Set initial ingredient prices
    }

    /**
     * Removes a coffee truck selected by the user.
     */
    public void truckRemoval(){

        int truckNumber;
        ArrayList<CoffeeTruck> trucks = truckManager.getTrucks();

        do{
            truckNumber = truckView.selectTruck(trucks);
        } while(truckView.checkTruckNumber(truckNumber - 1, trucks));

        truckManager.removeTruck(truckNumber - 1);
    }

    public int selectTruck(){

        int truckNumber;
        ArrayList<CoffeeTruck> trucks = truckManager.getTrucks();

        truckNumber = truckView.selectTruck(trucks);

        return truckNumber;
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

        int truckNumber, option, choice;
        CoffeeTruck selectedTruck;
        ArrayList<CoffeeTruck> trucks = truckManager.getTrucks();

        running = true;

        if(trucks.isEmpty()){
            menuView.noTrucksAvailablePrompt();
            running = false;
        }

        do{
            truckNumber = selectTruck();
        } while(truckView.checkTruckNumber(truckNumber, trucks));

        selectedTruck = trucks.get(truckNumber - 1);

        while(running){

            menuView.displaySimulationMenu();
            option = menuView.getSimulationMenuInput();

            switch (option){
                case 1:
                    drinkController.prepareCoffeeMenu(selectedTruck); //Display Coffee Menu
                    break;
                case 2:
                    displayTruckInfo(selectedTruck); //Display truck information
                    break;
                case 3:
                    menuView.displayTruckMaintenanceMenu();
                    choice = menuView.getTruckMaintenanceMenuInput();
                    truckMaintenanceMenu(selectedTruck, choice); //Display truck maintenance menu
                    break;
                case 4:
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
            }
        }
    }

    public void displayDashboard(){

        int regularCount, specialCount, totalCount;

        regularCount = truckManager.getRegularTruckCount();
        specialCount = truckManager.getSpecialTruckCount();
        totalCount = truckManager.getTrucks().size();

        truckView.displayTruckDeployment(regularCount, specialCount, totalCount);

        truckView.displayTruckSalesInfo(truckManager.getTrucks());
    }

    /**
     * Aggregates and displays the total inventory across all deployed trucks.
     * Includes coffee beans (grams), milk (oz), water (oz), and cup counts by size.
     *
     * //@param trucks the list of all deployed coffee trucks
     */
    /*private void displayTotalInventory(ArrayList<CoffeeTruck> trucks){

        int i, j;
        int totalSmallCups = 0, totalMediumCups = 0, totalLargeCups = 0;
        double totalCoffeeGrams = 0, totalMilkOz = 0, totalWaterOz = 0, quantity;
        double totalHazelnutSyrupOz = 0, totalChocolateSyrupOz = 0, totalAlmondSyrupOz = 0, totalSucroseSyrupOz = 0;
        String item;
        ArrayList<StorageBin> bins;

        for(i = 0; i < trucks.size(); i++){

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

                    case "Hazelnut":
                        totalHazelnutSyrupOz += quantity;
                        break;

                    case "Chocolate":
                        totalChocolateSyrupOz += quantity;
                        break;

                    case "Almond":
                        totalAlmondSyrupOz += quantity;
                        break;

                    case "Sucrose":
                        totalSucroseSyrupOz += quantity;
                        break;
                }
            }
        }

        //Display inventory
        System.out.println("\n--- Aggregate Inventory ---");
        System.out.printf("Coffee Beans     : %.2f g\n", totalCoffeeGrams);
        System.out.printf("Milk             : %.2f oz\n", totalMilkOz);
        System.out.printf("Water            : %.2f oz\n", totalWaterOz);
        System.out.printf("Hazelnut Syrup   : %.2f oz\n", totalHazelnutSyrupOz);
        System.out.printf("Chocolate Syrup  : %.2f oz\n", totalChocolateSyrupOz);
        System.out.printf("Almond Syrup     : %.2f oz\n", totalAlmondSyrupOz);
        System.out.printf("Sucrose Syrup    : %.2f oz\n", totalSucroseSyrupOz);
        System.out.println("Small Cups      : " + totalSmallCups);
        System.out.println("Medium Cups     : " + totalMediumCups);
        System.out.println("Large Cups      : " + totalLargeCups);
    }
*/



    public void displayTruckInfo(CoffeeTruck selectedTruck){

        truckView.displayInfo(selectedTruck);
    }

    public void restockBin(CoffeeTruck truck, int binNumber){

        double quantity;
        StorageBin bin = truck.getBins().get(binNumber-1);

        if(bin.getContent() == null)
            truckView.restockFailMessage();

        else{

            do{
                quantity = truckView.selectIngredientRestockQuantity();
            } while(truckView.checkIngredientRestockQuantity(quantity, bin.getContent().getQuantity(), bin.getContent().getCapacity()));

            truck.restockBin(bin, quantity);
        }
    }

    /**
     * Restocks all bins that have an assigned item.
     */

    public void restockAllBins(CoffeeTruck truck){

        int i;

        for(i = 0; i < truck.getBins().size(); i++){

            restockBin(truck, i + 1);
        }
    }

    public void emptyBin(CoffeeTruck truck, int binNumber){

        StorageBin bin = truck.getBins().get(binNumber-1);

        if(bin.isEmpty()){
            truck.emptyBin(bin);
            truckView.binEmptiedMessage(binNumber);
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
    public void truckMaintenanceMenu(CoffeeTruck truck, int option){

        int choice, binNumber;
        String name, location;

        switch(option){
            case 1:
                choice = menuView.restockMenu();

                if(choice == 1)
                    restockAllBins(truck); //Restock all bins

                else if(choice == 2){
                    do{
                        binNumber = truckView.selectBin();
                    } while(truckView.checkBinNumber(binNumber, truck.getBins()));

                    restockBin(truck, binNumber); //Restock selected bin
                }
                break;
            case 2:
                choice = menuView.modifyMenu();

                if(choice == 1)
                    truckLoadout(truck); //Modify all bins

                else if(choice == 2){
                    do{
                        binNumber = truckView.selectBin();
                    } while(truckView.checkBinNumber(binNumber, truck.getBins()));

                    modifyBin(truck, binNumber);
                }
                break;
            case 3:
                choice = menuView.emptyMenu();

                if(choice == 1)
                    truck.emptyAllBins(); //Empty all bins

                else if(choice == 2){
                    do{
                        binNumber = truckView.selectBin();
                    } while(truckView.checkBinNumber(binNumber, truck.getBins()));

                    emptyBin(truck, binNumber); //Empty selected bin
                }
                break;
            case 4:
                name = menuView.enterNewName();
                truck.setName(name); //Set new name
                break;
            case 5:
                location = menuView.enterNewLocation();
                truck.setLocation(location); //Set new location
                break;
            case 6:
                drinkController.setIngredientPrices(); //Set ingredient prices
                break;
            case 7:
                System.out.println("Exiting Menu...");
                break;
            default:
                System.out.println("Invalid option. Please try again");
                break;
        }
    }
}
