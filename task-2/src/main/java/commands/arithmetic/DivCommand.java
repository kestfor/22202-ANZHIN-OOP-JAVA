package commands.arithmetic;

import commands.exceptions.CommandsException;
import commands.exceptions.DivisionByZeroException;
import executioncontext.ExecutionContext;

import java.util.List;

public class DivCommand extends ArithmeticCommand {
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws CommandsException {
        super.execute(context, positionalArgs);
        double first = context.getStack().pop();
        double second = context.getStack().pop();
        if (second == 0) {
            context.getStack().push(second);
            context.getStack().push(first);
            throw new DivisionByZeroException();
        }
        context.getStack().push(first / second);
    }
}
