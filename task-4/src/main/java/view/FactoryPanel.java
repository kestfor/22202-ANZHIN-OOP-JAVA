package view;

import factory.Factory;
import factory.service.listeners.AccessoriesSliderListener;
import factory.service.listeners.BodySliderListener;
import factory.service.listeners.DealerSliderListener;
import factory.service.listeners.MotorSliderListener;

import javax.swing.*;
import java.awt.*;

public class FactoryPanel extends JPanel {
    private final Factory factory;
    private final Slider dealerSlider;
    private final Slider bodiesSupplierSlider;
    private final Slider accessoriesSupplierSlider;
    private final Slider motorsSupplierSlider;

    public FactoryPanel(Factory factory) {
        this.factory = factory;
        this.setBackground(new Color(56, 56, 56));

        AccessoriesSliderListener l1 = new AccessoriesSliderListener();
        BodySliderListener l2 = new BodySliderListener();
        DealerSliderListener l3 = new DealerSliderListener();
        MotorSliderListener l4 = new MotorSliderListener();

        l1.addObserver(factory.settings);
        l2.addObserver(factory.settings);
        l3.addObserver(factory.settings);
        l4.addObserver(factory.settings);

        accessoriesSupplierSlider = new Slider(l1);
        bodiesSupplierSlider = new Slider(l2);
        dealerSlider = new Slider(l3);
        motorsSupplierSlider = new Slider(l4);

        this.add(dealerSlider, BorderLayout.CENTER);
        this.add(bodiesSupplierSlider, BorderLayout.CENTER);
        this.add(accessoriesSupplierSlider, BorderLayout.CENTER);
        this.add(motorsSupplierSlider, BorderLayout.CENTER);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }

}
