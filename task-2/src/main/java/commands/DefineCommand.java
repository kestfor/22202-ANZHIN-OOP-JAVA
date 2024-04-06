package commands;

import java.util.List;
import commands.exceptions.CommandsException;
import commands.exceptions.IllegalAccessException;
import commands.exceptions.WrongFormatException;
import executioncontext.ExecutionContext;

public class DefineCommand extends Command {
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws CommandsException {
        if (positionalArgs.size() < 2) {
            throw new WrongFormatException("define command requires 2 positional arguments");
        }
        try {
            context.getNamedArgs().put(positionalArgs.get(0), Double.parseDouble(positionalArgs.get(1)));
        } catch (NumberFormatException e) {
            throw new IllegalAccessException("can't parse given value of parameter");
        }
    }
}
