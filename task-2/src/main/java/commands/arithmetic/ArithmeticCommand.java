package commands.arithmetic;

import java.util.List;

import commands.Command;
import executioncontext.ExecutionContext;

public class ArithmeticCommand extends Command {

    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalAccessException {
        if (context.getStack().size() < 2) {
            throw new IllegalAccessException("not sufficient amount of values in stack");
        }
    }
}
