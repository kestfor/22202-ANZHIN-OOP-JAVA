import commands.*;
import commands.arithmetic.*;
import commandsfactory.CommandsFactory;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.HashMap;

public class FactoryTest {

    private final CommandsFactory factory = new CommandsFactory();
    HashMap<String, String> commands = new HashMap<String, String> () {{
        put("add", "+");
        put("sub", "-");
        put("mul", "*");
        put("div", "/");
        put("print", "PRINT");
        put("pop", "POP");
        put("define", "DEFINE");
        put("push", "PUSH");
        put("sqrt", "SQRT");
    }};

    @Test
    public void addCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("add"));
        Assert.assertTrue(command instanceof AddCommand);
    }

    @Test
    public void subCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("sub"));
        Assert.assertTrue(command instanceof SubCommand);
    }

    @Test
    public void mulCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("mul"));
        Assert.assertTrue(command instanceof MulCommand);
    }

    @Test
    public void divCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("div"));
        Assert.assertTrue(command instanceof DivCommand);
    }

    @Test
    public void printCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("print"));
        Assert.assertTrue(command instanceof PrintCommand);
    }

    @Test
    public void popCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("pop"));
        Assert.assertTrue(command instanceof PopCommand);
    }

    @Test
    public void defineCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("define"));
        Assert.assertTrue(command instanceof DefineCommand);
    }

    @Test
    public void pushCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("push"));
        Assert.assertTrue(command instanceof PushCommand);
    }

    @Test
    public void sqrtCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("sqrt"));
        Assert.assertTrue(command instanceof SqrtCommand);
    }

    @Test(expected = IllegalAccessException.class)
    public void nonCommandTest() throws IllegalAccessException {
        Command command = factory.getCommand(commands.get("asdfe"));
    }
}
