package Metthy.Model;

public class LargeCup extends Cup{

    public LargeCup(){

        super("Large Cup", 40);
    }

    @Override
    public LargeCup clone() {

        return new LargeCup();
    }
}
