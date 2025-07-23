package Metthy.Model;

public class LargeCup extends Cup{

    public LargeCup(){

        super("Large Cup", 40, 0, 16.0);
    }

    @Override
    public LargeCup clone() {

        return new LargeCup();
    }
}
