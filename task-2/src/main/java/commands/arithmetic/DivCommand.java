package commands.arithmetic;

import commands.exceptions.CommandsException;
import executioncontext.ExecutionContext;

import java.util.List;

public class DivCommand extends ArithmeticCommand {
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws CommandsException {
        super.execute(context, positionalArgs);
        context.getStack().push(context.getStack().pop() / context.getStack().pop());
    }
}
