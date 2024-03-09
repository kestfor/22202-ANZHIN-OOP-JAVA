package commands;

import commands.exceptions.CommandsException;
import commands.exceptions.IllegalAccessException;
import executioncontext.ExecutionContext;

import java.util.List;

public class PrintCommand extends Command{
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws CommandsException {
        if (context.getStack().isEmpty()) {
            throw new IllegalAccessException("attempt to print element of empty stack");
        } else {
            System.out.println(context.getStack().peek());
        }
    }
}
