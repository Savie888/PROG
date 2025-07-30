package Metthy.Model;

/**
 * This class represents a medium cup.
 * Extends the abstract Cup class.
 */
public class MediumCup extends Cup{

    /**
     * Constructs a MediumCup object.
     * Name is set to "Medium" and capacity to 64 units.
     */
    public MediumCup(){

        super("Medium Cup", 64);
    }

    /**
     * Creates and returns a copy of the MediumCup object. .
     *
     * @return a new instance of MediumCup.
     */
    @Override
    public MediumCup clone() {

        return new MediumCup();
    }
}
