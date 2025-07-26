package Metthy.Model;

public class CoffeeBean extends Ingredient{

    public CoffeeBean(){

        super("Coffee Bean", 1008);
    }

    @Override
    public CoffeeBean clone() {

        return new CoffeeBean();
    }


}
