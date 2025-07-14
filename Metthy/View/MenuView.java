package Metthy.View;

public class MenuView extends View{

    public MenuView(){

        super();
    }

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

    public int getSimulationMenuInput(){

        int option;

        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    public void displaySimulationMenu(){

        System.out.println("\n=== Simulation Menu ===");
        System.out.println("1 - Prepare Coffee Drinks");
        System.out.println("2 - View Truck Information");
        System.out.println("3 - Truck Maintenance");
        System.out.println("4 - Exit to Main Menu");
        System.out.println("Select an Option: ");
    }

    public int getCoffeeMenuInput(){

        int option;

        option = scanner.nextInt();
        scanner.nextLine(); //Absorb new line

        return option;
    }

    public void displayCoffeeMenu(){

        System.out.println("\n=== Coffee Menu ===");
        System.out.println("1 - Prepare Drink");
        System.out.println("2 - Exit Menu");
        System.out.println("Select an Option: ");
    }
}
