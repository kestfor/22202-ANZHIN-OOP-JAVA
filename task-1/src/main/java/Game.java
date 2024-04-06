import java.util.InputMismatchException;

public class Game {
    private static final int upperLimit = 9999;
    private static final int lowerLimit = 1000;
    private static final int winningAmountOfMatches = 4;
    private final Riddler riddler;
    private final Player player;


    Game() {
        player = new Player();
        int value = 0;
        while (Validator.validate(value, lowerLimit, upperLimit) != Validator.responseCode.ok) {
            value = (int) Math.floor(Math.random() * (upperLimit - lowerLimit + 1) + lowerLimit);
        }
        riddler = new Riddler(value);
    }

    public void run() {
        System.out.print("Тайное число загадано, сделайте предположение: ");
        while (true) {
            if (isOver()) {
                break;
            }
        }
        System.out.format("Вы отгадали загаданное число - %d", player.getGuess());
    }

    private boolean isOver() {
        try {
            player.makeGuess();
        } catch (InputMismatchException e) {
            System.out.print("Необходимо ввести число: ");
            return false;
        }
        switch (Validator.validate(player.getGuess(), lowerLimit, upperLimit)) {
            case ok:
                break;
            case repeatedDigits:
                System.out.print("Цифры в числе не должны повторяться: ");
                return false;
            case outOfRange:
                System.out.format("Число находится в диапазоне от %d до %d: ", lowerLimit, upperLimit);
                return false;
        }
        int amountOfBulls = riddler.getAmountOufBulls(player.getGuess());
        if (amountOfBulls == winningAmountOfMatches) {
            return true;
        }
        int amountOfCows = riddler.getAmountOfCows(player.getGuess());
        System.out.format("количество коров: %d\nколичество быков: %d\nследующее предположение: ", amountOfCows, amountOfBulls);
        return false;
    }
}
