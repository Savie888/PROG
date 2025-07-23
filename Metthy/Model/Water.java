package Metthy.Model;

public class Water extends Ingredient{

    public Water(){

        super("Water", 640);
    }

    @Override
    public Water clone() {
        return new Water();
    }
}
