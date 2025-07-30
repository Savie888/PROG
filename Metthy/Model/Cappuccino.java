package Metthy.Model;

/**
 * The Cappuccino class represents a coffee drink of type "Cappuccino".
 * It extends the Drink class and sets its name and size upon creation.
 */

public class Cappuccino extends Drink{

    /**
     * Constructs a Cappuccino drink with the specified size.
     * Calls the superclass constructor to initialize the name and size.
     *
     * @param size the size of the drink (e.g., "Small", "Medium", "Large")
     */

    public Cappuccino(String size){

        super("Cappuccino", size);
    }
}
