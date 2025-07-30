package Metthy.Model;

public interface BinContent {

    String getName();

    void setQuantity(double quantity);

    double getQuantity();

    void addQuantity(double amount);

    void useQuantity(double amount);

    int getCapacity();

    BinContent clone();

    String toString();
}
