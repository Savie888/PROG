package Metthy.Model;

public interface BinContent {

    boolean store();

    boolean retrieve();

    String getName();

    void setQuantity(double quantity);

    double getQuantity();

    int getCapacity();
}
