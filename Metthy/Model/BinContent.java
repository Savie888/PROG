package Metthy.Model;

/** An interface for all items that can be stored in a coffee truck's storage bin. */
public interface BinContent {

    /**
     * Returns the name of the ingredient or item.
     *
     * @return the name of the content (e.g., "Coffee Bean", "Milk")
     */
    String getName();

    /**
     * Sets the current quantity of this bin content.
     *
     * @param quantity the new quantity to set
     */
    void setQuantity(double quantity);

    /**
     * Gets the current quantity of the item in the bin.
     *
     * @return the quantity
     */
    double getQuantity();

    /**
     * Adds a specified amount to the current quantity.
     *
     * @param amount the amount to add
     */
    void addQuantity(double amount);

    /**
     * Uses or deducts a specified amount from the current quantity.
     *
     * @param amount the amount to deduct
     */
    void useQuantity(double amount);

    /**
     * Returns the maximum capacity for this bin content.
     *
     * @return the capacity as an integer
     */
    int getCapacity();

    /**
     * Creates and returns a duplicate of this bin content.
     *
     * @return a new BinContent instance with the same values
     */
    BinContent clone();

    /**
     * Returns a string representation of the bin content, including name and quantity.
     *
     * @return a string.
     */
    String toString();
}
