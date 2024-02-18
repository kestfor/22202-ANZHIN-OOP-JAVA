import commands.Command;
import executioncontext.ExecutionContext;

import java.util.Arrays;
import java.util.List;

import commandsfactory.CommandsFactory;

public class StackCalculator {
    private final ExecutionContext context;

    private final CommandsFactory factory;

    StackCalculator(CommandsFactory factory) {
        this.context = new ExecutionContext();
        this.factory = factory;
    }

    public void execute(String rawString) throws IllegalAccessException {
        String[] parsed = rawString.split(" ");

        List<String> positionalArgs = Arrays.asList(parsed);
        positionalArgs = positionalArgs.subList(1, positionalArgs.size());

        Command command = null;
        command = factory.getCommand(parsed[0]);
        if (command != null) {
            command.execute(context, positionalArgs);
        }
    }


    public ExecutionContext getContext() {
        return context;
    }
}
