package commands;

import java.util.List;

import commands.exceptions.CommandsException;
import executioncontext.ExecutionContext;

public abstract class Command {
    abstract public void execute(ExecutionContext context, List<String> positionalArgs) throws CommandsException;

}