package Metthy;

import java.util.ArrayList;

public class CoffeeTruck {

    private String name;
    private String location;
    private ArrayList<StorageBin> bins;

    public CoffeeTruck(String name, String location){

        int i;
        this.name = name;
        this.location = location;
        this.bins = new ArrayList<>(8);

        for(i = 0; i < 8; i++){
            bins.add(new StorageBin(i));
        }
    }

    public void displayBins(){

        int i, capacity, binNumber;
        double quantity;
        String item;

        System.out.println("--- Storage Bins ---");

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            item = bin.getItemType();
            quantity = bin.getItemQuantity();
            capacity = bin.getCapacity();
            binNumber = i + 1;

            if(item == null)
                System.out.println("Bin #" + binNumber + ": [Empty]");

            else
                System.out.println("Bin #" + binNumber + ": " + item + " - " + quantity + "/" + capacity);
        }
    }

    public void displayInfo(){

        System.out.println("Name - " + this.name + " | " + "Location: " + this.location);
        displayBins();
    }

    public void assignItemToBin(int binNumber, String itemType, int itemQuantity){

        bins.get(binNumber).assignItem(itemType, itemQuantity);
    }

    public void emptyBin(int binNumber){

        if (binNumber >= 1 && binNumber <= bins.size()){

            bins.get(binNumber - 1).empty();
            System.out.println("Bin #" + binNumber + " emptied.");
        }

        else
            System.out.println("Invalid bin number.");
    }



    //needs simplifying
    public boolean getFromBin(String itemName, double amount){

        for(StorageBin bin : bins){
            if (bin != null && bin.getItemType().equalsIgnoreCase(itemName)) {
                return bin.useQuantity(amount);
            }
        }
        return false;
    }

    public String getName(){

        return name;
    }

    public String getLocation(){

        return location;
    }

    public void setName(String name){

        this.name = name;
    }

    public void setLocation(String location){

        this.location = location;
    }

    //Return arraylist containing storage bins
    public ArrayList<StorageBin> getBins(){

        return bins;
    }

    public StorageBin findBin(String itemName){

        int i;
        StorageBin result = null;

        bins = getBins();
        String item;

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            item = bin.getItemType();

            if(item != null && item.equalsIgnoreCase(itemName))
                result = bin;
        }

        return result;
    }

}