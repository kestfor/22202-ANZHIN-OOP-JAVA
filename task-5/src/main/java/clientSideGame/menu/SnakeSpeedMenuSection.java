package clientSideGame.menu;

import events.NewSnakeSpeedEvent;
import clientSideGame.game.Settings;

import javax.swing.*;
import java.util.HashMap;

public class SnakeSpeedMenuSection extends MenuSection {
    public enum SnakeSpeed {
        slow,
        standard,
        fast,
    }

    final public static HashMap<SnakeSpeed, Integer> speed = new HashMap<SnakeSpeed, Integer>() {
        {
            put(SnakeSpeed.slow, 1000);
            put(SnakeSpeed.standard, 750);
            put(SnakeSpeed.fast, 500);
        }
    };


    public SnakeSpeedMenuSection(String name, Settings gameSettings) {
        super(name);

        JMenuItem slow = new JMenuItem("slow");
        JMenuItem standard = new JMenuItem("standard");
        JMenuItem fast = new JMenuItem("fast");

        slow.addActionListener((e) -> gameSettings.notify(new NewSnakeSpeedEvent(speed.get(SnakeSpeed.slow))));

        standard.addActionListener((e) -> gameSettings.notify(new NewSnakeSpeedEvent(speed.get(SnakeSpeed.standard))));

        fast.addActionListener((e) -> gameSettings.notify(new NewSnakeSpeedEvent(speed.get(SnakeSpeed.fast))));

        this.add(slow);
        this.add(standard);
        this.add(fast);

    }


}
