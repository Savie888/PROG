package Metthy.View;

import java.util.Scanner;

public class View {

    private final Scanner scanner = new Scanner(System.in);

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
    public void displayMainMenu() {

        System.out.println("Welcome to Java Jeeps Coffee Truck Simulator");

        System.out.print("\n=== Main Menu ===\n");
        System.out.println("1 - Create a Coffee Truck");
        System.out.println("2 - Decommission a Coffee Truck");
        System.out.println("3 - Simulate Coffee Truck Business");
        System.out.println("4 - View Dashboard");
        System.out.println("5 - Exit\n");
    }

    public int getMenuSelection(){

        int option;

        System.out.println("Select an option: ");
        option = scanner.nextInt();
        scanner.nextLine(); //Clear excess line

        return option;
    }

    /**
     * Prompts user to enter a unique truck name
     *
     * @return the unique name entered
     */
    public String enterUniqueName(){

        String name;

        System.out.print("Enter truck name: ");
        name = scanner.nextLine();

        return name;
    }

    /**
     * Prompts user to enter a unique truck location
     *
     * @return the unique location entered
     */
    public String enterUniqueLocation(){

        String location;

        System.out.print("Enter truck location: ");
        location = scanner.nextLine();

        return location;
    }

    public int enterTruckType(){

        int option;

        System.out.println("Choose Truck Type:");
        System.out.println("1. Regular Coffee Truck");
        System.out.println("2. Special Coffee Truck");
        option = scanner.nextInt();
        scanner.nextLine(); //Clear excess line

        return option;
    }

    public String yesOrNo(){

        String choice;

        choice = scanner.nextLine();

        return choice;
    }

    public String setLoadoutPrompt(){

        String set;

        //Option to set up storage bins
        System.out.println("Set up storage bins?: (yes/no)");
        set = yesOrNo();

        return set;
    }

    public String repeatPrompt(){

        String repeat;

        System.out.println("Coffee Truck Created\n");

        System.out.print("Create another truck? (yes/no): ");
        repeat = scanner.nextLine();

        return repeat;
    }
}

