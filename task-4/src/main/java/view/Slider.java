package view;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Slider extends JSlider {
    public Slider(ChangeListener listener) {
        super(1, 10, 1);
        this.setPaintTrack(true);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setMajorTickSpacing(1);
        this.setPreferredSize(new Dimension(300, 50));
        this.addChangeListener(listener);
    }
}
