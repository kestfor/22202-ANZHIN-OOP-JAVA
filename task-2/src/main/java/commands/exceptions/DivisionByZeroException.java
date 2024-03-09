package commands.exceptions;

public class DivisionByZeroException extends CommandsException {

    public DivisionByZeroException(String message) {
        super(message);
    }

    public DivisionByZeroException() {
        this("Division by zero");
    }
}
