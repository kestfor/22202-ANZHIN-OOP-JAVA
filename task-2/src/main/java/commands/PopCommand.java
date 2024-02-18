package commands;

import java.util.List;
import executioncontext.ExecutionContext;

public class PopCommand extends Command {

    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalAccessException {
        if (context.getStack().isEmpty()) {
            throw new IllegalAccessException("pop command was used on empty stack");
        }
        context.getStack().pop();
    }

}
