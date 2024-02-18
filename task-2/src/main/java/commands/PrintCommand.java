package commands;

import executioncontext.ExecutionContext;

import java.util.List;

public class PrintCommand extends Command{
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalAccessException {
        if (context.getStack().isEmpty()) {
            throw new IllegalAccessException("attempt to print element of empty stack");
        } else {
            System.out.println(context.getStack().peek());
        }
    }
}
