package Metthy;

import java.util.Scanner;
import java.util.ArrayList;

public class DrinkManager {

    //private CoffeeTruck truck;
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Drink> drinks = new ArrayList<>();

    public void setPrices(){

        int i, j;
        String[] drinkTypes = { "Americano", "Latte", "Cappuccino" };
        String[] sizes = { "Small", "Medium", "Large" };
        int[] ounces = { 8, 12, 16 }; //Size of cups in ounces

        System.out.println("Enter price of coffee bean per gram: ");
        double COFFEE_PRICE_PER_GRAM = scanner.nextDouble();

        System.out.println("Enter price of milk per ounce: ");
        double MILK_PRICE_PER_OUNCE = scanner.nextDouble();

        System.out.println("Enter price of water per ounce: ");
        double WATER_PRICE_PER_OUNCE = scanner.nextDouble();

        for(i = 0; i < drinkTypes.length; i++){

            String drink = drinkTypes[i];

            for(j = 0; j < sizes.length; j++){

                String size = sizes[j];
                int totalOunces = ounces[j];

                double espressoOz = 0;
                double milkOz = 0;
                double waterOz = 0;

                if(drink.equals("Americano")){

                    espressoOz = totalOunces / 3.0;
                    waterOz = totalOunces - espressoOz;
                }

                else if (drink.equals("Latte")){

                    espressoOz = totalOunces / 5.0;
                    milkOz = totalOunces - espressoOz;
                }

                else if (drink.equals("Cappuccino")){

                    espressoOz = totalOunces / 3.0;
                    milkOz = totalOunces - espressoOz;
                }

                double coffeeGrams = espressoOz * 1.49208;
                double espressoWater = espressoOz * 0.94737;

                waterOz += espressoWater;

                double coffeeCost = coffeeGrams * COFFEE_PRICE_PER_GRAM;
                double milkCost = milkOz * MILK_PRICE_PER_OUNCE;
                double waterCost = waterOz * WATER_PRICE_PER_OUNCE;

                double price = (coffeeCost + milkCost + waterCost) * 1.5;
                price = Math.round(price * 100.0) / 100.0;

                drinks.add(new Drink(drink, size, price));
            }
        }
    }

    public void displayDrinkMenu(){

        int i;
        System.out.println("\n--- Drinks Menu ---");

        for(i = 0; i < drinks.size(); i++){

            Drink drink = drinks.get(i);
            System.out.printf("%s %s - $%.2f\n", drink.getSize(), drink.getCoffeeType(), drink.getPrice());
        }
    }
}
