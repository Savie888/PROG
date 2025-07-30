package Metthy.Model;

/** This class represents the "Syrup" ingredient used in coffee drink preparation. */
public class Syrup extends Ingredient{

    /**
     * Constructs a Syrup object.
     * The capacity is set to 640 ounces.
     *
     * @param name the name of the syrup
     */
    public Syrup(String name){

        super(name, 640);
    }

    /**
     * Creates and returns a new copy of this Syrup object.
     *
     * @return a new instance of Syrup
     */
    @Override
    public Syrup clone(){

        return this;
    }
}
