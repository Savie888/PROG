package Metthy.Model;

public class Syrup extends Ingredient{

    public Syrup(String name){

        super(name, 640);
    }

    @Override
    public Syrup clone(){

        return this;
    }
}
