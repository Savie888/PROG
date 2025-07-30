package Metthy.Model;

/** This class represents the "Milk" ingredient used in coffee drink preparation. */
public class Milk extends Ingredient{

    /**
     * Constructs a Milk object.
     * The name is set to "Milk" and the capacity to 640 ounces.
     */
    public Milk(){

        super("Milk", 640);
    }

    /**
     * Creates and returns a new copy of this Milk object.
     *
     * @return a new instance of Milk
     */
    @Override
    public Milk clone() {
        return new Milk(); // No try-catch, just create a new one
    }
}
