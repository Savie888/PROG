package Metthy;

//Continue testing simulate menu
//Test Drink Manager for duplication
//Test prepare Drink after modifications
//Starting Test Script

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

        Menu menu = new Menu(); //Create the menu

        menu.displayMenu(); //Display the menu
    }
}
