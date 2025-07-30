package Metthy.Model;

/**
 * This class represents a small cup.
 * Extends the abstract Cup class.
 */
public class SmallCup extends Cup{

    /**
     * Constructs a SmallCup object.
     * Name is set to "Small" and capacity to 80 units.
     */
    public SmallCup(){

        super("Small Cup", 80);
    }

    /**
     * Creates and returns a copy of the SmallCup object. .
     *
     * @return a new instance of SmallCup.
     */
    @Override
    public SmallCup clone() {

        return new SmallCup();
    }

}
