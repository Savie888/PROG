package Metthy.Model;

public class Model {

    public Model(){

    }

    public boolean checkYesOrNo(String choice){

        boolean flag = false;

        if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))
            flag = true;

        return flag;
    }

    public boolean isYes(String choice){

        boolean flag = false;

        if(choice.equalsIgnoreCase("yes"))
            flag = true;

        return flag;
    }
}
