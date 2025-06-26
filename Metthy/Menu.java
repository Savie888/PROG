package Metthy;

import java.util.Scanner;

/**
 * This class handles the main menu interface for the Coffee Truck Simulator.
 * <p>
 * This class displays the main menu to the user, processes user input,
 * and delegates actions such as truck creation, simulation, and dashboard viewing
 * to the {@link TruckManager} class.
 * </p>
 */

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private TruckManager manager = new TruckManager();

    /**
     * Displays the main menu and handles user interaction.
     * <p>
     * The menu allows the user to:
     * <ul>
     *     <li>Create a coffee truck</li>
     *     <li>Simulate business actions like making drinks</li>
     *     <li>View sales and inventory dashboard</li>
     *     <li>Exit the program</li>
     * </ul>
     * </p>
     * The method runs in a loop until the user chooses to exit.
     */
    public void displayMenu(){

        int option;

        System.out.println("Welcome to Java Jeeps Coffee Truck Simulator");

        do{
            System.out.println("\n=== Main Menu ===");
            System.out.println("1 - Create a Coffee Truck");
            System.out.println("2 - Decommission a Coffee Truck");
            System.out.println("3 - Simulate Coffee Truck Business");
            System.out.println("4 - View Dashboard");
            System.out.println("5 - Exit");

            System.out.println("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch(option){

                case 1:
                    manager.createTruck();
                    break;
                case 2:
                    manager.removeTruck();
                    break;
                case 3:
                    manager.simulateMenu();
                    break;
                case 4:
                    manager.displayDashboard();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }

            System.out.println();

        } while(option != 5);

        scanner.close();
    }
}
