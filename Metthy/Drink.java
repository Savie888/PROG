package Metthy;

/**
 * This class represents a coffee drink with a coffee type, size, and price.
 */
public class Drink {

    private final String coffeeType;
    private final String size;
    private double price;

    /**
     * Constructs a Drink object with the specified type, size, and price.
     *
     * @param coffeeType The type of coffee (e.g., "Latte", "Americano").
     * @param size       The size of the drink (e.g., "Small", "Medium", "Large").
     * @param price      The price of the drink.
     */
    public Drink(String coffeeType, String size, double price){

        this.coffeeType = coffeeType;
        this.size = size;
        this.price = price;
    }

    /**
     * Returns the type of coffee.
     *
     * @return The coffee type.
     */
    public String getCoffeeType(){

        return coffeeType;
    }

    /**
     * Returns the size of the drink.
     *
     * @return The drink size.
     */
    public String getSize(){

        return size;
    }

    /**
     * Returns the price of the drink.
     *
     * @return The drink price.
     */
    public double getPrice(){

        return price;
    }

    /**
     * Sets the price of the drink.
     *
     * @param price The new price to set.
     */
    public void setPrice(double price){

        this.price = price;
    }
}
