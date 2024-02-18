package commands.arithmetic;

import commands.arithmetic.ArithmeticCommand;
import executioncontext.ExecutionContext;

import java.util.List;

public class MulCommand extends ArithmeticCommand {
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalAccessException {
        super.execute(context, positionalArgs);
        context.getStack().push(context.getStack().pop() * context.getStack().pop());
    }
}
