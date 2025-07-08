package Metthy;

/**
 * This class represents a coffee drink with a coffee type, size, and price.
 */
public class Drink {

    /**
     * The type of coffee.
     */
    private final String coffeeType;
    /**
     * The size of the drink (e.g., Small, Medium, Large).
     */
    private final String size;
    /**
     * The brew type of the drink (e.g., Strong, Light, Standard, Custom).
     */
    private String brewType = "Standard";
    /**
     * The calculated price of the drink based on its ingredients.
     */
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
     * Returns the brew type of the drink.
     *
     * @return The drink price.
     */
    public String getBrewType(){

        return brewType;
    }

    /**
     * Sets the price of the drink.
     *
     * @param price The new price to be set.
     */
    public void setPrice(double price){

        this.price = price;
    }

    /**
     * Sets the brew type of the drink.
     *
     * @param brewType The new brew type to be set.
     */
    public void setBrewType(String brewType){

        this.brewType = brewType;
    }
}
