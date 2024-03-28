package menu;

import events.NewSnakeSpeedEvent;
import game.Settings;

import javax.swing.*;
import java.util.HashMap;

public class SnakeSpeedMenuSection extends MenuSection {
    public enum SnakeSpeed {
        slow,
        standard,
        fast,
    }

    final private HashMap<SnakeSpeed, Integer> speed = new HashMap<SnakeSpeed, Integer>() {
        {
            put(SnakeSpeed.slow, 20000000);
            put(SnakeSpeed.standard, 10000000);
            put(SnakeSpeed.fast, 5000000);
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
