package Metthy.Controller;

import Metthy.Model.*;
import Metthy.View.MenuView;

import java.util.ArrayList;

/**
 * Main controller class that handles interactions between
 * the View (MenuView), the Model (TruckManager, DrinkManager),
 * and manages control logic for the Coffee Truck System.
 */
public class MainController {

    private final MenuView menuView;
    private final TruckManager truckManager;
    private final DrinkManager drinkManager;

    /**
     * Initializes the main controller and sets up view and managers.
     */
    public MainController(){

        this.menuView = new MenuView(this);
        this.truckManager = new TruckManager(this);
        this.drinkManager = new DrinkManager(this);
    }

    //PANEL DISPLAYS

    /** Displays the main menu panel. */
    public void mainMenuPanel(){

        menuView.showMainMenu();
    }
    /** Displays the truck creation panel. */
    public void truckCreationPanel(){

        menuView.showTruckCreationPanel();
    }
    /** Displays the truck removal panel. */
    public void removeTruckPanel(){

        menuView.showRemoveTruckPanel();
    }
    /** Displays the truck simulation panel. */
    public void simulateTruckPanel(){

        menuView.showTruckSimulationMenu();
    }

    /**
     * Displays information about the selected truck.
     *
     * @param truck the truck to view
     */
    public void truckInformationPanel(CoffeeTruck truck){

        menuView.showTruckInformationPanel(truck);
    }

    /**
     * Displays the drink preparation panel for a truck.
     *
     * @param truck the truck that will prepare drinks
     */
    public void prepareDrinkPanel(CoffeeTruck truck){

        menuView.showPrepareDrinkPanel(truck);
    }

    /**
     * Displays the maintenance panel for a truck.
     *
     * @param truck the truck to maintain
     */
    public void truckMaintenancePanel(CoffeeTruck truck){

        menuView.showTruckMaintenancePanel(truck);
    }

    /**
     * Displays the loadout panel for a truck.
     *
     * @param truck the truck to load
     * @param backAction action to perform when back is clicked
     * @param fromTruckCreation whether this is during truck creation
     */
    public void truckLoadoutPanel(CoffeeTruck truck, Runnable backAction, boolean fromTruckCreation){

        menuView.showTruckLoadoutPanel(truck, backAction, fromTruckCreation);
    }
    /** Displays the dashboard panel. */
    public void dashboardPanel(){

        menuView.showDashboardPanel();
    }

    //TRUCK MANAGEMENT

    /**
     * Checks if a name is unique
     *
     * @param name - the name to be checked
     * @return true if unique, false otherwise
     */
    public boolean isTruckNameUnique(String name){

        return truckManager.checkTruckName(name);
    }

    /**
     * Checks if a location is unique
     *
     * @param location - the location to be checked
     * @return true if unique, false otherwise
     */
    public boolean isTruckLocationUnique(String location){

        return truckManager.checkTruckLocation(location);
    }

    /**
     * Removes a coffee truck selected by the user.
     */
    public void truckRemoval(CoffeeTruck truck){

        truckManager.removeTruck(truck);
    }

    /**
     * Sets a default loadout (items and quantities) for the truck.
     *
     * @param truck the truck to configure
     */
    public void setDefaultTruckLoadout(CoffeeTruck truck){

        truck.setDefaultLoadout();
    }

    /**
     * Creates a new truck.
     *
     * @param name truck name
     * @param location truck location
     * @param truckType type of truck (regular/special)
     * @return the created truck
     */
    public CoffeeTruck truckCreation(String name, String location, int truckType) {

        return truckManager.createTruck(name, location, truckType, drinkManager);
    }

    /**
     * Returns a list of ingredients available.
     *
     * @return ingredient list
     */
    public ArrayList<BinContent> getIngredients(){

        return truckManager.getIngredientList();
    }

    /**
     * Gets a specific ingredient based on its name.
     *
     * @param name ingredient name
     * @return BinContent object
     */
    public BinContent getIngredientFromName(String name){

        return truckManager.getIngredientFromName(name);
    }

    /**
     * Assigns an ingredient and quantity to a bin in a truck.
     */
    public void assignItemToBin(CoffeeTruck truck, StorageBin bin, BinContent content, double itemQuantity){

        truck.assignItemToBin(bin, content, itemQuantity);
    }

    //BIN MANAGEMENT

    /** Gets a specific bin from the truck using its number. */
    public StorageBin getBinByNumber(CoffeeTruck truck, int binNumber){

        return truck.getBinByNumber(binNumber);
    }
    /** Restocks a specific bin by a given quantity. */
    public void restockBin(CoffeeTruck truck, StorageBin bin, double quantity){

        truck.restockBin(bin, quantity);
    }
    /** Fully restocks a specific bin to capacity. */
    public void fullRestockBin(CoffeeTruck truck, StorageBin bin){

        truck.fullRestockBin(bin);
    }
    /** Fully restocks all bins in a truck. */
    public void fullRestockAllBins(CoffeeTruck truck){

        truck.fullRestockAllBins();
    }
    /** Modifies a bin's contents and quantity. */
    public void modifyBin(CoffeeTruck truck, StorageBin bin, BinContent ingredient, double quantity){

        truck.assignItemToBin(bin, ingredient, quantity);
    }
    /** Empties the contents of a specific bin. */
    public void emptyBin(CoffeeTruck truck, StorageBin bin){

        truck.emptyBin(bin);
    }
    /** Empties all bins in a truck. */
    public void emptyAllBins(CoffeeTruck truck){

        truck.emptyAllBins();
    }
    /** Validates if the given bin number is acceptable. */
    public boolean validBinNumber(int binNumber){

        return truckManager.checkBinNumber(binNumber);
    }
    /** Returns the count of regular trucks. */
    public int getRegularTruckCount(){

        return truckManager.getRegularTruckCount();
    }
    /** Returns the count of special trucks. */
    public int getSpecialTruckCount(){

        return truckManager.getSpecialTruckCount();
    }
    /** Returns all the trucks in the system. */
    public ArrayList<CoffeeTruck> getTrucks(){

        return truckManager.getTrucks();
    }

    //DRINK MANAGEMENT

    public void setCupPrice(double price){

        drinkManager.setCupPrice(price);
    }

    public void setCoffeeGramPrice(double price){

        drinkManager.setCoffeeGramPrice(price);
    }

    public void setMilkOzPrice(double price){

        drinkManager.setMilkOzPrice(price);
    }

    public void setWaterOzPrice(double price){

        drinkManager.setWaterOzPrice(price);
    }

    public void setSyrupOzPrice(double price){

        drinkManager.setSyrupOzPrice(price);
    }

    public double getSyrupOzPrice(){

        return drinkManager.getSyrupOzPrice();
    }

    public void setExtraShotPrice(double price){

        drinkManager.setExtraShotPrice(price);
    }

    public double getExtraShotPrice(){

        return drinkManager.getExtraShotPrice();
    }

    public void setBaseDrinkPrices(){

        drinkManager.setBaseDrinkPrices();
    }

    public Drink getDrink(String type, String size){

        return drinkManager.getDrink(type, size);
    }

    public ArrayList<Drink> getDrinks(){

        return drinkManager.getDrinks();
    }

    public double getBrewRatio(String brewType){

        return drinkManager.getBrewRatio(brewType);
    }

    public double[] getRequiredIngredients(String type, String size, double ratio){

        return drinkManager.getAdjustedIngredients(type, size, ratio);
    }

    public boolean hasSufficientIngredients(StorageBin[] bins, double[] ingredients){

        return drinkManager.hasSufficientIngredients(bins, ingredients);
    }

    public void useIngredients(StorageBin[] bins, double[] ingredients){

        drinkManager.useIngredients(bins, ingredients);
    }

    public StorageBin findBin(CoffeeTruck truck, String ingredient){

        return truck.findBin(ingredient);
    }

    public void addToTotalSales(CoffeeTruck truck, double price){

        truck.addToTotalSales(price);
    }

    public void recordSale(CoffeeTruck truck, String type, String size, double coffeeGrams, double milk, double water, double price){

        truck.recordSale(type, size, coffeeGrams, milk, water, price);
    }

    public void recordSpecialSale(SpecialCoffeeTruck truck, String type, String size, String brewType, double coffeeGrams,
                                  double milk, double water, ArrayList<BinContent> addOns, int extraShots, double price){

        truck.recordSpecialSale(type, size, brewType, coffeeGrams, milk, water, addOns, extraShots, price);
    }

    public ArrayList<Syrup> getAvailableSyrup(CoffeeTruck truck){

        return drinkManager.getAvailableSyrup(truck.getBins());
    }

    public boolean hasSufficientSyrup(CoffeeTruck truck, String syrupType){


        return drinkManager.hasSufficientSyrup(truck, syrupType);
    }
}
