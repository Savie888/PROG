package Metthy.Model;

public class MediumCup extends Cup{

    public MediumCup(){

        super("Medium Cup", 64, 0, 12.0);
    }

    @Override
    public MediumCup clone() {

        return new MediumCup();
    }
}
