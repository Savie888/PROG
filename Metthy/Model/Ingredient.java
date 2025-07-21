package Metthy.Model;

public abstract class Ingredient implements BinContent{


    protected String type;
    protected double quantity;
    protected int capacity;
    protected double cost;

    public Ingredient(String type, int capacity, double cost){

        this.type = type;
        this.capacity = capacity;
        this.cost = cost;
    }

    @Override
    public boolean store() {

        return true;
    }

    @Override
    public boolean retrieve() {

        return true;
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

    public double getCost() {

        return cost;
    }

}
