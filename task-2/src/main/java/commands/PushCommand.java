package commands;
import java.util.List;
import commands.exceptions.IllegalAccessException;
import executioncontext.ExecutionContext;
import commands.exceptions.*;

public class PushCommand extends Command{

    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws CommandsException {
        if (positionalArgs.isEmpty()) {
            throw new WrongFormatException("missed push argument");
        }
        String stringValue = positionalArgs.get(0);
        try {
            double value = Double.parseDouble(stringValue);
            context.getStack().push(value);
        } catch (NumberFormatException e) {
            if (!context.getNamedArgs().containsKey(stringValue)) {
                throw new IllegalAccessException("there is no defined value with name '" + stringValue + "'");
            } else {
                context.getStack().push(context.getNamedArgs().get(stringValue));
            }
        }
    }
}
