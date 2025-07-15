package Metthy.Model;

public class AddOn {

    private final String type;
    private final double amount;

    public AddOn(String name, double amount){

        this.type = name;
        this.amount = amount;
    }

    public String getType(){

        return type;
    }

    public double getAmount(){

        return amount;
    }
}
