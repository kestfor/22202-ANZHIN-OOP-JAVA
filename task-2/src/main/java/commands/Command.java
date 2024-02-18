package commands;

import java.util.List;
import executioncontext.ExecutionContext;

public abstract class Command {
    abstract public void execute(ExecutionContext context, List<String> positionalArgs) throws IllegalAccessException;

}