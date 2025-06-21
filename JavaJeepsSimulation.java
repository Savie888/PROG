import java.util.Scanner; // inputs from console

public class JavaJeepsSimulation {
    private static final int MAX_TRUCKS = 10; // max 
    private CoffeeTruck[] trucks; // object array 
    private int truckCount; // starts from 0
    private Scanner scanner;
    
    public JavaJeepsSimulation() {
        trucks = new CoffeeTruck[MAX_TRUCKS];
        truckCount = 0;
        scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("=== JAVAJEEPS Coffee Truck Business Simulation ===");
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    createCoffeeTruck();
                    break;
                case 2:
                    simulateTruckFeatures();
                    break;
                case 3:
                    showDashboard();
                    break;
                case 4:
                    System.out.println("Thank you for using JavaJeeps! Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Create a Coffee Truck");
        System.out.println("2. Simulate Coffee Truck Features");
        System.out.println("3. Dashboard");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private void createCoffeeTruck() {
        if (truckCount >= MAX_TRUCKS) {
            System.out.println("Maximum number of trucks reached!"); 
            return;
        }
        
        System.out.println("\n=== CREATE COFFEE TRUCK ===");
        System.out.println("1. Regular Coffee Truck (JavaJeep)");
        System.out.println("2. Special Coffee Truck (JavaJeep+)");
        System.out.print("Enter truck type: ");
        
        int type = getIntInput();
        System.out.print("Enter truck location: ");
        scanner.nextLine(); // consume newline
        String location = scanner.nextLine();
        
        // Check if location is already taken
        for (int i = 0; i < truckCount; i++) {
            if (trucks[i].getLocation().equalsIgnoreCase(location)) {
                System.out.println("Location already occupied by another truck!");
                return;
            }
        }
        
        CoffeeTruck truck;
        if (type == 1) {
            truck = new RegularCoffeeTruck(location);
        } else if (type == 2) {
            truck = new SpecialCoffeeTruck(location);
        } else {
            System.out.println("Invalid truck type!");
            return;
        }
        
        // Initial loadout
        performInitialLoadout(truck);
        
        trucks[truckCount] = truck;
        truckCount++;
        
        System.out.println("Coffee truck created successfully at " + location + "!");
    }
    
    private void performInitialLoadout(CoffeeTruck truck) {
        System.out.println("\n=== INITIAL LOADOUT ===");
        System.out.println("Configure your truck's storage bins:");
        
        for (int i = 0; i < truck.getStorageBins().length; i++) {
            System.out.println("\nBin " + (i + 1) + ":");
            System.out.println("1. Small Cup  2. Medium Cup  3. Large Cup");
            System.out.println("4. Coffee Beans  5. Milk  6. Water");
            if (truck instanceof SpecialCoffeeTruck && i >= 8) {
                System.out.println("7. Syrup Add-on");
            }
            System.out.print("Select item type: ");
            
            int itemType = getIntInput();
            System.out.print("Enter quantity: ");
            int quantity = getIntInput();
            
            Item item = createItem(itemType, quantity, truck instanceof SpecialCoffeeTruck && i >= 8);
            if (item != null) {
                truck.getStorageBins()[i].addItem(item, quantity);
            }
        }
        
        // Set prices
        setPrices(truck);
    }
    
    private Item createItem(int type, int quantity, boolean isSyrupBin) {
        switch (type) {
            case 1: return new Cup("Small", 8.0);
            case 2: return new Cup("Medium", 12.0);
            case 3: return new Cup("Large", 16.0);
            case 4: return new Ingredient("Coffee Beans", "grams");
            case 5: return new Ingredient("Milk", "fl oz");
            case 6: return new Ingredient("Water", "fl oz");
            case 7:
                if (isSyrupBin) {
                    System.out.print("Enter syrup flavor: ");
                    scanner.nextLine(); // consume newline
                    String flavor = scanner.nextLine();
                    return new Ingredient(flavor + " Syrup", "fl oz");
                }
                break;
        }
        System.out.println("Invalid item type!");
        return null;
    }
    
    private void setPrices(CoffeeTruck truck) {
        System.out.println("\n=== SET PRICES ===");
        System.out.print("Small Americano price: $");
        truck.getPriceList().setPrice("Small Americano", getDoubleInput());
        System.out.print("Medium Americano price: $");
        truck.getPriceList().setPrice("Medium Americano", getDoubleInput());
        System.out.print("Large Americano price: $");
        truck.getPriceList().setPrice("Large Americano", getDoubleInput());
        
        System.out.print("Small Latte price: $");
        truck.getPriceList().setPrice("Small Latte", getDoubleInput());
        System.out.print("Medium Latte price: $");
        truck.getPriceList().setPrice("Medium Latte", getDoubleInput());
        System.out.print("Large Latte price: $");
        truck.getPriceList().setPrice("Large Latte", getDoubleInput());
        
        System.out.print("Small Cappuccino price: $");
        truck.getPriceList().setPrice("Small Cappuccino", getDoubleInput());
        System.out.print("Medium Cappuccino price: $");
        truck.getPriceList().setPrice("Medium Cappuccino", getDoubleInput());
        System.out.print("Large Cappuccino price: $");
        truck.getPriceList().setPrice("Large Cappuccino", getDoubleInput());
        
        if (truck instanceof SpecialCoffeeTruck) {
            System.out.print("Extra shot price: $");
            truck.getPriceList().setPrice("Extra Shot", getDoubleInput());
            System.out.print("Syrup add-on price: $");
            truck.getPriceList().setPrice("Syrup Add-on", getDoubleInput());
        }
    }
    
    private void simulateTruckFeatures() {
        if (truckCount == 0) {
            System.out.println("No trucks available. Please create a truck first.");
            return;
        }
        
        System.out.println("\n=== SELECT TRUCK ===");
        for (int i = 0; i < truckCount; i++) {
            System.out.println((i + 1) + ". " + trucks[i].getType() + " at " + trucks[i].getLocation());
        }
        System.out.print("Select truck: ");
        
        int truckIndex = getIntInput() - 1;
        if (truckIndex < 0 || truckIndex >= truckCount) {
            System.out.println("Invalid truck selection!");
            return;
        }
        
        CoffeeTruck selectedTruck = trucks[truckIndex];
        
        while (true) {
            System.out.println("\n=== TRUCK FEATURES ===");
            System.out.println("1. Make Coffee Sale");
            System.out.println("2. View Truck Information");
            System.out.println("3. Restock Storage Bins");
            System.out.println("4. Maintenance");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    makeCoffeeSale(selectedTruck);
                    break;
                case 2:
                    viewTruckInformation(selectedTruck);
                    break;
                case 3:
                    restockStorageBins(selectedTruck);
                    break;
                case 4:
                    performMaintenance(selectedTruck);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void makeCoffeeSale(CoffeeTruck truck) {
        System.out.println("\n=== MAKE COFFEE SALE ===");
        System.out.println("1. Americano  2. Latte  3. Cappuccino");
        System.out.print("Select drink type: ");
        int drinkType = getIntInput();
        
        System.out.println("1. Small  2. Medium  3. Large");
        System.out.print("Select size: ");
        int sizeType = getIntInput();
        
        String[] drinkNames = {"", "Americano", "Latte", "Cappuccino"};
        String[] sizes = {"", "Small", "Medium", "Large"};
        
        if (drinkType < 1 || drinkType > 3 || sizeType < 1 || sizeType > 3) {
            System.out.println("Invalid selection!");
            return;
        }
        
        String drinkName = drinkNames[drinkType];
        String size = sizes[sizeType];
        double cupSize = (sizeType == 1) ? 8.0 : (sizeType == 2) ? 12.0 : 16.0;
        
        CoffeeOrder order = new CoffeeOrder(drinkName, size, cupSize);
        
        // Special truck customizations
        if (truck instanceof SpecialCoffeeTruck) {
            System.out.println("Brew strength: 1. Light  2. Standard  3. Strong");
            System.out.print("Select brew strength: ");
            int brewStrength = getIntInput();
            order.setBrewStrength(brewStrength);
            
            System.out.print("Extra shots (0 for none): ");
            int extraShots = getIntInput();
            order.setExtraShots(extraShots);
            
            System.out.print("Add syrup? (y/n): ");
            scanner.nextLine(); // consume newline
            String addSyrup = scanner.nextLine();
            if (addSyrup.equalsIgnoreCase("y")) {
                order.setHasSyrup(true);
            }
        }
        
        // Process the order
        boolean success = truck.processOrder(order);
        if (success) {
            double price = truck.getPriceList().getPrice(size + " " + drinkName);
            if (truck instanceof SpecialCoffeeTruck) {
                if (order.getExtraShots() > 0) {
                    price += order.getExtraShots() * truck.getPriceList().getPrice("Extra Shot");
                }
                if (order.isHasSyrup()) {
                    price += truck.getPriceList().getPrice("Syrup Add-on");
                }
            }
            System.out.println("Total Price: $" + String.format("%.2f", price));
        } else {
            System.out.println("Order failed - insufficient ingredients!");
        }
    }
    
    private void viewTruckInformation(CoffeeTruck truck) {
        System.out.println("\n=== TRUCK INFORMATION ===");
        System.out.println("Type: " + truck.getType());
        System.out.println("Location: " + truck.getLocation());
        
        System.out.println("\n--- Storage Bins ---");
        StorageBin[] bins = truck.getStorageBins();
        for (int i = 0; i < bins.length; i++) {
            System.out.println("Bin " + (i + 1) + ": " + bins[i].getContents());
        }
        
        System.out.println("\n--- Menu & Prices ---");
        truck.getPriceList().displayMenu();
        
        System.out.println("\n--- Sales Transactions ---");
        truck.getSalesHistory().displayTransactions();
    }
    
    private void restockStorageBins(CoffeeTruck truck) { 
        System.out.println("\n=== RESTOCK STORAGE BINS ===");
        System.out.println("1. Add items to bin");
        System.out.println("2. Replace bin contents");
        System.out.println("3. Empty bin");
        System.out.print("Select action: ");
        
        int action = getIntInput();
        System.out.print("Select bin number (1-" + truck.getStorageBins().length + "): ");
        int binNumber = getIntInput() - 1;
        
        if (binNumber < 0 || binNumber >= truck.getStorageBins().length) {
            System.out.println("Invalid bin number!");
            return;
        }
        
        StorageBin bin = truck.getStorageBins()[binNumber];
        
        switch (action) {
            case 1:
                System.out.print("Enter quantity to add: ");
                int quantity = getIntInput();
                bin.addQuantity(quantity);
                System.out.println("Added " + quantity + " items to bin " + (binNumber + 1));
                break;
            case 2:
                System.out.println("Select new item type:");
                System.out.println("1. Small Cup  2. Medium Cup  3. Large Cup");
                System.out.println("4. Coffee Beans  5. Milk  6. Water");
                if (truck instanceof SpecialCoffeeTruck && binNumber >= 8) {
                    System.out.println("7. Syrup Add-on");
                }
                System.out.print("Select: ");
                int itemType = getIntInput();
                System.out.print("Enter quantity: ");
                quantity = getIntInput();
                
                Item newItem = createItem(itemType, quantity, truck instanceof SpecialCoffeeTruck && binNumber >= 8);
                if (newItem != null) {
                    bin.replaceContents(newItem, quantity);
                    System.out.println("Bin " + (binNumber + 1) + " contents replaced.");
                }
                break;
            case 3:
                bin.empty();
                System.out.println("Bin " + (binNumber + 1) + " emptied.");
                break;
            default:
                System.out.println("Invalid action!");
        }
    }
    
    private void performMaintenance(CoffeeTruck truck) {
        System.out.println("\n=== MAINTENANCE ===");
        System.out.println("1. Change truck location");
        System.out.println("2. Update prices");
        System.out.print("Select maintenance option: ");
        
        int option = getIntInput();
        
        switch (option) {
            case 1:
                System.out.print("Enter new location: ");
                scanner.nextLine(); 
                String newLocation = scanner.nextLine();
                
                for (int i = 0; i < truckCount; i++) {
                    if (trucks[i] != truck && trucks[i].getLocation().equalsIgnoreCase(newLocation)) {
                        System.out.println("Location already occupied!");
                        return;
                    }
                }
                
                truck.setLocation(newLocation);
                System.out.println("Location updated to: " + newLocation);
                break;
            case 2:
                setPrices(truck);
                System.out.println("Prices updated successfully!");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void showDashboard() {
        System.out.println("\n=== DASHBOARD ===");
        
        int regularCount = 0;
        int specialCount = 0;
        
        for (int i = 0; i < truckCount; i++) {
            if (trucks[i] instanceof SpecialCoffeeTruck) {
                specialCount++;
            } else {
                regularCount++;
            }
        }
        
        System.out.println("Total Trucks Deployed: " + truckCount);
        System.out.println("  - Regular Trucks: " + regularCount);
        System.out.println("  - Special Trucks: " + specialCount);
        
        System.out.println("\n--- Aggregate Inventory ---");
        int[] cupCounts = new int[3]; // Small, Medium, Large
        double coffeeBeans = 0;
        double milk = 0;
        double water = 0;
        double syrups = 0;
        
        for (int i = 0; i < truckCount; i++) {
            StorageBin[] bins = trucks[i].getStorageBins();
            for (StorageBin bin : bins) {
                if (bin.getItem() != null) {
                    String itemName = bin.getItem().getName();
                    if (itemName.contains("Small Cup")) {
                        cupCounts[0] += bin.getQuantity();
                    } else if (itemName.contains("Medium Cup")) {
                        cupCounts[1] += bin.getQuantity();
                    } else if (itemName.contains("Large Cup")) {
                        cupCounts[2] += bin.getQuantity();
                    } else if (itemName.contains("Coffee Beans")) {
                        coffeeBeans += bin.getQuantity();
                    } else if (itemName.contains("Milk")) {
                        milk += bin.getQuantity();
                    } else if (itemName.contains("Water")) {
                        water += bin.getQuantity();
                    } else if (itemName.contains("Syrup")) {
                        syrups += bin.getQuantity();
                    }
                }
            }
        }
        
        System.out.println("Small Cups: " + cupCounts[0]);
        System.out.println("Medium Cups: " + cupCounts[1]);
        System.out.println("Large Cups: " + cupCounts[2]);
        System.out.println("Coffee Beans: " + coffeeBeans + " grams");
        System.out.println("Milk: " + milk + " fl oz");
        System.out.println("Water: " + water + " fl oz");
        System.out.println("Syrups: " + syrups + " fl oz");
        
        System.out.println("\n--- Combined Sales Summary ---");
        double totalSales = 0;
        int totalTransactions = 0;
        
        for (int i = 0; i < truckCount; i++) {
            totalSales += trucks[i].getSalesHistory().getTotalSales();
            totalTransactions += trucks[i].getSalesHistory().getTransactionCount();
        }
        
        System.out.println("Total Transactions: " + totalTransactions);
        System.out.println("Total Sales: $" + String.format("%.2f", totalSales));
    }
    
    private int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // clear buffer
            return -1;
        }
    }
    
    private double getDoubleInput() {
        try {
            return scanner.nextDouble();
        } catch (Exception e) {
            scanner.nextLine(); 
            return 0.0;
        }
    }
    
    public static void main(String[] args) {
        JavaJeepsSimulation simulation = new JavaJeepsSimulation();
        simulation.run();
    }
}

abstract class CoffeeTruck {
    protected String location;
    protected StorageBin[] storageBins;
    protected PriceList priceList;
    protected SalesHistory salesHistory;
    
    public CoffeeTruck(String location, int binCount) {
        this.location = location;
        this.storageBins = new StorageBin[binCount];
        for (int i = 0; i < binCount; i++) {
            storageBins[i] = new StorageBin();
        }
        this.priceList = new PriceList();
        this.salesHistory = new SalesHistory();
    }
    
    public abstract String getType();
    public abstract boolean processOrder(CoffeeOrder order);
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public StorageBin[] getStorageBins() { return storageBins; }
    public PriceList getPriceList() { return priceList; }
    public SalesHistory getSalesHistory() { return salesHistory; }
}

class RegularCoffeeTruck extends CoffeeTruck {
    public RegularCoffeeTruck(String location) {
        super(location, 8); // 8 storage bins for regular truck
    }
    
    @Override
    public String getType() {
        return "Regular Coffee Truck (JavaJeep)";
    }
    
    @Override
    public boolean processOrder(CoffeeOrder order) {
        System.out.println("\n>>> Preparing " + order.getSize() + " " + order.getDrinkType() + "...");
        
        // Calculate required ingredients
        double cupSize = order.getCupSize();
        double coffeeNeeded = 0;
        double waterNeeded = 0;
        double milkNeeded = 0;
        
        // Calculate espresso requirements (Standard brew ratio 1:18)
        if (order.getDrinkType().equals("Americano")) {
            // 1 part espresso, 2 parts water
            double espresso = cupSize / 3.0;
            waterNeeded = cupSize - espresso;
            coffeeNeeded = espresso / 18.0 * 28.34952; // Convert to grams
        } else if (order.getDrinkType().equals("Latte")) {
            // 1/5 espresso, 4/5 milk
            double espresso = cupSize / 5.0;
            milkNeeded = cupSize - espresso;
            coffeeNeeded = espresso / 18.0 * 28.34952;
        } else if (order.getDrinkType().equals("Cappuccino")) {
            // 1/3 espresso, 2/3 milk
            double espresso = cupSize / 3.0;
            milkNeeded = cupSize - espresso;
            coffeeNeeded = espresso / 18.0 * 28.34952;
        }
        
        if (!consumeIngredients(order.getSize(), coffeeNeeded, waterNeeded, milkNeeded)) {
            return false;
        }
        
        System.out.println(">>> Brewing Standard espresso - " + String.format("%.2f", coffeeNeeded) + " grams of coffee...");
        
        if (waterNeeded > 0) {
            System.out.println(">>> Adding Water...");
        }
        if (milkNeeded > 0) {
            System.out.println(">>> Adding Milk...");
        }
        
        System.out.println(">>> " + order.getDrinkType() + " Done!");
        
        double price = priceList.getPrice(order.getSize() + " " + order.getDrinkType());
        salesHistory.addTransaction(order.getSize() + " " + order.getDrinkType(), 
                                   coffeeNeeded, waterNeeded, milkNeeded, 0, 0, price);
        
        return true;
    }
    
    protected boolean consumeIngredients(String cupSize, double coffee, double water, double milk) {
        StorageBin cupBin = null;
        StorageBin coffeeBin = null;
        StorageBin waterBin = null;
        StorageBin milkBin = null;
        
        for (StorageBin bin : storageBins) {
            if (bin.getItem() != null) {
                String itemName = bin.getItem().getName();
                if (itemName.contains(cupSize + " Cup") && bin.getQuantity() > 0) {
                    cupBin = bin;
                } else if (itemName.equals("Coffee Beans") && bin.getQuantity() >= coffee) {
                    coffeeBin = bin;
                } else if (itemName.equals("Water") && bin.getQuantity() >= water) {
                    waterBin = bin;
                } else if (itemName.equals("Milk") && bin.getQuantity() >= milk) {
                    milkBin = bin;
                }
            }
        }
        
        if (cupBin == null || coffeeBin == null || 
            (water > 0 && waterBin == null) || (milk > 0 && milkBin == null)) {
            return false;
        }
        
        cupBin.consume(1);
        coffeeBin.consume(coffee);
        if (water > 0) waterBin.consume(water);
        if (milk > 0) milkBin.consume(milk);
        
        return true;
    }
}

class SpecialCoffeeTruck extends RegularCoffeeTruck {
    public SpecialCoffeeTruck(String location) {
        super(location);
        // Add 2 extra bins for syrups (total 10 bins)
        StorageBin[] newBins = new StorageBin[10];
        System.arraycopy(storageBins, 0, newBins, 0, 8);
        newBins[8] = new StorageBin();
        newBins[9] = new StorageBin();
        storageBins = newBins;
    }
    
    @Override
    public String getType() {
        return "Special Coffee Truck (JavaJeep+)";
    }
    
    @Override
    public boolean processOrder(CoffeeOrder order) {
        System.out.println("\n>>> Preparing " + order.getSize() + " " + order.getDrinkType() + "...");
        
        double cupSize = order.getCupSize();
        double coffeeNeeded = 0;
        double waterNeeded = 0;
        double milkNeeded = 0;
        double extraCoffee = 0;
        
        int brewRatio;
        String brewType;
        switch (order.getBrewStrength()) {
            case 1: // Light
                brewRatio = 20;
                brewType = "Light";
                break;
            case 3: // Strong
                brewRatio = 15;
                brewType = "Strong";
                break;
            default: // Standard
                brewRatio = 18;
                brewType = "Standard";
                break;
        }
        
        if (order.getDrinkType().equals("Americano")) {
            double espresso = cupSize / 3.0;
            waterNeeded = cupSize - espresso;
            coffeeNeeded = espresso / brewRatio * 28.34952;
        } else if (order.getDrinkType().equals("Latte")) {
            double espresso = cupSize / 5.0;
            milkNeeded = cupSize - espresso;
            coffeeNeeded = espresso / brewRatio * 28.34952;
        } else if (order.getDrinkType().equals("Cappuccino")) {
            double espresso = cupSize / 3.0;
            milkNeeded = cupSize - espresso;
            coffeeNeeded = espresso / brewRatio * 28.34952;
        }
        
        if (order.getExtraShots() > 0) {
            extraCoffee = order.getExtraShots() * (1.0 / brewRatio * 28.34952);
        }
        
        if (!consumeSpecialIngredients(order.getSize(), coffeeNeeded + extraCoffee, waterNeeded, milkNeeded, order.isHasSyrup())) {
            return false;
        }
        
        System.out.println(">>> Brewing " + brewType + " espresso - " + String.format("%.2f", coffeeNeeded) + " grams of coffee...");
        
        if (waterNeeded > 0) {
            System.out.println(">>> Adding Water...");
        }
        if (milkNeeded > 0) {
            System.out.println(">>> Adding Milk...");
        }
        if (order.isHasSyrup()) {
            System.out.println(">>> Adding Syrup...");
        }
        if (order.getExtraShots() > 0) {
            System.out.println(">>> Adding " + order.getExtraShots() + " extra shot(s) of " + brewType + " brew espresso - " + String.format("%.2f", extraCoffee) + " grams of coffee.");
        }
        
        System.out.println(">>> Custom " + order.getDrinkType() + " Done!");
        
        double price = priceList.getPrice(order.getSize() + " " + order.getDrinkType());
        if (order.getExtraShots() > 0) {
            price += order.getExtraShots() * priceList.getPrice("Extra Shot");
        }
        if (order.isHasSyrup()) {
            price += priceList.getPrice("Syrup Add-on");
        }
        
        salesHistory.addTransaction("Custom " + order.getSize() + " " + order.getDrinkType(), coffeeNeeded + extraCoffee, waterNeeded, milkNeeded, order.getExtraShots(), order.isHasSyrup() ? 1 : 0, price);
        
        return true;
    }
    
    private boolean consumeSpecialIngredients(String cupSize, double coffee, double water, double milk, boolean needsSyrup) {
        StorageBin cupBin = null;
        StorageBin coffeeBin = null;
        StorageBin waterBin = null;
        StorageBin milkBin = null;
        StorageBin syrupBin = null;
        
        for (StorageBin bin : storageBins) {
            if (bin.getItem() != null) {
                String itemName = bin.getItem().getName();
                if (itemName.contains(cupSize + " Cup") && bin.getQuantity() > 0) {
                    cupBin = bin;
                } else if (itemName.equals("Coffee Beans") && bin.getQuantity() >= coffee) {
                    coffeeBin = bin;
                } else if (itemName.equals("Water") && bin.getQuantity() >= water) {
                    waterBin = bin;
                } else if (itemName.equals("Milk") && bin.getQuantity() >= milk) {
                    milkBin = bin;
                } else if (needsSyrup && itemName.contains("Syrup") && bin.getQuantity() > 0) {
                    syrupBin = bin;
                }
            }
        }
        
        if (cupBin == null || coffeeBin == null || 
            (water > 0 && waterBin == null) || (milk > 0 && milkBin == null) || (needsSyrup && syrupBin == null)) {
            return false;
        }
        
        cupBin.consume(1);
        coffeeBin.consume(coffee);
        if (water > 0) waterBin.consume(water);
        if (milk > 0) milkBin.consume(milk);
        if (needsSyrup) syrupBin.consume(1.0); // 1 fl oz of syrup
        
        return true;
    }
}

class CoffeeOrder {
    private String drinkType;
    private String size;
    private double cupSize;
    private int brewStrength; // 1=Light 2=Standard 3=Strong
    private int extraShots;
    private boolean hasSyrup;
    
    public CoffeeOrder(String drinkType, String size, double cupSize) {
        this.drinkType = drinkType;
        this.size = size;
        this.cupSize = cupSize;
        this.brewStrength = 2; 
        this.extraShots = 0;
        this.hasSyrup = false;
    }
    
    public String getDrinkType() { return drinkType; }
    public String getSize() { return size; }
    public double getCupSize() { return cupSize; }
    public int getBrewStrength() { return brewStrength; }
    public void setBrewStrength(int brewStrength) { this.brewStrength = brewStrength; }
    public int getExtraShots() { return extraShots; }
    public void setExtraShots(int extraShots) { this.extraShots = extraShots; }
    public boolean isHasSyrup() { return hasSyrup; }
    public void setHasSyrup(boolean hasSyrup) { this.hasSyrup = hasSyrup; }
}

// Storage Bin class
class StorageBin {
    private Item item;
    private double quantity;
    private double capacity;
    
    public StorageBin() {
        this.item = null;
        this.quantity = 0;
        this.capacity = 0;
    }
    
    public boolean addItem(Item item, double quantity) {
        if (this.item == null || this.item.getName().equals(item.getName())) {
            this.item = item;
            setCapacityBasedOnItem();
            if (this.quantity + quantity <= capacity) {
                this.quantity += quantity;
                return true;
            }
        }
        return false;
    }
    
    public void replaceContents(Item newItem, double quantity) {
        this.item = newItem;
        setCapacityBasedOnItem();
        this.quantity = Math.min(quantity, capacity);
    }
    
    public void addQuantity(double amount) {
        if (item != null) {
            quantity = Math.min(quantity + amount, capacity);
        }
    }
    
    public boolean consume(double amount) {
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;
    }
    
    public void empty() {
        this.item = null;
        this.quantity = 0;
        this.capacity = 0;
    }
    
    private void setCapacityBasedOnItem() {
        if (item != null) {
            String itemName = item.getName();
            if (itemName.contains("Small Cup")) {
                capacity = 80;
            } else if (itemName.contains("Medium Cup")) {
                capacity = 64;
            } else if (itemName.contains("Large Cup")) {
                capacity = 40;
            } else if (itemName.equals("Coffee Beans")) {
                capacity = 1008;
            } else if (itemName.equals("Milk") || itemName.equals("Water") || itemName.contains("Syrup")) {
                capacity = 640;
            }
        }
    }
    
    public String getContents() {
        if (item == null) {
            return "Empty";
        }
        return item.getName() + " - " + quantity + "/" + capacity + " " + item.getUnit();
    }
    
    public Item getItem() { return item; }
    public double getQuantity() { return quantity; }
    public double getCapacity() { return capacity; }
}

abstract class Item {
    protected String name;
    protected String unit;
    
    public Item(String name, String unit) { //coffee been, grams
        this.name = name; // (name,unit)
        this.unit = unit;
    }
    
    public String getName() { return name; }
    public String getUnit() { return unit; }
}

class Cup extends Item {
    private double capacity;
    
    public Cup(String size, double capacity) {
        super(size + " Cup", "pcs");
        this.capacity = capacity;
    }
    
    public double getCapacity() { return capacity; }
}

class Ingredient extends Item {
    public Ingredient(String name, String unit) {
        super(name, unit);
    }
}

class PriceList {
    private static final int MAX_ITEMS = 20;
    private String[] itemNames;
    private double[] prices;
    private int itemCount;
    
    public PriceList() {
        itemNames = new String[MAX_ITEMS];
        prices = new double[MAX_ITEMS];
        itemCount = 0;
    }
    
    public void setPrice(String itemName, double price) {
        for (int i = 0; i < itemCount; i++) {
            if (itemNames[i].equals(itemName)) {
                prices[i] = price;
                return;
            }
        }
        
        if (itemCount < MAX_ITEMS) {
            itemNames[itemCount] = itemName;
            prices[itemCount] = price;
            itemCount++;
        }
    }
    
    public double getPrice(String itemName) {
        for (int i = 0; i < itemCount; i++) {
            if (itemNames[i].equals(itemName)) {
                return prices[i];
            }
        }
        return 0.0;
    }
    
    public void displayMenu() {
        System.out.println("Menu Items:");
        for (int i = 0; i < itemCount; i++) {
            System.out.println(itemNames[i] + ": $" + String.format("%.2f", prices[i]));
        }
    }
}

class SalesHistory {
    private static final int MAX_TRANSACTIONS = 100;
    private Transaction[] transactions;
    private int transactionCount;
    private double totalSales;
    
    public SalesHistory() {
        transactions = new Transaction[MAX_TRANSACTIONS];
        transactionCount = 0;
        totalSales = 0.0;
    }
    
    public void addTransaction(String drinkName, double coffee, double water, double milk, 
                              int extraShots, int syrups, double price) {
        if (transactionCount < MAX_TRANSACTIONS) {
            transactions[transactionCount] = new Transaction(drinkName, coffee, water, milk, 
                                                           extraShots, syrups, price);
            transactionCount++;
            totalSales += price;
        }
    }
    
    public void displayTransactions() {
        if (transactionCount == 0) {
            System.out.println("No transactions recorded.");
            return;
        }
        
        System.out.println("Recent Transactions:");
        for (int i = 0; i < transactionCount; i++) {
            Transaction t = transactions[i];
            System.out.println((i + 1) + ". " + t.getDrinkName() + 
                              " - Coffee: " + String.format("%.2f", t.getCoffeeUsed()) + "g" +
                              ", Water: " + String.format("%.2f", t.getWaterUsed()) + " fl oz" +
                              ", Milk: " + String.format("%.2f", t.getMilkUsed()) + " fl oz" +
                              (t.getExtraShots() > 0 ? ", Extra Shots: " + t.getExtraShots() : "") +
                              (t.getSyrups() > 0 ? ", Syrups: " + t.getSyrups() : "") +
                              " - $" + String.format("%.2f", t.getPrice()));
        }
        System.out.println("Total Sales: $" + String.format("%.2f", totalSales));
    }
    
    public double getTotalSales() { return totalSales; }
    public int getTransactionCount() { return transactionCount; }
}

class Transaction {
    private String drinkName;
    private double coffeeUsed;
    private double waterUsed;
    private double milkUsed;
    private int extraShots;
    private int syrups;
    private double price;
    
    public Transaction(String drinkName, double coffeeUsed, double waterUsed, double milkUsed,
                      int extraShots, int syrups, double price) {
        this.drinkName = drinkName;
        this.coffeeUsed = coffeeUsed;
        this.waterUsed = waterUsed;
        this.milkUsed = milkUsed;
        this.extraShots = extraShots;
        this.syrups = syrups;
        this.price = price;
    }
    
    
    public String getDrinkName() { return drinkName; }
    public double getCoffeeUsed() { return coffeeUsed; }
    public double getWaterUsed() { return waterUsed; }
    public double getMilkUsed() { return milkUsed; }
    public int getExtraShots() { return extraShots; }
    public int getSyrups() { return syrups; }
    public double getPrice() { return price; }
}
