package commands.exceptions;

abstract public class CommandsException extends Exception {
    public CommandsException(String message) {
        super(message);
    }
}
