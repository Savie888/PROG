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

    public void assignItem(String itemType, int itemQuantity){

        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemCapacity = getCapacityForItem(itemType);
    }

    public int getBinNumber(){

        return binNumber;
    }

    public void empty(){

        this.itemType = null;
        this.itemQuantity = 0;
    }

    public boolean addQuantity(double amount){

        boolean flag = false;

        if(itemQuantity + amount <= itemCapacity){
            itemQuantity += amount;
            flag = true;
        }

        return flag;
    }

    public boolean useQuantity(double amount){

        boolean flag = false;
        if(itemQuantity >= amount){
            itemQuantity -= amount;
            flag = true;
        }

        return flag;
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

    public void setItemQuantity(double itemQuantity){

        this.itemQuantity = itemQuantity;
    }

    public int getCapacity(){

        return itemCapacity;
    }

    public void setItemCapacity(int capacity){

        this.itemCapacity = capacity;
    }
    public boolean isEmpty(){

        return itemQuantity == 0;
    }

    public boolean isFull(){

        return itemQuantity == itemCapacity;
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
