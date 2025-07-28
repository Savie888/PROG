package Metthy.Controller;

import Metthy.Model.*;
import Metthy.View.MenuView;

import java.util.ArrayList;

public class TruckController{

    private final MenuView menuView;
    private final TruckManager truckManager;
    private final DrinkController drinkController;
    //private static boolean pricesInitialized = true;

    public TruckController(){

        this.menuView = new MenuView(this);
        this.truckManager = new TruckManager();
        this.drinkController = new DrinkController();
    }

    public void mainMenuPanel(){

        menuView.showMainMenu();
    }

    public void truckCreationPanel(){

        menuView.showTruckCreationPanel();
    }

    public void removeTruckPanel(){

        menuView.showRemoveTruckPanel();
    }

    public void truckInformationPanel(CoffeeTruck truck){

        menuView.showTruckInformationPanel(truck);
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
    public void simulateTruckPanel(){

        menuView.showTruckSimulationMenu();
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
    public void truckMaintenancePanel(CoffeeTruck truck){

        menuView.showTruckMaintenancePanel(truck);
    }

    public void truckLoadoutPanel(CoffeeTruck truck){

        menuView.showTruckLoadoutPanel(truck);
    }

    public boolean isTruckNameUnique(String name){

        return truckManager.checkTruckName(name);
    }

    public boolean isTruckLocationUnique(String location){

        return truckManager.checkTruckLocation(location);
    }

    /**
     * Removes a coffee truck selected by the user.
     */
    public void truckRemoval(CoffeeTruck truck){

        truckManager.removeTruck(truck);
    }

    public void setDefaultTruckLoadout(CoffeeTruck truck){

        truck.setDefaultLoadout();
    }

    public CoffeeTruck truckCreation(String name, String location, int truckType) {

        return truckManager.createTruck(name, location, truckType, drinkController);
    }

    public ArrayList<BinContent> getIngredients(){

        return truckManager.getIngredientList();
    }

    public BinContent getIngredientFromOption(int option) {

        return truckManager.getIngredientFromOption(option);
    }

    public void assignItemToBin(CoffeeTruck truck, StorageBin bin, BinContent content, double itemQuantity){

        truck.assignItemToBin(bin, content, itemQuantity);
    }

    public ArrayList<CoffeeTruck> getTrucks(){

        return truckManager.getTrucks();
    }

    /*
        if(pricesInitialized){
            drinkController.setIngredientPrices(); //Set initial ingredient prices
            pricesInitialized = false;
        }
        */

    /*
    public void modifyBin(CoffeeTruck truck, int binNumber){

        int option, max;
        double quantity;
        StorageBin bin;
        BinContent content;

        bin = truck.getBins().get(binNumber-1);
        menuView.showBinSetupPrompt(binNumber);

        //Select ingredient to store in bin
        do{
            option = menuView.selectIngredientToStore(binNumber); //Select ingredient to store in bin
        } while(menuView.checkIngredientChoice(option));

        if(option == 0)
            menuView.showBinSkipPrompt(binNumber);

        else{
            content = truckManager.getIngredientFromOption(option).clone();
            max = content.getCapacity();

            do{
                quantity = menuView.selectIngredientQuantity(max);
            } while(menuView.checkIngredientQuantity(quantity, max));

            truck.assignItemToBin(bin, content, quantity);
            menuView.showBinLoadedMessage(binNumber, content, quantity);
        }
    }

    public void modifySyrupBin(SpecialCoffeeTruck truck, int binNumber){

        int max;
        double quantity;
        StorageBin bin;
        BinContent content;
        DrinkView drinkView = drinkController.getDrinkView();

        bin = truck.getBins().get(binNumber-1);
        menuView.showBinSetupPrompt(binNumber);

        content = drinkView.enterSyrupName(); //Select ingredient to store in bin
        max = content.getCapacity();

        do{
            quantity = menuView.selectIngredientQuantity(max);
        } while(menuView.checkIngredientQuantity(quantity, max));

        truck.assignItemToBin(bin, content, quantity);
        menuView.showBinLoadedMessage(binNumber, content, quantity);
    }


    public void truckLoadout(CoffeeTruck truck){

        int i;
        ArrayList<StorageBin> bins = truck.getBins();

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            menuView.showBinSetupPrompt(bin.getBinNumber());
            modifyBin(truck, bin.getBinNumber());
        }

        menuView.showLoadoutComplete(truck);
    }

    public void specialTruckLoadout(SpecialCoffeeTruck truck){

        int i, binNumber;
        ArrayList<StorageBin> bins = truck.getBins();

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            binNumber = bin.getBinNumber();
            menuView.showBinSetupPrompt(binNumber);

            if(binNumber == 9 || binNumber == 10)
                modifySyrupBin(truck, binNumber);

            else
                modifyBin(truck, bin.getBinNumber());
        }

        menuView.showLoadoutComplete(truck);
    }

    public int selectTruck(){

        int truckNumber;
        ArrayList<CoffeeTruck> trucks = truckManager.getTrucks();

        truckNumber = menuView.selectTruck(trucks);

        return truckNumber;
    }

    public void simulateMenu(){

        int truckNumber, option, choice;
        CoffeeTruck selectedTruck;
        ArrayList<CoffeeTruck> trucks = getTrucks();

        boolean running = true;

        if(trucks.isEmpty()){
            menuView.noTrucksAvailablePrompt();
            running = false;
        }

        do{
            truckNumber = selectTruck();
        } while(menuView.checkTruckNumber(truckNumber, trucks));

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

        menuView.displayTruckDeployment(regularCount, specialCount, totalCount);

        menuView.displayTotalInventory(truckManager.getTrucks());

        menuView.displaySyrupInventory(truckManager.getTrucks());

        menuView.displayTruckSalesInfo(truckManager.getTrucks());
    }

    public void displayTruckInfo(CoffeeTruck selectedTruck){

        menuView.displayInfo(selectedTruck);
    }

    public void restockBin(CoffeeTruck truck, int binNumber){

        double quantity;
        StorageBin bin = truck.getBins().get(binNumber-1);

        if(bin.getContent() == null)
            menuView.restockFailMessage();

        else{

            do{
                quantity = menuView.selectIngredientRestockQuantity();
            } while(menuView.checkIngredientRestockQuantity(quantity, bin.getContent().getQuantity(), bin.getContent().getCapacity()));

            truck.restockBin(bin, quantity);
        }
    }

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
            menuView.binEmptiedMessage(binNumber);
        }
    }


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
                        binNumber = menuView.selectBin();
                    } while(menuView.checkBinNumber(binNumber, truck.getBins()));

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
                        binNumber = menuView.selectBin();
                    } while(menuView.checkBinNumber(binNumber, truck.getBins()));

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
                        binNumber = menuView.selectBin();
                    } while(menuView.checkBinNumber(binNumber, truck.getBins()));

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
*/

}
