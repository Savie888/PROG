package Metthy;

public class Drink {

    private String coffeeType;
    private String size;
    private double price;

    public Drink(String coffeeType, String size, double price){

        this.coffeeType = coffeeType;
        this.size = size;
        this.price = price;
    }

    public String getCoffeeType(){

        return coffeeType;
    }

    public String getSize(){

        return size;
    }

    public double getPrice(){

        return price;
    }


}
