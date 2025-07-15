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

    public TruckController(MenuView menuView){

        this.trucks = new ArrayList<>();
        this.bins = new ArrayList<>();
        this.truckView = new TruckView(trucks, bins);
        this.menuView = menuView;
        this.truckModel = new TruckModel();
    }

    public void truckCreation(){

        int truckType;
        String name, location, setLoadout, repeat;

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

            do{
                setLoadout = truckView.showLoadoutPrompt();
            } while(!truckModel.checkYesOrNo(setLoadout));

            truckModel.createTruck(name, location, truckType, setLoadout);

            repeat = truckView.repeatPrompt();

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

    public void displayDashboard(){

    }

    public int selectTruck(){

        int truckNumber;

        truckNumber = truckView.selectTruck(trucks);

        return truckNumber;
    }

    public void displayTruckInfo(RegularCoffeeTruck selectedTruck){

        truckView.displayInfo(selectedTruck);
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
                    truck.modifyAllBins(); //Modify all bins

                else if(choice == 2){
                    do{
                        binNumber = truckView.selectBin();
                    } while(truckView.checkBinNumber(binNumber));

                    truck.modifyBin(binNumber); //Modify regular bin
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
