package Metthy.Model;

/**
 * This class represents a storage bin in a coffee truck .
 * Each bin tracks the type of item, its capacity, and the current quantity of the item.
 */
public class StorageBin {


    /**
     * The bin's identification number.
     */
    private final int binNumber;

    /**
     * The type of item stored in this bin.
     */
    private BinContent content;

    /**
     * Creates a new StorageBin with the given bin number.
     *
     * @param binNumber the unique identifier for the bin
     */
    public StorageBin(int binNumber){

        this.binNumber = binNumber;
    }

    /**
     * Assigns an item type and quantity to the bin.
     * Also sets the capacity based on the item type.
     *
     * @param content the name of the item to store
     * @param quantity the starting quantity of the item
     */
    public void setContent(BinContent content, double quantity){

        this.content = content;
        content.setQuantity(quantity);
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
        if(content.getQuantity() == 0)
            flag = true;

        return flag;
    }

    /**
     * Fills the bin to its maximum capacity.
     */
    public void fill(){

        content.setQuantity(content.getCapacity());
    }

    /**
     * Empties the bin by removing the item type and setting the quantity to zero.
     */
    public void empty(){

        content = null;
    }

    /**
     * Deducts a specified amount from the current quantity.
     * Assumes the amount to be deducted does not exceed actual quantity in the bin
     *
     * @param amount the amount of item to use
     */
    public void useQuantity(double amount){

        //Only deduct if there is enough in storage
        if(content.getQuantity() >= amount)
            content.setQuantity(content.getQuantity() - amount);
    }

    /**
     * Adds a specified amount to the bin's current quantity.
     * Quantity must not exceed item capacity
     *
     * @param amount the amount of item to add
     */
    public void addQuantity(double amount){

        content.addQuantity(amount);
    }

    public BinContent getContent() {

        return content;
    }

    public double getItemQuantity(){

        return content.getQuantity();
    }
}
