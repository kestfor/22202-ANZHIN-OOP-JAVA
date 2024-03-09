import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private int guess;
    Scanner scanner;

    Player() {
        scanner = new Scanner(System.in);
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public int getGuess() {
        return guess;
    }

    public void makeGuess() throws InputMismatchException {
        String guess = scanner.next();
        int intGuess;
        try {
            intGuess = Integer.parseInt(guess);
        } catch (NumberFormatException e) {
            throw new InputMismatchException();
        }
        setGuess(intGuess);
    }
}
