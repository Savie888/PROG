package Metthy.Model;

public class MediumCup extends Cup{

    public MediumCup(){

        super("Medium Cup", 64);
    }

    @Override
    public MediumCup clone() {

        return new MediumCup();
    }
}
