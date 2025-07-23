package Metthy.Model;

public class SmallCup extends Cup{

    public SmallCup(){

        super("Small Cup", 80, 0, 8.0);
    }

    @Override
    public SmallCup clone() {

        return new SmallCup();
    }

}
