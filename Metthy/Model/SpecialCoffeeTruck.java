package Metthy.Model;

import java.util.ArrayList;

/**
 * This class represents a special coffee truck
 */
public class SpecialCoffeeTruck extends CoffeeTruck {

    /**
     * Constructs a special coffee truck with the given name and location.
     * Initializes 10 storage bins and an empty sales log.
     *
     * @param name         the name of the coffee truck
     * @param location     the initial location of the truck
     */
    public SpecialCoffeeTruck(String name, String location, TruckManager truckManager, DrinkManager drinkManager){

        //Call regular coffee truck constructor
        super(name, location, truckManager, drinkManager);

        int i;

        //Create the 8 storage bins
        for(i = 0; i <= 7; i++)
            bins.add(new StorageBin(i + 1));

        //Create the 2 extra storage bins for syrups
        getBins().add(new StorageBin(9));
        getBins().add(new StorageBin(10));
    }

    public ArrayList<BinContent> selectAddOns(){

        String repeat;
        boolean validAddOn;
        BinContent addOn;
        ArrayList<BinContent> addOns = new ArrayList<>();

        if(!drinkView.hasAvailableSyrup(bins))
            System.out.println("No syrup available");

        else{
            do{
                drinkView.displayAvailableSyrup(bins);

                addOn = drinkView.selectAddOn(bins);

                validAddOn = hasSufficientSyrup(addOn.getName());

                if(validAddOn){
                    addOns.add(addOn);
                    addOn.useQuantity(1); //Deduct ingredients from syrup bins
                }

                System.out.println("Continue adding? (yes/no): ");
                repeat = drinkView.yesOrNo();

            } while(repeat.equalsIgnoreCase("yes"));
        }

        return addOns;
    }

    public boolean hasSufficientSyrup(String syrupType){

        boolean validAddOns = true;

        StorageBin syrupBin = findBin(syrupType); //Find the bin containing the specified add-on

        if(syrupBin == null){
            drinkView.missingSyrupBinMessage(syrupType);
            validAddOns = false;
        }

        else if(syrupBin.getItemQuantity() < 1){
            drinkView.notEnoughSyrupMessage(syrupType);
            validAddOns = false;
        }

        return validAddOns;
    }

    public int selectExtraShots(double coffeeGrams, double remainingCoffeeGrams){

        int shots, maxShots;

        maxShots = (int) (remainingCoffeeGrams / coffeeGrams);

        do{
            System.out.printf("Enter number of extra espresso shots (0 - %d): ", maxShots);
            shots = scanner.nextInt();
            scanner.nextLine(); //Clear excess line

            if(shots < 0 || shots > maxShots)
                System.out.println("Invalid number entered");

        } while(shots < 0 || shots > maxShots);

        return shots;
    }

    /**
     * Records a drink sale to the truck's sales log.
     *
     * @param coffeeType   the type of coffee drink.
     * @param size         the size of the drink.
     * @param coffeeGrams  the grams of coffee beans used.
     * @param milk         the ounces of milk used.
     * @param water        the ounces of water used.
     * @param addOns       list of syrup or other optional add-ons.
     * @param extraShots   number of extra espresso shots.
     * @param price        the price of the drink.
     */
    public void recordSpecialSale(String coffeeType, String size, String brewType, double coffeeGrams,
                                  double milk, double water, ArrayList<BinContent> addOns, int extraShots, double price){

        int i;
        double extraCoffeeGrams;

        String drinkInfo;
        StringBuilder ingredients = new StringBuilder();
        StringBuilder addOnDetails = new StringBuilder();

        //Build base ingredient info string (without extra shots or add-ons)
        ingredients.append(String.format("%.2f g beans, %.2f oz milk, %.2f oz water", coffeeGrams, milk, water));

        //Build formatted drink info line
        drinkInfo = String.format("Drink: %s %s (%s)", size, coffeeType, brewType);

        //Include extra shot info if any
        if(extraShots > 0){
            extraCoffeeGrams = coffeeGrams * extraShots;
            ingredients.append(String.format(" + %d extra shot%s (%.2f g beans)", extraShots, (extraShots == 1 ? "" : "s"), extraCoffeeGrams));
        }

        //Build add-on details, if any
        if(!addOns.isEmpty()){
            addOnDetails.append("Add-Ons: ");

            for(i = 0; i < addOns.size(); i++){
                BinContent addOn = addOns.get(i);
                addOnDetails.append(String.format("%.1f oz %s", 1.0, addOn.getName()));

                if(i < addOns.size() - 1){
                    addOnDetails.append(", ");
                }
            }
        }

        //If no add-ons, display "None"
        String addOnText = addOnDetails.isEmpty() ? "No Add-Ons" : addOnDetails.toString();

        //Format and add to the sales log
        salesLog.add(String.format("%-30s | %-45s | %s | $%.2f", drinkInfo, ingredients, addOnText, price));
    }

    /**
     * Handles the process of preparing a drink.
     *
     */

    @Override
    public void prepareDrink(){

        int i, extraShots = 0;
        String add, extra;
        double coffeeGrams, milkOz, waterOz, remainingCoffeeGrams, price;
        double extraCoffeeGrams = 0, extraShotCost = 0;
        double[] ingredients;
        ArrayList<BinContent> addOns = new ArrayList<>();

        System.out.println("\n--- Prepare Coffee Drink ---");
        String coffeeType = drinkView.selectDrinkType();
        String coffeeSize = drinkView.selectDrinkSize();
        String brewType = drinkView.selectBrewType();
        double ratio = drinkView.getBrewRatio(brewType);

        Drink drink = drinkManager.getDrink(coffeeType, coffeeSize);

        ingredients = drinkManager.getAdjustedIngredients(coffeeType, coffeeSize, ratio); //Get the ingredients needed for the drink
        drinkView.showIngredients(coffeeType, coffeeSize, brewType, ingredients); //Show required ingredients

        StorageBin beanBin = findBin("Coffee Bean"); //Find bin with coffee beans
        StorageBin milkBin = findBin("Milk"); //Find bin with milk
        StorageBin waterBin = findBin("Water"); //Find bin with water
        StorageBin cupBin = findBin(coffeeSize + " Cup"); //Find bin with specified cup size
        StorageBin[] bins = {beanBin, milkBin, waterBin, cupBin};

        //Check if storage bins have sufficient ingredients and syrup quantities
        if(drinkManager.hasSufficientIngredients(bins, ingredients)){

            coffeeGrams = ingredients[0];
            milkOz = ingredients[1];
            waterOz = ingredients[2];
            price = drink.getPrice();

            drinkManager.useIngredients(bins, ingredients); //Deduct ingredients from storage bins

            remainingCoffeeGrams = beanBin.getItemQuantity(); //Get remaining coffee bean quantity

            System.out.println("Add syrup add-ons? (yes/no)");
            add = scanner.nextLine();

            if(add.equalsIgnoreCase("yes"))
                addOns = selectAddOns();

            System.out.println("Add extra espresso shots? (yes/no): ");
            extra = scanner.nextLine();

            if(extra.equalsIgnoreCase("yes")){
                extraShots = selectExtraShots(coffeeGrams, remainingCoffeeGrams);
                extraCoffeeGrams = coffeeGrams * extraShots;
                beanBin.useQuantity(coffeeGrams * extraShots);
                extraShotCost = extraShots * drinkManager.getExtraShotPrice();
            }

            System.out.printf("\n>>> Preparing %s Cup...\n", coffeeSize);
            System.out.printf(">>> Brewing %s Espresso - %.2f grams of coffee...\n", brewType, coffeeGrams);

            if(milkOz > 0)
                System.out.println(">>> Adding Milk...");

            if(coffeeType.equalsIgnoreCase("Americano"))
                System.out.println(">>> Adding Water...");

            if(!addOns.isEmpty()){
                for(i = 0; i < addOns.size(); i++){
                    BinContent addOn = addOns.get(i);
                    System.out.println(">>> Adding " + addOn.getName() + " Syrup");
                    price += drinkManager.getSyrupOzPrice();
                }
            }

            if(extraShots != 0){
                System.out.printf(">>> Added %d extra shot%s (%.2f g beans)\n", extraShots, extraShots == 1 ? "" : "s", extraCoffeeGrams);
                price += extraShotCost ;
            }

            System.out.printf(">>> %s Done!\n", coffeeType);

            System.out.printf("Total Price: $%.2f\n", price);

            addToTotalSales(price); //Update truck's total sales
            recordSpecialSale(coffeeType, coffeeSize, brewType, coffeeGrams, milkOz, waterOz, addOns, extraShots, price); //Update sales log
        }

        else
            System.out.println("Not enough ingredients. Drink preparation cancelled.");

    }
}
