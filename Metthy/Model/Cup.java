package Metthy.Model;

public abstract class Cup implements BinContent{

    protected String size;
    protected double quantity;
    protected int capacity;

    public Cup(String size, int capacity){

        this.size = size;
        this.capacity = capacity;
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


    public abstract Cup clone();

    @Override
    public String toString() {

        return size;
    }
}
