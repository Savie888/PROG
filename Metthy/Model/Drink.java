package Metthy.Model;

/**
 * This class represents a coffee drink with a coffee type, size, and price.
 */
public abstract class Drink {

    /**
     * The type of coffee.
     */
    protected final String type;
    /**
     * The size of the drink (e.g., Small, Medium, Large).
     */
    protected final String size;
    /**
     * The price of the drink.
     */
    protected double price;

    /**
     * Constructs a Drink object with the specified type, size, and price.
     *
     * @param coffeeType The type of coffee (e.g., "Latte", "Americano").
     * @param size       The size of the drink (e.g., "Small", "Medium", "Large").
     */
    public Drink(String coffeeType, String size){

        this.type = coffeeType;
        this.size = size;
    }

    /**
     * Returns the type of coffee.
     *
     * @return The coffee type.
     */
    public String getType(){

        return type;
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
     * @param price The new price to be set.
     */
    public void setPrice(double price){

        this.price = price;
    }

    @Override
    public String toString(){

        return String.format("%s (%s) - ₱%.2f", type, size, price);
    }
}
