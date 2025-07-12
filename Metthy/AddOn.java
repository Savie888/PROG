package Metthy;

public class AddOn {

    public String type;
    public double amount;

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
