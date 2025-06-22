package Metthys_Folder;

import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    TruckManager manager = new TruckManager();

    public void displayMenu(){

        int option;

        System.out.println("Welcome to Java Jeeps Coffee Truck Simulator\n");

        do{
            System.out.println("Main Menu");
            System.out.println("1 - Create a Coffee Truck");
            System.out.println("2 - Simulate Coffee Truck Business");
            System.out.println("3 - View Dashboard");
            System.out.println("4 - Exit");

            System.out.println("Select an option: ");
            option = scanner.nextInt();

            switch(option){

                case 1:
                    manager.createTruck();

                    break;
                case 2:
                    System.out.println("Simulating Coffee Trucks...");
                    // TODO: Add simulation logic
                    break;
                case 3:
                    System.out.println("Displaying Dashboard...");
                    manager.displayDashboard();
                    break;
                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }

            System.out.println();

        } while(option != 4);

        scanner.close();
    }
}
