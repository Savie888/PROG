package Metthy.Model;

public abstract class Ingredient implements BinContent{


    protected String type;
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
    public String getName() {

        return type;
    }

    public int getCapacity() {

        return capacity;
    }

    public double getCost() {

        return cost;
    }

}
