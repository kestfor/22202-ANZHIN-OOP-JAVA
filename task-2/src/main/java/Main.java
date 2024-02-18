import commandsfactory.CommandsFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;
        if (args.length == 0) {
            scanner = new Scanner(System.in);
        } else {
            try {
                scanner = new Scanner(new FileInputStream(args[0]));
            } catch (FileNotFoundException e) {
                System.err.println(e.getLocalizedMessage());
                return;
            }
        }
        CommandsFactory factory = new CommandsFactory();
        StackCalculator calculator = new StackCalculator(factory);
        while (scanner.hasNextLine()) {
            try {
                calculator.execute(scanner.nextLine());
            } catch (IllegalAccessException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }
}
