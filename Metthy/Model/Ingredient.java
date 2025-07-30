package Metthy.Model;

public abstract class Ingredient implements BinContent{

    protected String type;

    /**
     * The current quantity of the item.
     */
    protected double quantity;

    /**
     * The maximum capacity of this item.
     */
    protected int capacity;

    public Ingredient(String type, int capacity){

        this.type = type;
        this.capacity = capacity;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item.
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

    public void addQuantity(double amount){

        quantity += amount;
    }

    public void useQuantity(double amount){

        quantity -= amount;
    }

    public abstract Ingredient clone();

    @Override
    public String toString() {

        return type;
    }
}
