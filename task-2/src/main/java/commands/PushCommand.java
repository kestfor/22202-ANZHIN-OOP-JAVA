package commands;

import java.util.List;
import java.util.MissingFormatArgumentException;
import executioncontext.ExecutionContext;

public class PushCommand extends Command{

    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalArgumentException {
        if (positionalArgs.isEmpty()) {
            throw new MissingFormatArgumentException("missed push argument");
        }
        String stringValue = positionalArgs.get(0);
        try {
            double value = Double.parseDouble(stringValue);
            context.getStack().push(value);
        } catch (NumberFormatException e) {
            if (!context.getNamedArgs().containsKey(stringValue)) {
                throw new IllegalArgumentException("there is no defined value with name '" + stringValue + "'");
            } else {
                context.getStack().push(context.getNamedArgs().get(stringValue));
            }
        }
    }
}
