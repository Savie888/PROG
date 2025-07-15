package Metthy.View;

import Metthy.Model.RegularCoffeeTruck;

import java.util.ArrayList;
import java.util.Scanner;

public class View {

    protected final Scanner scanner;

    public View(){

        scanner = new Scanner(System.in);
    }

    public void printHeader(String header){

        System.out.println("\n==== " + header + " ====");
    }

    public void printLine(String msg) {
        System.out.println(msg);
    }

    public void printError(String error) {
        System.out.println("[Error] " + error);
    }

    public void printDivider() {
        System.out.println("-------------------------");
    }

    public void printExitMessage() {
        System.out.println("Exiting Menu...");
    }

    public void printInvalidOption() {
        System.out.println("Invalid option. Please try again");
    }

    public String yesOrNo(){

        String choice;

        choice = scanner.nextLine();

        return choice;
    }

    public void showQuantityPrompt(){

        System.out.print("Enter quantity: ");
    }
}

