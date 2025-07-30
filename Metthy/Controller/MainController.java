package Metthy.Controller;

import Metthy.Model.*;
import Metthy.View.MenuView;

import java.util.ArrayList;


public class MainController {

    private final MenuView menuView;
    private final TruckManager truckManager;
    private final DrinkManager drinkManager;

    public MainController(){

        this.menuView = new MenuView(this);
        this.truckManager = new TruckManager(this);
        this.drinkManager = new DrinkManager(this);
    }

    //PANEL DISPLAYS

    public void mainMenuPanel(){

        menuView.showMainMenu();
    }

    public void truckCreationPanel(){

        menuView.showTruckCreationPanel();
    }

    public void removeTruckPanel(){

        menuView.showRemoveTruckPanel();
    }

    public void simulateTruckPanel(){

        menuView.showTruckSimulationMenu();
    }

    public void truckInformationPanel(CoffeeTruck truck){

        menuView.showTruckInformationPanel(truck);
    }

    public void prepareDrinkPanel(CoffeeTruck truck){

        menuView.showPrepareDrinkPanel(truck);
    }

    public void truckMaintenancePanel(CoffeeTruck truck){

        menuView.showTruckMaintenancePanel(truck);
    }

    public void truckLoadoutPanel(CoffeeTruck truck, Runnable backAction, boolean fromTruckCreation){

        menuView.showTruckLoadoutPanel(truck, backAction, fromTruckCreation);
    }

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

    public void setDefaultTruckLoadout(CoffeeTruck truck){

        truck.setDefaultLoadout();
    }

    public CoffeeTruck truckCreation(String name, String location, int truckType) {

        return truckManager.createTruck(name, location, truckType, drinkManager);
    }

    public ArrayList<BinContent> getIngredients(){

        return truckManager.getIngredientList();
    }

    public BinContent getIngredientFromOption(int option) {

        return truckManager.getIngredientFromOption(option);
    }

    public BinContent getIngredientFromName(String name){

        int i;
        BinContent content;
        ArrayList<BinContent> ingredientList = getIngredients();

        for(i = 0; i < ingredientList.size(); i++){

            content = ingredientList.get(i);

            if(content.getName().equalsIgnoreCase(name)){
                return content.clone();
            }
        }

        return null; //Not found
    }

    public void assignItemToBin(CoffeeTruck truck, StorageBin bin, BinContent content, double itemQuantity){

        truck.assignItemToBin(bin, content, itemQuantity);
    }

    //BIN MANAGEMENT

    public StorageBin getBinByNumber(CoffeeTruck truck, int binNumber){

        return truck.getBinByNumber(binNumber);
    }

    public void restockBin(CoffeeTruck truck, StorageBin bin, double quantity){

        truck.restockBin(bin, quantity);
    }

    public void fullRestockBin(CoffeeTruck truck, StorageBin bin){

        truck.fullRestockBin(bin);
    }

    public void fullRestockAllBins(CoffeeTruck truck){

        truck.fullRestockAllBins();
    }

    public void modifyBin(CoffeeTruck truck, StorageBin bin, BinContent ingredient, double quantity){

        truck.assignItemToBin(bin, ingredient, quantity);
    }

    public void emptyBin(CoffeeTruck truck, StorageBin bin){

        truck.emptyBin(bin);
    }

    public void emptyAllBins(CoffeeTruck truck){

        truck.emptyAllBins();
    }

    public boolean validBinNumber(int binNumber){

        return truckManager.checkBinNumber(binNumber);
    }

    public int getRegularTruckCount(){

        return truckManager.getRegularTruckCount();
    }

    public int getSpecialTruckCount(){

        return truckManager.getSpecialTruckCount();
    }

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
