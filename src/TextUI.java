import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextUI {

    Scanner scan = new Scanner(System.in);

    public String promptText(String msg) {
        System.out.println(msg);
        String input = scan.nextLine();
        return input;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public boolean promptBinary(String msg) {
        String input = promptText(msg).toLowerCase();
        if (input.equals("y")) {
            return true;
        }

        if (input.equals("n")) {
            return false;
        }
        return promptBinary(msg);
    }

    public int promptNumeric(String msg) {
        System.out.println(msg);
        int input = 0;
        try {
            input = scan.nextInt();
        } catch (InputMismatchException e) {
            scan.nextLine();
            System.out.println("Skriv et tal");
            input = promptNumeric(msg);
        }

        scan.nextLine(); //scannerbug flush

        return input;
    }

    public void displayList(ArrayList<String> list, String msg, boolean numbered) {//parameter der forstæller OM listen er nummereret eller ej.
        System.out.println(msg);

        for (int i = 0; i < list.size(); i++) {

            // String s = numbered?i+1+". "+ list.get(i):list.get(i);
            String s;
            if (numbered) {
                s = i + 1 + ". " + list.get(i);

            } else {

                s = list.get(i);
            }
            System.out.println(s);
        }

       /*
       int i = 0;
       for(String s : list){
            // s = list.get(i);
            System.out.println(s);
            i++;

        }

        */


    }

    public ArrayList<String> promptChoice(ArrayList<String> options, int limit, String msg) {
        ArrayList<String> choices = new ArrayList<>();


        //kør et while loop, så længe limit ikke er nået
        while (choices.size() < limit) {
            int input = this.promptNumeric(msg);
            if (input > 0 && input <= options.size()) {
                String choice = options.get(input - 1);
                choices.add(choice);
            } else {
                System.out.println("ugyldigt input");
            }

        }


        return choices;
    }

    public int promptMenuChoice(ArrayList<String> menu, String msg) {
        displayList(menu, msg, true);
        while(true) {
            int input = this.promptNumeric("tast et tal");

            if(input <= menu.size() && input >= 1) {
                return input;
            }
            else {
                System.out.println("Not a valid choice");
            }
        }
    }


    public void displayMessage(ArrayList<String> texts, String s) {
        displayList(texts, s, false);
    }
}