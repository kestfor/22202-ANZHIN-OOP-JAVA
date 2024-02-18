package commands;

import java.util.List;
import java.util.MissingFormatArgumentException;
import executioncontext.ExecutionContext;

public class DefineCommand extends Command{
    @Override
    public void execute(ExecutionContext context, List<String> positionalArgs) throws MissingFormatArgumentException {
        if (positionalArgs.size() < 2) {
            throw new MissingFormatArgumentException("define command requires 2 positional arguments");
        }
        try {
            context.getNamedArgs().put(positionalArgs.get(0), Double.parseDouble(positionalArgs.get(1)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("can't parse given value of parameter");
        }
    }
}
