package view;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderWithLabel extends JPanel {

    JLabel label;
    JSlider slider;

    public SliderWithLabel(ChangeListener listener, int start, String label) {
        this.label = new JLabel(label);
        this.label.setSize(300, 50);
        this.slider = new JSlider(1, 15, start);
        this.slider.setPaintTrack(true);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        this.slider.setMajorTickSpacing(1);
        this.slider.setPreferredSize(new Dimension(300, 50));
        this.slider.addChangeListener(listener);
        this.setLayout(new GridLayout(2, 1));
        this.add(this.label);
        this.add(slider);
    }
}
