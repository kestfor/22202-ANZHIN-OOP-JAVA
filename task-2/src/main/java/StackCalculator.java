import commands.Command;
import commands.exceptions.CommandsException;
import executioncontext.ExecutionContext;

import java.util.Arrays;
import java.util.List;

import commandsfactory.CommandsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackCalculator {
    private final ExecutionContext context;

    public static final Logger logger = LoggerFactory.getLogger(StackCalculator.class);

    private final CommandsFactory factory;

    StackCalculator(CommandsFactory factory) {
        this.context = new ExecutionContext();
        this.factory = factory;
    }

    public void execute(String rawString) {
        String[] parsed = rawString.split(" ");

        List<String> positionalArgs = Arrays.asList(parsed);
        positionalArgs = positionalArgs.subList(1, positionalArgs.size());

        Command command = null;
        try {
            command = factory.getCommand(parsed[0]);
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }
        if (command != null) {
            try {
                command.execute(context, positionalArgs);
            } catch (CommandsException e) {
                logger.warn(e.getLocalizedMessage());
            }
        }
    }


    public ExecutionContext getContext() {
        return context;
    }
}
