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

    public void displayInfo(){

        System.out.println("Name: "+ this.name);
        System.out.println("Location: "+ this.location);
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

    public void displayBins(){

        int i, quantity, capacity, binNumber;
        String item;

        System.out.println("--- Storage Bins ---");

        for(i = 0; i < bins.size(); i++){

            StorageBin bin = bins.get(i);
            item = bin.getItemType();
            quantity = bin.getQuantity();
            capacity = bin.getCapacity();
            binNumber = i + 1;

            if(item == null)
                System.out.println("Bin #" + binNumber + ": [Empty]");

            else
                System.out.println("Bin #" + binNumber + ": " + item + " - " + quantity + "/" + capacity);
        }
    }

    public String getName(){

        return name;
    }

    //Return arraylist containing storage bins
    public ArrayList<StorageBin> getBins(){

        return bins;
    }

    //Return bin based on index
    public StorageBin getBin(int index){

        return bins.get(index);
    }

    public String getLocation(){

        return location;
    }
}