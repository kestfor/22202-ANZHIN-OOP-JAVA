package commands.arithmetic;

import commands.Command;
import executioncontext.ExecutionContext;

import java.util.List;

public class SqrtCommand extends Command {
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalAccessException {
        if (context.getStack().isEmpty()) {
            throw new IllegalAccessException("attempt to access item from empty stack");
        }
        context.getStack().push(Math.sqrt(context.getStack().pop()));
    }
}
