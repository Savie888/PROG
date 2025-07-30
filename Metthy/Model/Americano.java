package Metthy.Model;
/**
 * The Americano class represents a coffee drink of type "Americano".
 * It inherits from the Drink class and sets the drink name and size.
 */

public class Americano extends Drink{

    /**
     * Constructs an Americano drink with the specified size.
     * Calls the superclass constructor to initialize the name and size.
     *
     * @param size the size of the drink (e.g., "Small", "Large")
     */

    public Americano(String size){

        super("Americano", size);
    }
}
