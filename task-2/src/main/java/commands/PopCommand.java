package commands;

import java.util.List;

import commands.exceptions.CommandsException;
import commands.exceptions.IllegalAccessException;
import executioncontext.ExecutionContext;

public class PopCommand extends Command {

    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws CommandsException {
        if (context.getStack().isEmpty()) {
            throw new IllegalAccessException("pop command was used on empty stack");
        }
        context.getStack().pop();
    }

}
