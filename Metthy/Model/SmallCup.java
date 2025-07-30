package Metthy.Model;

public class SmallCup extends Cup{

    public SmallCup(){

        super("Small Cup", 80);
    }

    @Override
    public SmallCup clone() {

        return new SmallCup();
    }

}
