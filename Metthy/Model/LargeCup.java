package Metthy.Model;

/**
 * This class represents a large cup.
 * Extends the abstract Cup class.
 */
public class LargeCup extends Cup{

    /**
     * Constructs a LargeCup object.
     * Name is set to "Large" and capacity to 40 units.
     */
    public LargeCup(){

        super("Large Cup", 40);
    }

    /**
     * Creates and returns a copy of the LargeCup object. .
     *
     * @return a new instance of LargeCup
     */
    @Override
    public LargeCup clone() {

        return new LargeCup();
    }
}
