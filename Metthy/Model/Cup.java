package Metthy.Model;

public abstract class Cup implements BinContent{

    protected String size;
    protected double quantity;
    protected int capacity;
    protected double cost;

    public Cup(String size, int capacity, double cost){

        this.size = size;
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

    public String getName() {

        return size;
    }

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
