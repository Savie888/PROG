package Metthy;

public class StorageBin {

    private String itemType;
    private int binNumber;
    private int itemCapacity;
    private double itemQuantity;

    public StorageBin(int binNumber){

        this.binNumber = binNumber;
        this.itemType = null;
        this.itemCapacity = 0; //Initialize to 0
        this.itemQuantity = 0; //Initialize to 0
    }

    public void assignItem(String itemType, double itemQuantity){

        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemCapacity = getCapacityForItem(itemType);
    }

    public int getBinNumber(){

        return binNumber;
    }

    public boolean isEmpty(){

        return itemQuantity == 0 && itemType == null;
    }

    public boolean isFull(){

        return itemQuantity == itemCapacity && itemType != null;
    }

    public void fill(){

        if(itemType != null && !isFull())
            this.itemQuantity = 1.0 * itemCapacity;
    }

    public void empty(){

        if(!isEmpty()){

            this.itemType = null;
            this.itemQuantity = 0;
        }
    }

    public void useQuantity(double amount){

        itemQuantity -= amount;
    }

    public void addQuantity(double amount){

        if(itemQuantity + amount <= itemCapacity){

            itemQuantity += amount;
            System.out.println("Bin #" + binNumber + " restocked.");
        }

        else
            System.out.println("Added quantity would go over capacity. Restock failed");
    }

    public String getItemType(){

        return itemType;
    }

    public void setItemType(String itemType){

        this.itemType = itemType;
    }

    public double getItemQuantity(){

        return itemQuantity;
    }

    public int getCapacity(){

        return itemCapacity;
    }

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
