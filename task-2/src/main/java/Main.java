import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import commandsfactory.CommandsFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Scanner scanner;
        if (args.length == 0) {
            scanner = new Scanner(System.in);
        } else {
            try {
                scanner = new Scanner(new FileInputStream(args[0]));
            } catch (FileNotFoundException e) {
                logger.error(e.getLocalizedMessage());
                return;
            }
        }

        CommandsFactory factory = new CommandsFactory();
        StackCalculator calculator = new StackCalculator(factory);
        while (scanner.hasNextLine()) {
            calculator.execute(scanner.nextLine());
        }
        scanner.close();
    }
}
