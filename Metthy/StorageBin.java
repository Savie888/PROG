package Metthy;

/**
 * This class represents a storage bin in a coffee truck .
 * Each bin tracks the type of item, its capacity, and the current quantity of the item.
 */
public class StorageBin {

    /**
     * The type of item stored in this bin.
     */
    private String itemType;
    /**
     * The bin's identification number.
     */
    private final int binNumber;
    /**
     * The maximum capacity of this bin, based on the item type.
     */
    private int itemCapacity;
    /**
     * The current quantity of the item stored in this bin.
     */
    private double itemQuantity;

    /**
     * Creates a new StorageBin with the given bin number.
     *
     * @param binNumber the unique identifier for the bin
     */
    public StorageBin(int binNumber){

        this.binNumber = binNumber;
        this.itemType = null;
        this.itemCapacity = 0; //Initialize to 0
        this.itemQuantity = 0; //Initialize to 0
    }

    /**
     * Assigns an item type and quantity to the bin.
     * Also sets the capacity based on the item type.
     *
     * @param itemType the name of the item to store
     * @param itemQuantity the starting quantity of the item
     */
    public void assignItem(String itemType, double itemQuantity){

        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemCapacity = getCapacityForItem(itemType);
    }

    /**
     * Returns the bin's number.
     *
     * @return the bin number
     */
    public int getBinNumber(){

        return binNumber;
    }

    /**
     * Checks if the bin is empty
     *
     * @return true if the bin is empty, false otherwise
     */
    public boolean isEmpty(){

        boolean flag = false;

        //Bin is considered empty if there is no item assigned or item quantity is 0
        if(itemQuantity == 0)
            flag = true;

        return flag;
    }

    /**
     * Checks if the bin is full.
     *
     * @return true if the bin is full, false otherwise
     */
    public boolean isFull(){

        boolean flag = false;

        //Bin is considered full if item quantity is equal to capacity and an item is assigned
        if(itemQuantity == itemCapacity)
            flag = true;

        return flag;
    }

    /**
     * Fills the bin to its maximum capacity.
     */
    public void fill(){

        //Check first if there is an item assigned and if it's already full
        if(itemType != null && !isFull())
            itemQuantity = 1.0 * itemCapacity;
    }

    /**
     * Empties the bin by removing the item type and setting the quantity to zero.
     */
    public void empty(){

        itemType = null;
        itemQuantity = 0;

    }

    /**
     * Deducts a specified amount from the current quantity.
     * Assumes the amount to be deducted does not exceed actual quantity in the bin
     *
     * @param amount the amount of item to use
     */
    public void useQuantity(double amount){

        //Only deduct if there is enough in storage
        if(itemQuantity >= amount)
            itemQuantity -= amount;
    }

    /**
     * Adds a specified amount to the bin's current quantity.
     * Quantity must not exceed item capacity
     * @param amount the amount of item to add
     */
    public void addQuantity(double amount){

        //Make sure specified amount does not exceed item capacity
        if(itemQuantity + amount <= itemCapacity){

            itemQuantity += amount;
            System.out.println("Bin #" + binNumber + " restocked.");
        }

        else
            System.out.println("Added quantity would go over capacity. Restock failed");
    }

    /**
     * Returns the type of item stored in this bin.
     *
     * @return the item type, or null if unassigned
     */
    public String getItemType(){

        return itemType;
    }

    /**
     * Sets the item type for this bin.
     *
     * @param itemType the type of item to assign
     */
    public void setItemType(String itemType){

        this.itemType = itemType;
    }

    /**
     * Returns the current quantity of the item in the bin.
     *
     * @return the quantity of the item in the bin
     */
    public double getItemQuantity(){

        return itemQuantity;
    }

    /**
     * Returns the maximum capacity of the bin.
     *
     * @return the item capacity of the bin
     */
    public int getCapacity(){

        return itemCapacity;
    }

    /**
     * Returns the maximum capacity for a specific item type.
     *
     * @param itemType the type of item to look up capacity for
     * @return the maximum capacity for the given item type,
     *         or 0 if the item type is unrecognized
     */
    public int getCapacityForItem(String itemType){

        int capacity;

        switch(itemType){

            case "Small Cup":
                capacity = 80;
                break;
            case "Medium Cup":
                capacity = 64;
                break;
            case "Large Cup":
                capacity = 40;
                break;
            case "Coffee Beans":
                capacity = 1008;
                break;
            case "Milk":
                capacity = 640;
                break;
            case "Water":
                capacity = 640;
                break;
            default:
                capacity = 0;
                break;
        }

        return capacity;
    }
}
