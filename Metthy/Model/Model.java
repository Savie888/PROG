package Metthy.Model;

import Metthy.DrinkManager;

import java.util.ArrayList;

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
