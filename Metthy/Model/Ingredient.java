package Metthy.Model;

/** Abstract class representing an ingredient that can be stored in a truck's bin. */
public abstract class Ingredient implements BinContent{

    /** The name of the ingredient. */
    protected String type;

    /** The current quantity of the item. */
    protected double quantity;

    /** The maximum capacity of this item. */
    protected int capacity;

    /**
     * Constructs an Ingredient with a specific name and maximum capacity.
     *
     * @param type     the name/type of the ingredient
     * @param capacity the max capacity for this item
     */
    public Ingredient(String type, int capacity){

        this.type = type;
        this.capacity = capacity;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the ingredient.
     */
    public String getName() {

        return type;
    }

    /**
     * Returns the maximum capacity of the item.
     *
     * @return the max capacity of the item
     */
    public int getCapacity() {

        return capacity;
    }

    /**
     * Sets the current quantity of ingredients.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {

        this.quantity = quantity;
    }

    /**
     * Returns the current quantity of the item.
     *
     * @return the quantity of the item.
     */
    public double getQuantity() {

        return quantity;
    }

    /**
     * Adds more ingredients to the current quantity.
     *
     * @param amount number of ingredients to add
     */
    public void addQuantity(double amount){

        quantity += amount;
    }

    /**
     * Deducts ingredients from the current quantity.
     *
     * @param amount number of ingredients to deduct
     */
    public void useQuantity(double amount){

        quantity -= amount;
    }

    /**
     * Creates and returns a duplicate of the ingredient object.
     *
     * @return a copy of this ingredient
     */
    public abstract Ingredient clone();

    /**
     * Returns the string representation of the ingredient.
     *
     * @return the name of the ingredient
     */
    @Override
    public String toString() {

        return type;
    }
}
