package Metthy.Model;

/** This class represents the "Water" ingredient used in coffee drink preparation. */
public class Water extends Ingredient{

    /**
     * Constructs a Water object.
     * The name is set to "Water" and the capacity to 640 ounces.
     */
    public Water(){

        super("Water", 640);
    }

    /**
     * Creates and returns a new copy of this Water object.
     *
     * @return a new instance of Water
     */
    @Override
    public Water clone() {
        return new Water();
    }
}
