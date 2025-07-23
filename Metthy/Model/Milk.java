package Metthy.Model;

public class Milk extends Ingredient{

    public Milk(){

        super("Milk", 640);
    }

    @Override
    public Milk clone() {
        return new Milk(); // No try-catch, just create a new one
    }
}
