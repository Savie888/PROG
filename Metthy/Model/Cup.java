package Metthy.Model;

/**
 * Abstract class representing a Cup used in drinks.
 * Implements BinContent interface.
 */
public abstract class Cup implements BinContent{

    /** The size of the cup (e.g., Small, Medium, Large). */
    protected String size;

    /** The maximum number of cups this bin can store. */
    protected int capacity;

    /** The current number of cups in the bin. */
    protected double quantity;

    /**
     * Constructs a Cup object with the given size and capacity.
     *
     * @param size     name/label of the cup (e.g., "Small Cup")
     * @param capacity maximum storage limit
     */
    public Cup(String size, int capacity){

        this.size = size;
        this.capacity = capacity;
    }

    /**
     * Returns the size the cup.
     *
     * @return the cup size
     */
    public String getName() {

        return size;
    }

    /**
     * Returns the capacity of the cup.
     *
     * @return the capacity
     */
    public int getCapacity() {

        return capacity;
    }

    /**
     * Sets the current quantity of cups.
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
     * Adds more cups to the current quantity.
     *
     * @param amount number of cups to add
     */
    public void addQuantity(double amount){

        quantity += amount;
    }

    /**
     * Deducts cups from the current quantity.
     *
     * @param amount number of cups to deduct
     */
    public void useQuantity(double amount){

        quantity -= amount;
    }

    /**
     * Creates and returns a duplicate of this Cup object.
     *
     * @return a copy of this Cup
     */
    public abstract Cup clone();

    /**
     * Returns the string representation of the cup.
     *
     * @return the size name of the cup
     */
    @Override
    public String toString() {

        return size;
    }
}
