package menu;
import events.NewFieldSizeEvent;
import game.Settings;
import utils.Pair;

import javax.swing.*;
import java.util.HashMap;

public class FieldMenuSection extends MenuSection {
    public enum FieldSizes {
        small,
        standard,
        big,
    }

    final public static HashMap<FieldSizes, Pair<Integer, Integer>> sizes = new HashMap<FieldSizes, Pair<Integer, Integer>>() {
        {
            put(FieldSizes.small, new Pair<>(6, 6));
            put(FieldSizes.standard, new Pair<>(10, 10));
            put(FieldSizes.big, new Pair<>(10, 20));
        }
    };


    public FieldMenuSection(String name, Settings gameSettings) {
        super(name);

        JMenuItem small = new JMenuItem("small");
        small.addActionListener((e) -> gameSettings.notify(new NewFieldSizeEvent(sizes.get(FieldSizes.small))));

        JMenuItem standard = new JMenuItem("standard");
        standard.addActionListener((e) -> gameSettings.notify(new NewFieldSizeEvent(sizes.get(FieldSizes.standard))));

        JMenuItem big = new JMenuItem("big");
        big.addActionListener((e) -> gameSettings.notify(new NewFieldSizeEvent(sizes.get(FieldSizes.big))));


        this.add(small);
        this.add(standard);
        this.add(big);
    }

}
