package Metthy;

//Implement MVC
    //Continue on method refactoring
    //Error messages for duplicate name and location

//Work on drinks
    //use Cup object in getCupSize
    //transaction format

//Change currency to peso

//GUI
    //Start with main menu


import Metthy.Controller.MenuController;
import Metthy.View.MenuView;


/**
 * This is a simulation of a Coffee Truck Business.
 * <p>
 * The application allows users to create and manage coffee trucks.
 * Each truck maintains its own inventory, sales data, and drink preparation system.
 * Users can perform actions such as:
 * <ul>
 *     <li>Create Regular or Special trucks</li>
 *     <li>Have trucks prepare drinks</li>
 *     <li>Track and display total sales revenue</li>
 *     <li>Monitor ingredient usage and storage bins</li>
 * </ul>
 * <p>
 * This class contains the main method to start the program.
 * @author Adrian Matthew Dee and Queenie Salao
 * @version 1.0
 */
public class Driver {

    public static void main(String[] args){

        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView);
        menuController.start();
    }
}
