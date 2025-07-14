package Metthy.Controller;

import Metthy.Model.RegularCoffeeTruck;
import Metthy.Model.TruckModel;
import Metthy.StorageBin;
import Metthy.View.TruckView;

import java.util.ArrayList;

public class TruckController{

    private final TruckView truckView;
    private final TruckModel truckModel;
    public ArrayList<RegularCoffeeTruck> trucks;
    public ArrayList<StorageBin> bins;
    private boolean running;

    public TruckController(){

        this.trucks = new ArrayList<>();
        this.bins = new ArrayList<>();
        this.truckView = new TruckView(trucks, bins);
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
                setLoadout = truckView.setLoadoutPrompt();
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

    /*
    @Override
    public void start(){

        int option;

        running = true;

        while (running) {

            view.displayMainMenu();
            option = view.getMenuSelection();
            mainMenu(option);
        }

    }
*/
}
