import commands.Command;
import commands.DefineCommand;
import commands.PopCommand;
import commands.PushCommand;
import commands.arithmetic.AddCommand;
import commands.arithmetic.DivCommand;
import commands.arithmetic.MulCommand;
import commands.arithmetic.SubCommand;
import executioncontext.ExecutionContext;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import commands.exceptions.*;

public class CommandsTest {

    private ExecutionContext context = new ExecutionContext();

    @Test
    public void addTest() {
        Command command = new AddCommand();
        context.getStack().push(1.0);
        context.getStack().push(2.0);
        List<String> args = new ArrayList<>();
        try {
            command.execute(context, args);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        try {
            double val = context.getStack().peek();
            Assert.assertEquals(3.0, val, 0.0);
        } catch (EmptyStackException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Test
    public void subTest() {
        Command command = new SubCommand();
        context.getStack().push(1.0);
        context.getStack().push(2.0);
        List<String> args = new ArrayList<>();
        try {
            command.execute(context, args);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        try {
            double val = context.getStack().peek();
            Assert.assertEquals(1.0, val, 0.0);
        } catch (EmptyStackException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Test
    public void mulTest() {
        Command command = new MulCommand();
        context.getStack().push(1.0);
        context.getStack().push(2.0);
        List<String> args = new ArrayList<>();
        try {
            command.execute(context, args);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        try {
            double val = context.getStack().peek();
            Assert.assertEquals(2.0, val, 0.0);
        } catch (EmptyStackException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Test
    public void divTest() {
        Command command = new DivCommand();
        context.getStack().push(2.0);
        context.getStack().push(6.0);
        List<String> args = new ArrayList<>();
        try {
            command.execute(context, args);
        } catch (CommandsException e) {
            System.err.println(e.getLocalizedMessage());
        }
        try {
            double val = context.getStack().peek();
            Assert.assertEquals(3.0, val, 0.0);
        } catch (EmptyStackException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Test
    public void DefinePushTest() {
        Command def = new DefineCommand();
        Command push = new PushCommand();
        context = new ExecutionContext();
        List<String> args = new ArrayList<>();
        args.add("a");
        args.add("8");
        try {
            def.execute(context, args);
            args.remove(1);
            push.execute(context, args);
        } catch (CommandsException e) {
            System.err.println(e.getLocalizedMessage());
        }
        try {
            double val = context.getStack().peek();
            Assert.assertEquals(8.0, val, 0.0);
        } catch (EmptyStackException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Test
    public void DefineTest() {
        Command def = new DefineCommand();
        context = new ExecutionContext();
        List<String> args = new ArrayList<>();
        args.add("a");
        args.add("8");
        try {
            def.execute(context, args);
        } catch (CommandsException e) {
            System.err.println(e.getLocalizedMessage());
        }
        try {
            double val = context.getNamedArgs().get("a");
            Assert.assertEquals(8.0, val, 0.0);
        } catch (EmptyStackException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }


    @Test(expected = CommandsException.class)
    public void addExceptionTest() throws CommandsException {
        Command command = new AddCommand();
        context.getStack().push(1.0);
        List<String> args = new ArrayList<>();
        command.execute(context, args);
    }

    @Test(expected = CommandsException.class)
    public void popExceptionTest() throws CommandsException {
        Command command = new PopCommand();
        List<String> args = new ArrayList<>();
        command.execute(context, args);
    }

    @Test(expected = DivisionByZeroException.class)
    public void divisionByZeroExceptionTest() throws CommandsException {
        Command command = new DivCommand();
        context.getStack().push(0.0);
        context.getStack().push(0.0);
        List<String> args = new ArrayList<>();
        command.execute(context, args);
    }
}
