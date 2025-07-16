package Metthy.Controller;

import Metthy.Model.RegularCoffeeTruck;
import Metthy.Model.TruckModel;
import Metthy.StorageBin;
import Metthy.View.MenuView;
import Metthy.View.TruckView;

import java.util.ArrayList;

public class TruckController{

    public ArrayList<RegularCoffeeTruck> trucks;
    public ArrayList<StorageBin> bins;
    private final TruckView truckView;
    private final MenuView menuView;
    private final TruckModel truckModel;
    private boolean running;

    public TruckController(MenuView menuView){

        this.trucks = new ArrayList<>();
        this.bins = new ArrayList<>();
        this.truckView = new TruckView(bins);
        this.menuView = menuView;
        this.truckModel = new TruckModel();
    }

    public void truckLoadout(RegularCoffeeTruck truck){

        int i, option, max;
        double quantity;
        String setDefault;
        StorageBin bin;

        setDefault = truckView.showDefaultLoadoutPrompt(truck);

        if(truckModel.isYes(setDefault))
            truck.setDefaultLoadout(); //Set to default loadout

        else{
            for(i = 0; i < bins.size(); i++){

                truckView.showBinPrompt(i+1);
                bin = bins.get(i);

                do{
                    option = truckView.selectIngredientToStore(i+1); //Select ingredient to store in bin
                    max = bin.getCapacityForItem(bin.getItemType()); //Get max capacity of bin according to item assigned
                } while(truckView.checkIngredientChoice(option));

                do{
                    quantity = truckView.selectIngredientQuantity(max);
                } while(truckView.checkIngredientQuantity(quantity, max));

                if(option == 0)
                    truckView.showBinSkipPrompt(i+1);

                truck.setLoadout(option, quantity);
                truckView.showBinLoadedMessage(bin.getBinNumber(), bin.getItemType(), quantity);
            }

            truckView.showLoadoutComplete(truck);
        }
    }

    public void truckCreation(){

        int truckType;
        String name, location, setLoadout, repeat;
        RegularCoffeeTruck truck;

        do{
            do{
                name = truckView.enterUniqueName();
            } while(truckModel.checkName(name));

            do{
                location = truckView.enterUniqueLocation();
            } while(truckModel.checkLocation(location));

            do{
                truckType = truckView.enterTruckType();
            } while(!truckModel.checkTruckType(truckType));

            truck = truckModel.createTruck(name, location, truckType);

            do{
                setLoadout = truckView.showLoadoutPrompt();
            } while(!truckModel.checkYesOrNo(setLoadout));


            if(truckModel.isYes(setLoadout))
                truckLoadout(truck);

            //Add new truck to arraylist of trucks
            truckModel.addTruck(truck);

            repeat = truckView.repeatTruckCreationPrompt();

        } while(repeat.equalsIgnoreCase("yes"));
    }

    /**
     * Removes a coffee truck selected by the user.
     */
    public void truckRemoval(){

        int truckNumber;
        ArrayList<RegularCoffeeTruck> trucks = truckModel.getTrucks();

        do{
            truckNumber = truckView.selectTruck(trucks);
        } while(truckModel.checkTruckIndex(truckNumber - 1, trucks));

        truckModel.removeTruck(truckNumber - 1);
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
        RegularCoffeeTruck selectedTruck;
        ArrayList<RegularCoffeeTruck> trucks = truckModel.getTrucks();

        running = true;

        if(trucks.isEmpty()){
            menuView.noTrucksAvailablePrompt();
            running = false;
        }

        while(running){

            menuView.displaySimulationMenu();
            option = menuView.getSimulationMenuInput();

            do{
                truckNumber = selectTruck();
            } while(truckModel.checkTruckIndex(truckNumber - 1, trucks));

            selectedTruck = trucks.get(truckNumber - 1);

            switch (option){

                case 1:
                    //coffeeMenu(); //Display Coffee Menu
                    break;
                case 2:
                    displayTruckInfo(selectedTruck); //Display truck information
                    break;
                case 3:
                    menuView.dispayTruckMaintenanceMenu();
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

    }

    public int selectTruck(){

        int truckNumber;
        trucks = truckModel.getTrucks();

        truckNumber = truckView.selectTruck(trucks);

        return truckNumber;
    }

    public void displayTruckInfo(RegularCoffeeTruck selectedTruck){

        truckView.displayInfo(selectedTruck);
    }

    public void modifyBin(RegularCoffeeTruck truck){

        int binNumber, option, max;
        double quantity;
        StorageBin bin;

        do{
            binNumber = truckView.selectBin();
        } while(truckView.checkBinNumber(binNumber));

        truckView.showBinPrompt(binNumber);
        bin = bins.get(binNumber - 1);

        do{
            option = truckView.selectIngredientToStore(binNumber); //Select ingredient to store in bin
            max = bin.getCapacityForItem(bin.getItemType()); //Get max capacity of bin according to item assigned
        } while(truckView.checkIngredientChoice(option));

        do{
            quantity = truckView.selectIngredientQuantity(max);
        } while(truckView.checkIngredientQuantity(quantity, max));

        truck.modifyBin(binNumber - 1, option, quantity);
    }

    public void truckMaintenanceMenu(RegularCoffeeTruck truck, int option){

        int choice, binNumber;
        String name, location;

        switch(option){

            case 1:
                choice = menuView.restockMenu();

                if(choice == 1)
                    truck.restockAllBins(); //Restock all bins

                else if(choice == 2){
                    do{
                        binNumber = truckView.selectBin();
                    } while(truckView.checkBinNumber(binNumber));

                    truck.restockBin(binNumber); //Restock selected bin
                }
                break;
            case 2:
                choice = menuView.modifyMenu();

                if(choice == 1)
                    //truck.modifyAllBins(); //Modify all bins

                /*else*/ if(choice == 2){
                    modifyBin(truck);
                }
                break;
            case 3:
                choice = menuView.emptyMenu();

                if(choice == 1)
                    truck.emptyAllBins(); //Empty all bins

                else if(choice == 2){
                    do{
                        binNumber = truckView.selectBin();
                    } while(truckView.checkBinNumber(binNumber));

                    truck.emptyBin(binNumber); //Empty selected bin
                }
                break;
            case 4:
                name = menuView.getNewName();
                truck.setName(name); //Set new name
                break;
            case 5:
                location = menuView.getNewLocation();
                truck.setLocation(location); //Set new location
                break;
            case 6:
                //Add in drink controller
                //drinkManager.setIngredientPrices(); //Set ingredient prices
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
