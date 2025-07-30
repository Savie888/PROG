package Metthy.Model;

/** This class represents the "Coffee Bean" ingredient used in coffee drink preparation. */
public class CoffeeBean extends Ingredient{

    /**
     * Constructs a CoffeeBean object.
     * The name is set to "Coffee Bean" and the capacity to 1008 grams.
     */
    public CoffeeBean(){

        super("Coffee Bean", 1008);
    }

    /**
     * Creates and returns a new copy of this CoffeeBean object.
     *
     * @return a new instance of CoffeeBean
     */
    @Override
    public CoffeeBean clone() {

        return new CoffeeBean();
    }
}
