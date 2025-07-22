package Metthy.Model;

public abstract class Cup implements BinContent{

    protected String size;
    protected double quantity;
    protected int capacity;
    protected double cost;
    protected double volume;

    public Cup(String size, int capacity, double cost, double volume){

        this.size = size;
        this.capacity = capacity;
        this.cost = cost;
        this.volume = volume;
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

    public void addQuantity(double amount){

        quantity += amount;
    }

    public void useQuantity(double amount){

        quantity -= amount;
    }

    public double getCost() {

        return cost;
    }

    public double getVolume() {

        return volume;
    }
}
