package commands.arithmetic;

import executioncontext.ExecutionContext;

import java.util.List;

public class AddCommand extends ArithmeticCommand {
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalAccessException {
        super.execute(context, positionalArgs);
        context.getStack().push(context.getStack().pop() + context.getStack().pop());
    }
}
