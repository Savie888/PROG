package Metthy.Controller;

import Metthy.Model.*;
import Metthy.View.DrinkView;
import Metthy.View.MenuView;
import Metthy.View.TruckView;

import java.util.ArrayList;

public class TruckController{

    private final TruckView truckView;
    private final MenuView menuView;
    private final TruckManager truckManager;
    private final DrinkController drinkController;
    private static boolean pricesInitialized = true;

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
            content = truckManager.getIngredientFromOption(option).clone();
            max = content.getCapacity();

            do{
                quantity = truckView.selectIngredientQuantity(max);
            } while(truckView.checkIngredientQuantity(quantity, max));

            truck.assignItemToBin(bin, content, quantity);
            truckView.showBinLoadedMessage(binNumber, content, quantity);
        }
    }

    public void modifySyrupBin(SpecialCoffeeTruck truck, int binNumber){

        int max;
        double quantity;
        StorageBin bin;
        BinContent content;
        DrinkView drinkView = drinkController.getDrinkView();

        bin = truck.getBins().get(binNumber-1);
        truckView.showBinSetupPrompt(binNumber);

        content = drinkView.enterSyrupName(); //Select ingredient to store in bin
        max = content.getCapacity();

        do{
            quantity = truckView.selectIngredientQuantity(max);
        } while(truckView.checkIngredientQuantity(quantity, max));

        truck.assignItemToBin(bin, content, quantity);
        truckView.showBinLoadedMessage(binNumber, content, quantity);
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
                    modifySyrupBin(truck, binNumber);

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


            if(truckManager.isYes(setLoadout)){

                if(truck instanceof RegularCoffeeTruck)
                    truckLoadout(truck);
                else
                    specialTruckLoadout((SpecialCoffeeTruck) truck);
            }

            //Add new truck to arraylist of trucks
            truckManager.addTruck(truck);

            repeat = truckView.repeatTruckCreationPrompt();

        } while(repeat.equalsIgnoreCase("yes"));

        if(pricesInitialized){
            drinkController.setIngredientPrices(); //Set initial ingredient prices
            pricesInitialized = false;
        }
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

        boolean running = true;

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

        truckView.displayTotalInventory(truckManager.getTrucks());

        truckView.displaySyrupInventory(truckManager.getTrucks());

        truckView.displayTruckSalesInfo(truckManager.getTrucks());
    }


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

                if(choice == 1){
                    if(truck instanceof SpecialCoffeeTruck)
                        specialTruckLoadout((SpecialCoffeeTruck) truck); //Set special truck loadout
                    else
                        truckLoadout(truck); //Set regular truck loadout
                }

                else if(choice == 2){
                    do{
                        binNumber = truckView.selectBin();
                    } while(truckView.checkBinNumber(binNumber, truck.getBins()));

                    if(binNumber == 9 || binNumber == 10)
                        modifySyrupBin((SpecialCoffeeTruck) truck, binNumber);
                    else
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

    public TruckView getTruckView() {

        return truckView;
    }
}
